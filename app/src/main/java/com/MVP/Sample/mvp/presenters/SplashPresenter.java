package com.MVP.Sample.mvp.presenters;

import android.content.Intent;
import android.widget.TextView;

import com.MVP.Sample.baseframework.mvp.BasePresenter;
import com.MVP.Sample.baseframework.mvp.BaseView;

public interface SplashPresenter extends BasePresenter {
    interface SplashView extends BaseView {
        void onRedirect(Intent intent);
    }

    void gotoLogin();

    void fadeOutAndHideImage(TextView textView);

    void gotoHome();
}
