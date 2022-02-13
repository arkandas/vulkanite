package com.arkandas.vulkanite.model.response;

import java.time.Instant;

public class TokenResponse {

    private String userEmail;
    private String userName;
    private String token;
    private Instant expirationDate;
    private Integer userType;

    public TokenResponse(String userEmail, String userName, String token, Instant expirationDate, Integer userType) {
        super();
        this.userEmail = userEmail;
        this.userName = userName;
        this.token = token;
        this.expirationDate = expirationDate;
        this.userType = userType;
    }

    public TokenResponse() {
        super();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", userType=" + userType +
                '}';
    }
}