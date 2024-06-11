package com.jambox.monetisationdemoapp;

import android.app.Application;

import com.jambox.monetisation.AdjustHelper;

public class MyApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        AdjustHelper.Initialize(this, "uw277yqlu1hc");
    }
}
