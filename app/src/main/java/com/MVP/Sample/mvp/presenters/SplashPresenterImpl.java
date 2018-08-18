package com.MVP.Sample.mvp.presenters;

import android.content.Intent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.MVP.Sample.ui.activities.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SplashPresenterImpl implements SplashPresenter {
    SplashView mView;
    private EventBus mEventBus;

    public SplashPresenterImpl(SplashView mView) {
        this.mView = mView;
        mEventBus = EventBus.getDefault();
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    gotoLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


    @Subscribe
    public void onErr(String string) {
        //TODO this method is used to handle event bus object only
    }


    @Override
    public void onError(String message) {

    }

    @Override
    public void registerBus() {
        mEventBus.register(this);

    }


    @Override
    public void unRegisterBus() {
        mEventBus.unregister(this);
    }


    @Override
    public void gotoLogin() {

        Intent intent;
        intent = new Intent(mView.getViewActivity(), LoginActivity.class);
        mView.onRedirect(intent);
        mView.getViewActivity().finish();
    }

    @Override
    public void gotoHome() {

    }

    /**
     * For set fade of application icon on plash screen
     *
     * @param appName
     */
    @Override
    public void fadeOutAndHideImage(final TextView appName) {
        Animation fadeOut = new AlphaAnimation(1, 0.0f);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(2000);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                //img.setVisibility(View.GONE);
                appName.setAlpha(0.0f);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        appName.startAnimation(fadeOut);
    }

}
