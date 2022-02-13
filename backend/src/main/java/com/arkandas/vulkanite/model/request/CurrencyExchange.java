package com.arkandas.vulkanite.model.request;

public class CurrencyExchange {

    private String walletDestination;
    private Long amount;
    private String note;

    public CurrencyExchange() {
        super();
    }

    public CurrencyExchange(String walletOrigin, String walletDestination, Long amount, String note) {
        this.walletDestination = walletDestination;
        this.amount = amount;
        this.note = note;
    }

    public String getWalletDestination() {
        return walletDestination;
    }

    public void setWalletDestination(String walletDestination) {
        this.walletDestination = walletDestination;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                ", walletDestination='" + walletDestination + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    }
}
