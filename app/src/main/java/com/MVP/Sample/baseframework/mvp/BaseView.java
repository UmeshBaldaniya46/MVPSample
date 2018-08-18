package com.MVP.Sample.baseframework.mvp;

import android.app.Activity;

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showToast(String message);

    void showError(String message);

    void showSnackBar(String message);

    Activity getViewActivity();

    void onNetworkStateChange(boolean isConnect);

}
