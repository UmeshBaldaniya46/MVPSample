package com.MVP.Sample.mvp.model;

import android.content.Context;

import com.MVP.Sample.baseframework.mvp.BaseViewModel;
import com.MVP.Sample.mvp.domains.LoginActivityDomain;
import com.MVP.Sample.response_models.LoginRequest;


//this class is used to set request  and get response
public class LoginActivityModel extends BaseViewModel {

    public LoginActivityDomain mainActivityDomain;
    private LoginRequest requestLogin;

    // initialization of any array for related list is done here
    public LoginActivityModel(Context context) {
        super(context);
        this.mainActivityDomain = new LoginActivityDomain();
    }

    public LoginRequest getRequestLogin() {
        return requestLogin;
    }

    public void setRequestLogin(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail(username);
        request.setPassword(password);
        this.requestLogin = request;
    }
}
