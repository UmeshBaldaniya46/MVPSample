package com.MVP.Sample.mvp.presenters;

import android.content.Intent;

import com.MVP.Sample.baseframework.mvp.BasePresenter;
import com.MVP.Sample.baseframework.mvp.BaseView;
import com.MVP.Sample.mvp.model.LoginActivityModel;
import com.MVP.Sample.response_models.LoginResponse;

//this interface is used to get data from api or data to pass on UI using view object
public interface LoginPresenter extends BasePresenter {

    //this interface is used to setup UI after any api or db call.
    interface LoginView extends BaseView {

        void onLoginSuccessful(LoginResponse loginResponse);

        LoginActivityModel doRetrieveModel();
    }

    void doLogin();

}