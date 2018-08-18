package com.MVP.Sample.mvp.domains;

import com.MVP.Sample.response_models.LoginResponse;

public class LoginActivityDomain {

    private LoginResponse userResponse;

    public LoginResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(LoginResponse userResponse) {
        this.userResponse = userResponse;
    }

}
