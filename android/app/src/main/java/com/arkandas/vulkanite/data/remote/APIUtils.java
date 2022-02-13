package com.arkandas.vulkanite.data.remote;

public class APIUtils {

    private APIUtils() {}

    public static final String BASE_URL = "http://10.0.2.2:8080/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}