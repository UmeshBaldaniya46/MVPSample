package com.MVP.Sample.APIHelper;


import com.MVP.Sample.MVPSampleApp;
import com.MVP.Sample.utils.Constants;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {


    private String credentials = "";

    public RequestInterceptor() {

        if (MVPSampleApp.getLoggedInUser() != null) {
            this.credentials = Credentials.basic(MVPSampleApp.getLoggedInUser().getEmail(),
                    MVPSampleApp.getLoggedInUser().getApiPlainKey());
        }
    }

    @Override
    public Response intercept(Chain aChain) throws IOException {

        Request.Builder builder = aChain.request().newBuilder();
        builder.addHeader(Constants.ApiHeaders.AUTHORIZATION, credentials);
        Request request = builder.build();
        return aChain.proceed(request);
    }
}