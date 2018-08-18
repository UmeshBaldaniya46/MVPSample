package com.MVP.Sample.APIHelper;

import com.MVP.Sample.baseframework.models.GenericModel;
import com.MVP.Sample.response_models.LoginResponse;
import com.MVP.Sample.utils.Constants;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApis {

    @POST(Constants.ApiMethods.GET_LOGIN)
    Flowable<GenericModel<LoginResponse>> doLogin(@Body RequestBody requestBody);
//
//    @Multipart
//    @POST(Constants.ApiMethods.UPLOAD_IMAGE)
//    Flowable<GenericModel<LoginResponse>> uploadImage(@Part("id") RequestBody id,
//                                                      @Part("type") RequestBody type,
//                                                      @Part("profile_upload") RequestBody description,
//                                                      @Part MultipartBody.Part file);

}
