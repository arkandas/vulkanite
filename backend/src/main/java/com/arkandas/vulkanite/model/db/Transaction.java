package com.arkandas.vulkanite.model.db;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "transaction")
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"id\"", unique = true)
    private Long transactionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "date")
    private Instant transactionDate;

    @Column(name = "type")
    private String transactionType;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "description")
    private String description;

    @Column(name = "note")
    private String note;

    @Column(name = "wallet_origin")
    private String walletOrigin;

    @Column(name = "wallet_destination")
    private String walletDestination;

    public Transaction(){
        super();
    }

    public Transaction(Long transactionId, Long userId, Instant transactionDate, String transactionType, Long amount, Long balance, String description, String note, String walletOrigin, String walletDestination) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
        this.description = description;
        this.note = note;
        this.walletOrigin = walletOrigin;
        this.walletDestination = walletDestination;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWalletOrigin() {
        return walletOrigin;
    }

    public void setWalletOrigin(String walletOrigin) {
        this.walletOrigin = walletOrigin;
    }

    public String getWalletDestination() {
        return walletDestination;
    }

    public void setWalletDestination(String walletDestination) {
        this.walletDestination = walletDestination;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", transactionDate=" + transactionDate +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", balance=" + balance +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", walletOrigin='" + walletOrigin + '\'' +
                ", walletDestination='" + walletDestination + '\'' +
                '}';
    }
}
