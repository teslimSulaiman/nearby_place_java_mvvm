package com.example.owner.myapplication.di;

import android.content.Context;

import com.example.owner.myapplication.mapper.NearbyPlaceInfoMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    NearbyPlaceInfoMapper provideNearbyPlaceInfoMapper(){
        return new NearbyPlaceInfoMapper();
    }
}
