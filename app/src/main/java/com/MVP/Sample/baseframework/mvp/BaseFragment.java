package com.MVP.Sample.baseframework.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.MVP.Sample.utils.Utility;

public class BaseFragment extends Fragment implements BaseView {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgress() {
        Utility.showProgressDialog(getViewActivity(), "");
    }

    @Override
    public void hideProgress() {
        Utility.hideProgressDialog();
    }

    @Override
    public void showToast(String message) {

        Utility.showSnackBar(getViewActivity().findViewById(android.R.id.content), message,
                Snackbar.LENGTH_LONG, false, getViewActivity());
        hideProgress();
    }

    @Override
    public void showError(String message) {
        if (message != null && !TextUtils.isEmpty(message)) {
            Utility.showSnackBar(getViewActivity().findViewById(android.R.id.content), message,
                    Snackbar.LENGTH_LONG, true, getViewActivity());
            hideProgress();
        }
    }

    @Override
    public void showSnackBar(String message) {
        Utility.showSnackBar(getViewActivity().findViewById(android.R.id.content), message,
                Snackbar.LENGTH_LONG, false, getViewActivity());
    }

    @Override
    public Activity getViewActivity() {
        return getActivity();
    }

    @Override
    public void onNetworkStateChange(boolean isConnect) {

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
