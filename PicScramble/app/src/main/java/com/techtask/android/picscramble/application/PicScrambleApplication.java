package com.techtask.android.picscramble.application;

import android.app.Application;
import android.content.Context;

public class PicScrambleApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }
}
