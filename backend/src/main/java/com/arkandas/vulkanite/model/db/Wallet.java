package com.arkandas.vulkanite.model.db;

import javax.persistence.*;

@Entity(name = "wallet")
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long walletId;

    @Column(name = "address")
    private String walletAddress;

    @Column(name = "currency_type")
    private String currencyType;

    @Column(name = "current_balance")
    private Long currentBalance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;


    public Wallet(){
        super();
    }

    public Wallet(Long walletId, String walletAddress, String currencyType, Long currentBalance, User userId) {
        this.walletId = walletId;
        this.walletAddress = walletAddress;
        this.currencyType = currencyType;
        this.currentBalance = currentBalance;
        this.userId = userId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public Long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", walletAddress='" + walletAddress + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", currentBalance=" + currentBalance +
                ", userId=" + userId +
                '}';
    }
}
