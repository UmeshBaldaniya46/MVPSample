package com.MVP.Sample.response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role_id")
    @Expose
    private String role_id = "2";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}