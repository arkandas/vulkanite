package com.arkandas.vulkanite.data.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class RegisterTransaction {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;

    public RegisterTransaction() {
    }

    public RegisterTransaction(String email, String fullname, String password, String username) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "RegisterTransaction{" +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
