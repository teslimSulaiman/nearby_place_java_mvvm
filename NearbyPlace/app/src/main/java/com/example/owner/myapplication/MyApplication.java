package com.example.owner.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.owner.myapplication.di.AppComponent;
import com.example.owner.myapplication.di.AppModule;
import com.example.owner.myapplication.di.DaggerAppComponent;
import com.example.owner.myapplication.di.UtilsModule;

public class MyApplication  extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
