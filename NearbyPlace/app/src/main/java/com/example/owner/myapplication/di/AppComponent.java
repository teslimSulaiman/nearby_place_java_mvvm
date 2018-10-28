package com.example.owner.myapplication.di;

import com.example.owner.myapplication.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MainActivity mainActivity);

}
