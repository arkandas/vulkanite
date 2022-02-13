package com.arkandas.vulkanite.controller;

import com.arkandas.vulkanite.config.JwtTokenUtil;
import com.arkandas.vulkanite.errors.ResourceNotFoundException;
import com.arkandas.vulkanite.model.db.Transaction;
import com.arkandas.vulkanite.model.db.User;
import com.arkandas.vulkanite.model.db.Voucher;
import com.arkandas.vulkanite.model.db.Wallet;
import com.arkandas.vulkanite.repository.TransactionRepository;
import com.arkandas.vulkanite.repository.UserRepository;
import com.arkandas.vulkanite.repository.VoucherRepository;
import com.arkandas.vulkanite.repository.WalletRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class VoucherController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/vouchers/redeem/{voucherCode}", method = RequestMethod.POST)
    public void redeemVoucher(@PathVariable("voucherCode") String voucherCode, @RequestHeader(value = "token") @NotNull String token, HttpServletResponse response) throws Exception {

        User user = userRepository.findByUserName(jwtTokenUtil.getUsernameFromToken(token.substring(7)));

//        1. Get voucher
        Voucher voucher = voucherRepository.getVoucherByCode(voucherCode);
//        2. If voucher exists and is valid add funds to user
        if(voucher != null && voucher.getVoucherUsed() == false){
            Wallet wallet = walletRepository.getWalletByUserId(user);
            Long newWalletAmount = wallet.getCurrentBalance() + voucher.getAmount();
            wallet.setCurrentBalance(newWalletAmount);
            walletRepository.save(wallet);
            voucher.setRedemptionDate(Instant.now());
            voucher.setUserId(user.getUserId());
            voucher.setVoucherUsed(true);
            voucherRepository.save(voucher);
//            3. Create redemption transaction
            Transaction redemptionTransaction = new Transaction();
            redemptionTransaction.setAmount(voucher.getAmount());
            redemptionTransaction.setBalance(newWalletAmount);
            redemptionTransaction.setDescription("Code: " + voucher.getVoucherCode());
            redemptionTransaction.setTransactionDate(Instant.now());
            redemptionTransaction.setTransactionType("VOUCHER");
            redemptionTransaction.setUserId(user.getUserId());
            redemptionTransaction.setWalletOrigin("System");
            redemptionTransaction.setWalletDestination(wallet.getWalletAddress());
            transactionRepository.save(redemptionTransaction);
        }else{
            throw new ResourceNotFoundException("Voucher is invalid or has already been redeemed");
        }
    }

    @RequestMapping(value = "/vouchers", method = RequestMethod.GET)
    public ArrayList<Voucher> getVouchers(@RequestHeader(value = "token") @NotNull String token, HttpServletResponse response) throws Exception {

        User user = userRepository.findByUserName(jwtTokenUtil.getUsernameFromToken(token.substring(7)));

        return voucherRepository.getVoucherByUser(user.getUserId());

    }



}
