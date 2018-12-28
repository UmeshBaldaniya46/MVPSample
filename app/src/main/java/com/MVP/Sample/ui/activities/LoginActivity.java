package com.MVP.Sample.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.MVP.Sample.MVPSampleApp;
import com.MVP.Sample.R;
import com.MVP.Sample.baseframework.mvp.BaseActivity;
import com.MVP.Sample.mvp.model.LoginActivityModel;
import com.MVP.Sample.mvp.presenters.LoginPresenter;
import com.MVP.Sample.mvp.presenters.LoginPresenterImpl;
import com.MVP.Sample.response_models.LoginResponse;
import com.MVP.Sample.response_models.UserInfo;
import com.MVP.Sample.utils.Constants;
import com.MVP.Sample.utils.SharedPrefHelper;
import com.MVP.Sample.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginPresenter.LoginView {

    private static final String TAG = LoginActivity.class.getCanonicalName();

    @BindView(R.id.edtEmailLogin)
    EditText edtEmailLogin;
    @BindView(R.id.edtPasswordLogin)
    EditText edtPasswordLogin;
    @BindView(R.id.checkBoxRemenberMe)
    RadioButton checkBoxRemenberMe;

    //Implementer to create business logic for same
    private LoginPresenterImpl presenter;
    //Model for store data of Request Result
    private LoginActivityModel model;

    //Shared Pref Helper
    private SharedPrefHelper sharedPrefHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get User data from pref
        UserInfo userInfo = MVPSampleApp.getLoggedInUser();
        //If exist move to main screen
        if (userInfo != null) {
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
            return;
        }
//
        ButterKnife.bind(this);
        presenter = new LoginPresenterImpl(this);
        model = new LoginActivityModel(this);

        sharedPrefHelper = new SharedPrefHelper(this);

        /*
         * Get and set user name and pass
         * Check user name and pass are exist in pref If user have remember
         */
        if (sharedPrefHelper.isRemembered()) {
            edtEmailLogin.setText(sharedPrefHelper.getRememberMe().get(Constants.SharedPrefKey.USER_EMAIL));
            edtPasswordLogin.setText(sharedPrefHelper.getRememberMe().get(Constants.SharedPrefKey.USER_PASS));
            checkBoxRemenberMe.setChecked(true);
        }
    }

    @OnClick({R.id.btn_login_LoginActivity, R.id.txtForgotPasswordLoginActivity})
    public void onViewClicked(View v) {

        switch (v.getId()) {

            //Go to forgot password
            case R.id.txtForgotPasswordLoginActivity:
                break;

            /*
             *Request for login
             */
            case R.id.btn_login_LoginActivity:
                doRetrieveModel().setRequestLogin(edtEmailLogin.getText().toString().trim(), edtPasswordLogin.getText().toString().trim());
                presenter.doLogin();

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * Register event bus
         */
        presenter.registerBus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
         * Unregister event bus
         */
        presenter.unRegisterBus();
    }

    /**
     * Show Snack bar
     * IF isTypeError = true it is error else success msg
     *
     * @param message
     */
    @Override
    public void showError(String message) {
        Utility.showSnackBar(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG, true, this);
    }

    /**
     * Show Snack bar
     * IF isTypeError = true it is error else success msg
     *
     * @param message
     */
    @Override
    public void showSnackBar(String message) {
        Utility.showSnackBar(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT, true, this);
    }

    /**
     * Show toast
     *
     * @param message
     */
    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * show progress dialog
     */
    @Override
    public void showProgress() {
        Utility.showProgressDialog(this, "");
    }

    /**
     * Hide Progress Dialog
     */
    @Override
    public void hideProgress() {
        Utility.hideProgressDialog();
    }


    /**
     * Get current Activity
     *
     * @return
     */
    @Override
    public Activity getViewActivity() {
        return this;
    }

    /**
     * Fire when network changed
     *
     * @param isConnect
     */
    @Override
    public void onNetworkStateChange(boolean isConnect) {

    }

    /**
     * Fire when successful LOGIN
     */
    @Override
    public void onLoginSuccessful(LoginResponse loginResponse) {

        UserInfo data = loginResponse.getData();
        if (data != null && data.getApiKey() != null) {
            MVPSampleApp.setLoggedInUser(data);
            MVPSampleApp.resetClient();

            /*
             * Save Remember Me if RememberMe is ON
             */
            if (checkBoxRemenberMe.isChecked()) {
                sharedPrefHelper.setRememberMe(edtEmailLogin.getText().toString().trim(), edtPasswordLogin.getText().toString().trim());
            } else {
                sharedPrefHelper.clearRememberMe();
            }
            /*
             * Move to Main Activity
             * With Clear all previous activities
             */
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        } else {
            showError(loginResponse.getMessage());
        }
    }

    /**
     * Get Current Activity Model
     * @return
     */
    @Override
    public LoginActivityModel doRetrieveModel() {
        return this.model;
    }

}
