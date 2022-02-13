package com.arkandas.vulkanite.controller;

import com.arkandas.vulkanite.config.JwtTokenUtil;
import com.arkandas.vulkanite.errors.InternalErrorException;
import com.arkandas.vulkanite.errors.UnauthorizedException;
import com.arkandas.vulkanite.model.db.User;
import com.arkandas.vulkanite.model.db.Wallet;
import com.arkandas.vulkanite.model.request.LoginRequest;
import com.arkandas.vulkanite.model.request.RegisterRequest;
import com.arkandas.vulkanite.model.response.AccountInfo;
import com.arkandas.vulkanite.model.response.JWTResponse;
import com.arkandas.vulkanite.repository.UserRepository;
import com.arkandas.vulkanite.repository.WalletRepository;
import com.arkandas.vulkanite.service.EmailGenerator;
import com.arkandas.vulkanite.service.JwtUserDetailsService;
import com.arkandas.vulkanite.util.TokenCypher;
import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.activation.DataHandler;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import com.sun.mail.smtp.SMTPTransport;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

@RestController
@CrossOrigin
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest request) throws Exception {

        authenticate(request.getUsername(),  request.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JWTResponse(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void createUser(@RequestBody @NotNull RegisterRequest registerRequest, HttpServletResponse response) throws UnauthorizedException, InternalErrorException {
//      1. Check if user already exists
        User checkUserExists = userRepository.getUserWithEmail(registerRequest.getEmail());
        if(checkUserExists != null){
            throw new UnauthorizedException("User already exists");
        }else{
//      2. Create User
            userDetailsService.save(registerRequest);

//      3. Create Wallet for new User
            User userForWallet = userRepository.getUserWithEmail(registerRequest.getEmail());
            Wallet wallet = new Wallet();
            if(userForWallet != null){
                Instant instantForWalletAddress = Instant.now().plus(10, ChronoUnit.HOURS);
                wallet.setWalletAddress(StringUtils.left( TokenCypher.GenerateToken(userForWallet.getUsername(), instantForWalletAddress), 14) );
                wallet.setCurrentBalance(999L);
                wallet.setCurrencyType("VUL");
                wallet.setUserId(userForWallet);
                walletRepository.save(wallet);
            }else{
                throw new UnauthorizedException("Error during user creation");
            }
//            4. Send HTML email to new User
            sendHTMLMail(userForWallet, wallet);
        }
    }

    @RequestMapping(value = "/accountInfo", method = RequestMethod.GET)
    public AccountInfo getAccountInfo(@RequestHeader(value = "token") @NotNull String token, HttpServletResponse response) throws Exception {

        User user = userRepository.findByUserName(jwtTokenUtil.getUsernameFromToken(token.substring(7)));

        Wallet wallet = walletRepository.getWalletByUserId(user);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUserId(wallet.getUserId().getUserId());
        accountInfo.setWalletAddress(wallet.getWalletAddress());
        accountInfo.setWalletBalance(wallet.getCurrentBalance());
        accountInfo.setWalletCurrencyType(wallet.getCurrencyType());
        return accountInfo;
    }

    private void sendHTMLMail(User newUser, Wallet wallet) {
        final String SMTP_SERVER = env.getProperty("mail.smtp");
        final String USERNAME = env.getProperty("mail.username");
        final String PASSWORD = env.getProperty("mail.password");
        final String EMAIL_FROM = env.getProperty("mail.address");
        final String EMAIL_TO = newUser.getUserEmail();
        final String EMAIL_SUBJECT = "Vulkanite User Registration";
        final String EMAIL_TEXT = EmailGenerator.userRegistrationEmail(newUser, wallet);

            Properties prop = System.getProperties();
            prop.put("mail.smtp.host", SMTP_SERVER);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.port", "587");

            Session session = Session.getInstance(prop, null);
            Message msg = new MimeMessage(session);

            try {
                msg.setFrom(new InternetAddress(EMAIL_FROM));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));
                msg.setSubject(EMAIL_SUBJECT);
                msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));
                SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
                t.connect(SMTP_SERVER, USERNAME, PASSWORD);
                t.sendMessage(msg, msg.getAllRecipients());
                LOG.warn("Response: " + t.getLastServerResponse());
                t.close();

            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }

        static class HTMLDataSource implements DataSource {

            private String html;

            public HTMLDataSource(String htmlString) {
                html = htmlString;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                if (html == null) throw new IOException("html message is null!");
                return new ByteArrayInputStream(html.getBytes());
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                throw new IOException("This DataHandler cannot write HTML");
            }

            @Override
            public String getContentType() {
                return "text/html";
            }

            @Override
            public String getName() {
                return "HTMLDataSource";
            }
        }
    }


