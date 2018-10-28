package com.example.owner.myapplication.api;

import android.support.annotation.Nullable;

import com.example.owner.myapplication.Utility;
import com.example.owner.myapplication.model.NearbyPlace;

import io.reactivex.annotations.NonNull;

public class ApiResponse {

    public String status;

    @Nullable
    public NearbyPlace data;

    @Nullable
    public final Throwable error;

    private ApiResponse(String status, @Nullable NearbyPlace data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(Utility.LOADING, null, null);
    }

    public static ApiResponse success(@NonNull NearbyPlace data) {
        return new ApiResponse(Utility.SUCCESS, data, null);
    }


    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(Utility.ERROR, null, error);
    }

}
