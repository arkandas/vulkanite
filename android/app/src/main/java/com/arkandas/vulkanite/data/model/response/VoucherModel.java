package com.arkandas.vulkanite.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoucherModel {

    @SerializedName("voucherId")
    @Expose
    private Integer voucherId;
    @SerializedName("voucherCode")
    @Expose
    private String voucherCode;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("voucherTitle")
    @Expose
    private String voucherTitle;
    @SerializedName("voucherDescription")
    @Expose
    private String voucherDescription;
    @SerializedName("voucherUsed")
    @Expose
    private Boolean voucherUsed;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("redemptionDate")
    @Expose
    private String redemptionDate;
    @SerializedName("voucherImage")
    @Expose
    private String voucherImage;
    @SerializedName("rarity")
    @Expose
    private String rarity;

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getVoucherTitle() {
        return voucherTitle;
    }

    public void setVoucherTitle(String voucherTitle) {
        this.voucherTitle = voucherTitle;
    }

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }

    public Boolean getVoucherUsed() {
        return voucherUsed;
    }

    public void setVoucherUsed(Boolean voucherUsed) {
        this.voucherUsed = voucherUsed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(String redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public String getVoucherImage() {
        return voucherImage;
    }

    public void setVoucherImage(String voucherImage) {
        this.voucherImage = voucherImage;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return "VoucherModel{" +
                "voucherId=" + voucherId +
                ", voucherCode='" + voucherCode + '\'' +
                ", amount=" + amount +
                ", voucherTitle='" + voucherTitle + '\'' +
                ", voucherDescription='" + voucherDescription + '\'' +
                ", voucherUsed=" + voucherUsed +
                ", userId=" + userId +
                ", redemptionDate='" + redemptionDate + '\'' +
                ", voucherImage='" + voucherImage + '\'' +
                ", rarity='" + rarity + '\'' +
                '}';
    }
}
