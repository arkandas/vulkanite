package com.arkandas.vulkanite.model.response;

public class AccountInfo {


    private String walletAddress;
    private Long walletBalance;
    private String walletCurrencyType;
    private Long userId;

    public AccountInfo(String walletAddress, Long walletBalance, String walletCurrencyType, Long userId) {
        this.walletAddress = walletAddress;
        this.walletBalance = walletBalance;
        this.walletCurrencyType = walletCurrencyType;
        this.userId = userId;
    }

    public AccountInfo(){
        super();
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Long getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Long walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getWalletCurrencyType() {
        return walletCurrencyType;
    }

    public void setWalletCurrencyType(String walletCurrencyType) {
        this.walletCurrencyType = walletCurrencyType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
