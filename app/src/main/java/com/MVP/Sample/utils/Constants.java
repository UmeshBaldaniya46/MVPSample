package com.MVP.Sample.utils;


public class Constants {

    public interface SharedPref {
        String PREF_FILE = "RepairApp";
        String PREF_FILE_REMEMBER = "RepairAppRemember";
    }

    public interface SharedPrefKey {

        String IS_REMEMBER_ME = "isRememberMe";
        String USER_EMAIL = "userEmail";
        String USER_PASS = "userPassword";
        String LOGGEDIN_USER = "authorization";
    }

    public interface ApiMethods {

        String GET_LOGIN = "users/login.json";

    }

    public interface ApiHeaders {

        String API_TYPE_JSON = "application/json";
        String AUTHORIZATION = "authorization";
    }

}