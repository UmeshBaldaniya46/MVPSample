package com.MVP.Sample.mvp.presenters;

import android.text.TextUtils;
import android.util.Log;

import com.MVP.Sample.APIHelper.RestApisImpl;
import com.MVP.Sample.R;
import com.MVP.Sample.baseframework.models.APIError;
import com.MVP.Sample.response_models.LoginRequest;
import com.MVP.Sample.response_models.LoginResponse;
import com.MVP.Sample.utils.ConnectivityReceiver;
import com.MVP.Sample.utils.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginPresenter.LoginView view;
    private RestApisImpl mRestApisImpl;
    private EventBus mEventBus;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        mEventBus = EventBus.getDefault();
        this.mRestApisImpl = new RestApisImpl(mEventBus);
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void registerBus() {
        mEventBus.register(this);
    }

    @Override
    public void unRegisterBus() {
        mEventBus.unregister(this);
    }


    @Subscribe(sticky = true)
    public void onHttpError(Throwable throwable) {
        view.showError(throwable.getCause().getLocalizedMessage());
        view.hideProgress();
        mEventBus.removeStickyEvent(throwable);
    }


    @Subscribe(sticky = true)
    public void onItemsResponse(APIError event) {
        view.showError(event.getHttpErrorMessage());
        view.hideProgress();
        mEventBus.removeStickyEvent(event);
    }


    @Subscribe(sticky = true)
    public void onEvent(LoginResponse event) {
        Log.e("event", event.toString());
        view.doRetrieveModel().mainActivityDomain.setUserResponse(event);
        view.hideProgress();
        if (event.getData() != null) {
            view.onLoginSuccessful(event);
        } else {
            view.showError(event.getMessage());
        }
        mEventBus.removeStickyEvent(event);

    }

    @Override
    public void doLogin() {
        if (ConnectivityReceiver.isNetworkConnected(view.getViewActivity())) {

            LoginRequest request = view.doRetrieveModel().getRequestLogin();

            Log.e("doLogin ", "request " + request.getEmail());
            Log.e("doLogin ", "request " + request.getPassword());

            Utility.hideKeyboard(view.getViewActivity());

            if (request.getEmail() == null || TextUtils.isEmpty(request.getEmail().trim())) {
                view.showError(view.getViewActivity().getString(R.string.label_enter_email_id));
            } else if (!Utility.checkEmail(request.getEmail().trim())) {
                view.showError(view.getViewActivity().getString(R.string.validation_Please_enter_valid_email_id));
            } else if (TextUtils.isEmpty(request.getPassword().trim())) {
                view.showError(view.getViewActivity().getString(R.string.label_enter_password));
            } else {
                view.showProgress();
                mRestApisImpl.doLogin(request);
            }
        } else {
            view.showError(view.getViewActivity().getString(R.string.no_internet_message));
        }
    }

}
