package com.MVP.Sample.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.util.HashMap;

public class SharedPrefHelper {

    private SharedPreferences preferences;
    private SharedPreferences preferencesRememberMe;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editorRemeberMe;
    private Context context;

    public SharedPrefHelper(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(Constants.SharedPref.PREF_FILE, Context.MODE_PRIVATE);
        preferencesRememberMe = context.getSharedPreferences(Constants.SharedPref.PREF_FILE_REMEMBER, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editorRemeberMe = preferencesRememberMe.edit();
    }

    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

    public void setRememberMe(String email, String password) {

        editorRemeberMe.putBoolean(Constants.SharedPrefKey.IS_REMEMBER_ME, true);
        editorRemeberMe.putString(encrypt(Constants.SharedPrefKey.USER_EMAIL), encrypt(email));
        editorRemeberMe.putString(encrypt(Constants.SharedPrefKey.USER_PASS), encrypt(password));
        editorRemeberMe.apply();
    }

    public boolean isRemembered() {
        return preferencesRememberMe.getBoolean(Constants.SharedPrefKey.IS_REMEMBER_ME, false);
    }

    public HashMap<String, String> getRememberMe() {
        HashMap<String, String> userCredentials = new HashMap<>();

        userCredentials.put(Constants.SharedPrefKey.USER_EMAIL, decrypt(preferencesRememberMe.getString(encrypt(Constants.SharedPrefKey.USER_EMAIL), "")));
        userCredentials.put(Constants.SharedPrefKey.USER_PASS, decrypt(preferencesRememberMe.getString(encrypt(Constants.SharedPrefKey.USER_PASS), "")));

        return userCredentials;
    }

    public void clearRememberMe() {
        editorRemeberMe.clear().apply();
    }
}