package com.example.owner.myapplication.api;

import com.example.owner.myapplication.model.NearbyPlace;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET("/maps/api/place/nearbysearch/json")
    Observable<NearbyPlace> getNearbyPlaces(
            @Query("location") String location,
            @Query("radius") String radius,
            @Query("key") String key);
}
