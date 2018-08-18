package com.MVP.Sample.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

import org.greenrobot.eventbus.EventBus;

public class ConnectivityReceiver extends BroadcastReceiver {
    public static final String NETWORK_AVAILABLE_ACTION = "Connectivity Receiver";
    public static final String IS_NETWORK_AVAILABLE = "isNetworkAvailable";
//    public static ConnectivityReceiverListener connectivityReceiverListener;

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
//        int type = getConnectivityStatus(context);
//        boolean isConnected = false;
//        if (type != TYPE_NOT_CONNECTED)
//            isConnected = true;

        boolean isConnected = isNetworkConnected(context);
        EventBus.getDefault().postSticky(isConnected);
        Intent networkStateIntent = new Intent(NETWORK_AVAILABLE_ACTION);
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, isConnected);
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent);
//        if (connectivityReceiverListener != null) {
//            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
//        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}
