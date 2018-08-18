package com.MVP.Sample.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.MVP.Sample.R;
import com.MVP.Sample.baseframework.mvp.BaseActivity;
import com.MVP.Sample.mvp.presenters.SplashPresenter;
import com.MVP.Sample.mvp.presenters.SplashPresenterImpl;
import com.MVP.Sample.utils.Utility;

import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity implements SplashPresenter.SplashView {

    private SplashPresenterImpl mPresenter;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.registerBus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unRegisterBus();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        //Init Implementer
        this.mPresenter = new SplashPresenterImpl(this);

    }

    @Override
    public void onRedirect(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Utility.showSnackBar(findViewById(R.id.mainLayout), message, Snackbar.LENGTH_LONG, true, this);
    }

    @Override
    public void showError(String message) {
        Utility.showSnackBar(findViewById(R.id.mainLayout), message, Snackbar.LENGTH_LONG, true, this);
    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void onNetworkStateChange(boolean isConnect) {

    }

}
