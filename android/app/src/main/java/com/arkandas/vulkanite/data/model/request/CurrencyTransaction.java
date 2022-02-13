package com.arkandas.vulkanite.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyTransaction {

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("walletDestination")
    @Expose
    private String walletDestination;

    public CurrencyTransaction() {
        super();
    }

    public CurrencyTransaction(Integer amount, String note, String walletDestination) {
        this.amount = amount;
        this.note = note;
        this.walletDestination = walletDestination;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWalletDestination() {
        return walletDestination;
    }

    public void setWalletDestination(String walletDestination) {
        this.walletDestination = walletDestination;
    }

    @Override
    public String toString() {
        return "CurrencyTransaction{" +
                "amount=" + amount +
                ", note='" + note + '\'' +
                ", walletDestination='" + walletDestination + '\'' +
                '}';
    }
}
