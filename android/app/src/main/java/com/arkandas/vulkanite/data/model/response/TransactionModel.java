package com.arkandas.vulkanite.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class TransactionModel {


    @SerializedName("transactionId")
    @Expose
    private Integer transactionId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("transactionType")
    @Expose
    private String transactionType;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("walletOrigin")
    @Expose
    private String walletOrigin;
    @SerializedName("walletDestination")
    @Expose
    private String walletDestination;

    public TransactionModel() {
        super();
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
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
        return "TransactionModel{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", transactionDate='" + transactionDate + '\'' +
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

