package com.MVP.Sample.APIHelper;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.MVP.Sample.BuildConfig;
import com.MVP.Sample.MVPSampleApp;
import com.MVP.Sample.baseframework.models.APIError;
import com.MVP.Sample.baseframework.models.GenericModel;
import com.MVP.Sample.response_models.LoginRequest;
import com.MVP.Sample.response_models.LoginResponse;
import com.MVP.Sample.utils.Utility;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;

import java.net.SocketTimeoutException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.flowable.FlowableAmb;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApisImpl {

    private EventBus mEventBus;
    private RestApis mRestApis;

    public RestApisImpl(EventBus mEventBus) {
        this.mEventBus = mEventBus;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(MVPSampleApp.getClient())
                .build();

        mRestApis = retrofit.create(RestApis.class);
    }

    @SuppressLint("CheckResult")
    public void doLogin(LoginRequest reqString) {


/*        {
                "code": 200,
                "url": "Users/commonlogin.json",
                "message": "Login successfully.",
                "data": {
                    "Users": {
                        "id": 145,
                        "workshop_id": 41,
                        "firstname": "dev",
                        "lastname": "cust",
                        "username": "devcust",
                        "email": "devcust@gmail.com",
                        "user_profile": "",
                        "modified": "2018-12-26T08:57:27"
                            }
                        }
        }
*/

        /*
         * For this Architecture API response are same as above
         * Like code, message, and data(Json object) are fixed
         * Users data is different by api
         * Users should be any things JsonArray or JsonObject
         */

        Flowable<GenericModel<LoginResponse>> getusers = mRestApis.doLogin(Utility.getRequest(MVPSampleApp.getGsonWithExpose().toJson(reqString)));
        getusers.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    try {
                        String message = ((HttpException) throwable).response().errorBody().string();
                        GenericModel<LoginResponse> userBaseResponse = MVPSampleApp.getGsonWithExpose().fromJson(message, GenericModel.class);
                        return userBaseResponse;
                    } catch (SocketTimeoutException e) {
                        GenericModel<LoginResponse> asd = new GenericModel<>();
                        asd.setStatus(100);
                        asd.setMessage("Server not responding");
                        return asd;
                    } catch (Exception e) {
                        GenericModel<LoginResponse> asd = new GenericModel<>();
                        asd.setStatus(100);
                        asd.setMessage("Something went wrong");
                        return asd;
                    }
                })
                .subscribe(responsData -> {
                    if (responsData != null) {
                        GenericModel<LoginResponse> responseGenericModel = responsData;
                        if ((responseGenericModel.getStatus() == 200) && (responseGenericModel.getResponse() != null)) {
                            LoginResponse loginResponse = responseGenericModel.getResponseModel(LoginResponse.class);
                            loginResponse.setMessage(responseGenericModel.getMessage());
                            mEventBus.post(loginResponse);
                        } else {
                            mEventBus.post(new APIError(105, responseGenericModel.getMessage()));
                        }
                    }
                });
    }

}
