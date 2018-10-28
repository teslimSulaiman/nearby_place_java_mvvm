package com.example.owner.myapplication.home;

import com.example.owner.myapplication.api.ApiCallInterface;
import com.example.owner.myapplication.model.NearbyPlace;

import io.reactivex.Observable;

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<NearbyPlace> executeGetNearbyPlaces(String location, String radius, String key) {
        return apiCallInterface.getNearbyPlaces(location, radius, key);
    }

}
