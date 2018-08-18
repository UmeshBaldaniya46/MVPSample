package com.MVP.Sample;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.MVP.Sample.APIHelper.RequestInterceptor;
import com.MVP.Sample.response_models.UserInfo;
import com.MVP.Sample.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class MVPSampleApp extends Application {

    //Instance of Application Class
    private static MVPSampleApp mInstance;

    //Instance of Gson
    private static Gson mGson;

    //Instance of Gson without Expose
    private static Gson mGsonExcludeExpose;

    //Instance of OkHttpclient
    private static OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Method for get current instance of Application
     *
     * @return
     */
    public static MVPSampleApp getAppInstance() {
        return mInstance;
    }

    /**
     * Method for get synchronized instance of Application
     *
     * @return
     */
    public static synchronized MVPSampleApp getInstance() {
        return mInstance;
    }

    /**
     * Method for Create OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getClient() {

        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            RequestInterceptor m1RequestInterceptor = new RequestInterceptor();
            client = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .addInterceptor(m1RequestInterceptor)
                    .build();
        }
        return client;
    }

    /**
     * Method for get data in Preference
     *
     * @param name
     * @return
     */
    public static SharedPreferences getSharedPreference(String name) {
        SharedPreferences preferences = getAppInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences;
    }

    /**
     * Method for get SharedPreferences for edit
     *
     * @param name
     * @return
     */
    public static SharedPreferences.Editor getSharedPreferenceEditor(String name) {
        SharedPreferences preferences = getAppInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }

    /**
     * Method for Gson With Expose
     *
     * @return
     */
    public static Gson getGsonWithExpose() {
        if (mGson != null) {
            mGson = null;
        }
        mGson = new GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create();
        return mGson;
    }

    /**
     * Method for Gson Without Expose
     *
     * @return
     */
    public static Gson getGsonWithoutExpose() {

        if (mGsonExcludeExpose != null) {
            mGsonExcludeExpose = null;
        }
        mGsonExcludeExpose = new GsonBuilder().setLenient().create();
        return mGsonExcludeExpose;
    }

    /**
     * Method for get Login User data from Preferences
     *
     * @return
     */
    public static UserInfo getLoggedInUser() {
        SharedPreferences sharedPref = getSharedPreference(Constants.SharedPref.PREF_FILE);
        String user = sharedPref.getString(Constants.SharedPrefKey.LOGGEDIN_USER, "");

        return getGsonWithExpose().fromJson(user, UserInfo.class);
    }


    /**
     * Method for save Login User data in to Preferences
     *
     * @return
     */
    public static void setLoggedInUser(UserInfo user) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPreferenceEditor(Constants.SharedPref.PREF_FILE);
        sharedPrefEditor.putString(Constants.SharedPrefKey.LOGGEDIN_USER, getGsonWithExpose().toJson(user)).apply();
    }

    /**
     * Method for save Login User data in to Preferences
     *
     * @return
     */
    public void setShowCas(String id) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPreferenceEditor(Constants.SharedPref.PREF_FILE);
        sharedPrefEditor.putBoolean(id, true).apply();
    }

    public Boolean getShowCase(String id) {
        SharedPreferences sharedPref = getSharedPreference(Constants.SharedPref.PREF_FILE);
        return sharedPref.getBoolean(id, false);
    }

    /**
     * Reset OkHttpclient
     */
    public static void resetClient() {
        client = null;
        getClient();
    }
}
