package com.arkandas.vulkanite.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "user")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"id\"", unique = true)
    private Long userId;

    @Column(name = "\"email\"", unique = true)
    private String userEmail;

    @Column(name = "\"username\"", unique = true)
    private String username;

    @Column(name = "\"fullname\"")
    private String userfullname;

    @Column(name = "\"password\"")
//    @JsonIgnore
    private String password;

    @Column(name = "\"type\"")
    private Integer userType;


    public User() {
        super();
    }

    public User(Long userId, String userEmail, String username, String userfullname, String password, Integer userType) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.username = username;
        this.userfullname = userfullname;
        this.password = password;
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", userfullname='" + userfullname + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}

