package com.arkandas.vulkanite.data.remote;


import com.arkandas.vulkanite.data.model.request.CurrencyTransaction;
import com.arkandas.vulkanite.data.model.request.RegisterTransaction;
import com.arkandas.vulkanite.data.model.response.AccountInfo;
import com.arkandas.vulkanite.data.model.response.LoginResponse;
import com.arkandas.vulkanite.data.model.response.TransactionModel;
import com.arkandas.vulkanite.data.model.response.VoucherModel;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/login")
    Call<LoginResponse> getUserToken(@Body JsonObject jsonOb);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/register")
    Call<Void> registerUser(@Body RegisterTransaction registerTransaction);

    @Headers({"Accept: application/json","Content-Type: application/json"})
    @GET("/accountInfo")
    Call<AccountInfo> getAccountInfo(@Header("token") String token);

    @Headers({"Accept: application/json","Content-Type: application/json"})
    @GET("/transactions")
    Call<List<TransactionModel>> getUserTransactions(@Header("token") String token);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/transactions/sendCurrency")
    Call<Void> sendCurrency(@Header("token") String token, @Body CurrencyTransaction currencyTransaction);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("/vouchers")
    Call<List<VoucherModel>> getVouchers(@Header("token") String token);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("/vouchers/redeem/{voucherCode}")
    Call<Void> useVoucher(@Header("token") String token, @Path("voucherCode") String voucherCode);


}