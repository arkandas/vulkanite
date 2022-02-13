package com.arkandas.vulkanite.repository;

import com.arkandas.vulkanite.model.db.User;
import com.arkandas.vulkanite.model.db.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {

    @Query("select new com.arkandas.vulkanite.model.db.Wallet(u.walletId, u.walletAddress, u.currencyType, u.currentBalance, u.userId) from wallet u where u.walletId = :walletId")
    Wallet getWalletById(@Param("walletId") Long walletId);

    @Query("select new com.arkandas.vulkanite.model.db.Wallet(u.walletId, u.walletAddress, u.currencyType, u.currentBalance, u.userId) from wallet u where u.walletAddress = :walletAddress")
    Wallet getWalletByAddress(@Param("walletAddress") String walletAddress);

    @Query("select new com.arkandas.vulkanite.model.db.Wallet(u.walletId, u.walletAddress, u.currencyType, u.currentBalance, u.userId) from wallet u where u.userId = :userId")
    Wallet getWalletByUserId(@Param("userId") User userId);

}
