package com.smile.mohamed.photoweathertask.app;

import android.app.Application;

import com.smile.mohamed.photoweathertask.services.RetrofitModule;

public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitModule.intialize(this);

    }
}
