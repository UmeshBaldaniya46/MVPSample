package com.MVP.Sample.response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.MVP.Sample.baseframework.models.BaseResponse;

public class LoginResponse extends BaseResponse {

    @SerializedName("Users")
    @Expose
    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}