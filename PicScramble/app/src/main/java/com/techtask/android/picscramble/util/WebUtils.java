package com.techtask.android.picscramble.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebUtils {
    public static boolean hasNetConnectivity(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
