package com.arkandas.vulkanite.model.db;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "voucher")
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"id\"", unique = true)
    private Long voucherId;

    @Column(name = "\"voucherCode\"", unique = true)
    private String voucherCode;

    @Column(name = "\"amount\"")
    private Long amount;

    @Column(name = "\"title\"")
    private String voucherTitle;

    @Column(name = "\"description\"")
    private String voucherDescription;

    @Column(name = "\"used\"")
    private Boolean voucherUsed;

    @Column(name = "\"user_id\"")
    private Long userId;

    @Column(name = "\"redemption_date\"")
    private Instant redemptionDate;

    @Column(name = "\"image\"")
    private String voucherImage;

    @Column(name = "\"rarity\"")
    private String rarity;

    public Voucher() {
        super();
    }

    public Voucher(Long voucherId, String voucherCode, Long amount, String voucherTitle, String voucherDescription, Boolean voucherUsed, Long userId, Instant redemptionDate, String voucherImage, String rarity) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.amount = amount;
        this.voucherTitle = voucherTitle;
        this.voucherDescription = voucherDescription;
        this.voucherUsed = voucherUsed;
        this.userId = userId;
        this.redemptionDate = redemptionDate;
        this.voucherImage = voucherImage;
        this.rarity = rarity;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(Instant redemptionDate) {
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
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", voucherCode='" + voucherCode + '\'' +
                ", amount=" + amount +
                ", voucherTitle='" + voucherTitle + '\'' +
                ", voucherDescription='" + voucherDescription + '\'' +
                ", voucherUsed=" + voucherUsed +
                ", userId=" + userId +
                ", redemptionDate=" + redemptionDate +
                ", voucherImage='" + voucherImage + '\'' +
                ", rarity='" + rarity + '\'' +
                '}';
    }
}
