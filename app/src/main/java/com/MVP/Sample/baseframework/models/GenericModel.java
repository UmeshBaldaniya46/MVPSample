package com.MVP.Sample.baseframework.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.MVP.Sample.MVPSampleApp;


public class GenericModel<Umesh> {

    private Umesh ResponseJson;

    @Expose
    @SerializedName("code")
    int code;

    @Expose
    @SerializedName("url")
    String url;

    @Expose
    @SerializedName("message")
    private
    String message;

    @Expose
    @SerializedName("response")
    private
    Object response;

    public Umesh getResponseModel(Class<Umesh> aModel) {
        setResponseJson(aModel);
        return ResponseJson;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void setResponseJson(Class<Umesh> aModel) {
        ResponseJson = prepareModel(response, aModel);
    }

    private <T> T prepareModel(@NonNull Object aString, Class<T> aClass) {
        return MVPSampleApp.getGsonWithExpose().fromJson(MVPSampleApp.getGsonWithExpose().toJson(aString), aClass);
    }


    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }


}
