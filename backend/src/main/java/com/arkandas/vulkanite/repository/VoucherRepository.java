package com.arkandas.vulkanite.repository;

import com.arkandas.vulkanite.model.db.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface VoucherRepository extends CrudRepository<Voucher, Long> {

    @Query("select new com.arkandas.vulkanite.model.db.Voucher(u.voucherId, u.voucherCode, u.amount, u.voucherTitle, u.voucherDescription, u.voucherUsed, u.userId, u.redemptionDate,u.voucherImage, u.rarity) from voucher u where u.voucherCode = :voucherCode")
    Voucher getVoucherByCode(@Param("voucherCode") String voucherCode);

    @Query("select new com.arkandas.vulkanite.model.db.Voucher(u.voucherId, u.voucherCode, u.amount, u.voucherTitle, u.voucherDescription, u.voucherUsed, u.userId, u.redemptionDate,u.voucherImage, u.rarity) from voucher u where u.userId = :userId order by u.redemptionDate desc")
    ArrayList<Voucher> getVoucherByUser(Long userId);
}