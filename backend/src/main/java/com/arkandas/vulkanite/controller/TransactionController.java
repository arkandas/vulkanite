package com.arkandas.vulkanite.controller;

import com.arkandas.vulkanite.config.JwtTokenUtil;
import com.arkandas.vulkanite.errors.UnauthorizedException;
import com.arkandas.vulkanite.model.db.Transaction;
import com.arkandas.vulkanite.model.db.User;
import com.arkandas.vulkanite.model.db.Wallet;
import com.arkandas.vulkanite.model.request.CurrencyExchange;
import com.arkandas.vulkanite.repository.TransactionRepository;
import com.arkandas.vulkanite.repository.UserRepository;
import com.arkandas.vulkanite.repository.WalletRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public ArrayList<Transaction> getRecentTransactions(@RequestHeader(value = "token") @NotNull String token, HttpServletResponse response) throws Exception {

        ArrayList<Transaction> userTransactionList = transactionRepository.getTransactionsByUserId(userRepository.findByUserName(jwtTokenUtil.getUsernameFromToken(token.substring(7))).getUserId());

        return userTransactionList;

    }

    @RequestMapping(value = "/transactions/{transactionId}", method = RequestMethod.GET)
    public Transaction getTransactionByTransactionIdId(@PathVariable("transactionId") Long transactionId, @RequestHeader(value = "token") @NotNull String token, HttpServletResponse response) throws Exception {

        User user = userRepository.findByUserName(jwtTokenUtil.getUsernameFromToken(token.substring(7)));

        Transaction transactionByTransactionId = transactionRepository.getTransactionById(transactionId);

        if((transactionByTransactionId.getUserId() == user.getUserId()) || user.getUserType() == 0) {
            return transactionByTransactionId;
        }else{
            throw new UnauthorizedException("User does not have permission to see this transaction");
        }

    }

    @RequestMapping(value = "/transactions/sendCurrency", method = RequestMethod.POST)
    public void sendCurrencyBetweenWallets(@RequestBody CurrencyExchange currencyExchange, @RequestHeader(value = "token") @NotNull String token, HttpServletResponse response) throws Exception {

        User user = userRepository.findByUserName(jwtTokenUtil.getUsernameFromToken(token.substring(7)));

        Wallet senderWallet = walletRepository.getWalletByUserId(user);
        Wallet destinationWallet = walletRepository.getWalletByAddress(currencyExchange.getWalletDestination());
        if(destinationWallet == null){
            throw new UnauthorizedException("Destination wallet does not exist");
        }

        if(senderWallet.getWalletAddress().equals(destinationWallet.getWalletAddress())){
            throw new UnauthorizedException("Wallet origin and destination are the same");
        }

        if(senderWallet.getCurrentBalance() < currencyExchange.getAmount()){
            throw new UnauthorizedException("Insufficient funds in sender account");
        }else {

            Long newBalanceOrigin = senderWallet.getCurrentBalance() - currencyExchange.getAmount();
            Long newBalanceDestination = destinationWallet.getCurrentBalance() + currencyExchange.getAmount();

            senderWallet.setCurrentBalance(newBalanceOrigin);
            destinationWallet.setCurrentBalance(newBalanceDestination);
            walletRepository.save(senderWallet);
            walletRepository.save(destinationWallet);

            Instant currentTime = Instant.now();

            Transaction senderTransaction = new Transaction(0L, senderWallet.getUserId().getUserId(), currentTime, "SENT", -currencyExchange.getAmount(), senderWallet.getCurrentBalance(), "To: " + destinationWallet.getWalletAddress(), currencyExchange.getNote(), senderWallet.getWalletAddress(), destinationWallet.getWalletAddress());
            Transaction destinationTransaction = new Transaction(0L, destinationWallet.getUserId().getUserId(), currentTime, "RECEIVED", currencyExchange.getAmount(), destinationWallet.getCurrentBalance(), "From: " + senderWallet.getWalletAddress(), currencyExchange.getNote(), senderWallet.getWalletAddress(), destinationWallet.getWalletAddress());
            transactionRepository.save(senderTransaction);
            transactionRepository.save(destinationTransaction);
        }
    }

}