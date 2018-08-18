package com.MVP.Sample.baseframework.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.MVP.Sample.R;
import com.MVP.Sample.utils.Utility;


public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    BroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onNetworkStateChange(isNetworkConnected(BaseActivity.this));
                Log.e("status", String.valueOf(isNetworkConnected(BaseActivity.this)));
            }
        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_trans_right_in, R.anim.anim_trans_right_out);
    }

    @Override
    protected void onResume() {
        this.registerReceiver(receiver, intentFilter);
        super.onResume();

    }

    @Override
    public void showProgress() {
        Utility.showProgressDialog(getViewActivity(), "");
    }

    @Override
    protected void onPause() {
        this.unregisterReceiver(receiver);
        super.onPause();

    }

    @Override
    public void hideProgress() {
        Utility.hideProgressDialog();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Gets the fragment manager object of activity required for fragment transaction
     * <p>This method can be customised on the need of application,in which it returns {@link FragmentManager}</p>
     *
     * @return object of {@link android.app.FragmentManager} or {@link FragmentManager}
     */
    public FragmentManager getLocalFragmentManager() {
        return this.getSupportFragmentManager();
    }

    public void setViewVisibility(View viewIn, View viewOut) {

        Animation animation_fade_in = AnimationUtils.loadAnimation(getViewActivity(), android.R.anim.fade_in);
        Animation animation_fade_out = AnimationUtils.loadAnimation(getViewActivity(), android.R.anim.fade_out);

        viewIn.setVisibility(View.VISIBLE);
        viewIn.startAnimation(animation_fade_in);
        viewOut.startAnimation(animation_fade_out);
        viewOut.setVisibility(View.GONE);

    }

}
