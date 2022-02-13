package com.arkandas.vulkanite.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountInfo {

    @SerializedName("walletAddress")
    @Expose
    private String walletAddress;
    @SerializedName("walletBalance")
    @Expose
    private Integer walletBalance;
    @SerializedName("walletCurrencyType")
    @Expose
    private String walletCurrencyType;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public AccountInfo() {
    }

    public AccountInfo(String walletAddress, Integer walletBalance, String walletCurrencyType, Integer userId) {
        this.walletAddress = walletAddress;
        this.walletBalance = walletBalance;
        this.walletCurrencyType = walletCurrencyType;
        this.userId = userId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Integer getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Integer walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getWalletCurrencyType() {
        return walletCurrencyType;
    }

    public void setWalletCurrencyType(String walletCurrencyType) {
        this.walletCurrencyType = walletCurrencyType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "walletAddress='" + walletAddress + '\'' +
                ", walletBalance=" + walletBalance +
                ", walletCurrencyType='" + walletCurrencyType + '\'' +
                ", userId=" + userId +
                '}';
    }
}
