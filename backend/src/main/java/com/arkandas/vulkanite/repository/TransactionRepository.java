package com.arkandas.vulkanite.repository;

import com.arkandas.vulkanite.model.db.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("select new com.arkandas.vulkanite.model.db.Transaction(u.transactionId, u.userId, u.transactionDate, u.transactionType, u.amount, u.balance, u.description, u.note, u.walletOrigin, u.walletDestination) from transaction u where u.transactionId = :transactionId order by u.transactionDate desc ")
    Transaction getTransactionById (@Param("transactionId") Long transactionId);

    @Query("select new com.arkandas.vulkanite.model.db.Transaction(u.transactionId, u.userId, u.transactionDate, u.transactionType, u.amount, u.balance, u.description, u.note, u.walletOrigin, u.walletDestination) from transaction u where u.userId = :userId order by u.transactionDate desc")
    ArrayList<Transaction> getTransactionsByUserId (@Param("userId") Long userId);

}

