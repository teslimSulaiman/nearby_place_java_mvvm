package com.example.owner.myapplication.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.owner.myapplication.api.ApiResponse;
import com.example.owner.myapplication.home.Repository;
import com.example.owner.myapplication.model.NearbyPlace;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NearbyPlaceViewModel  extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private  MutableLiveData<ApiResponse> responseLiveData;

    public NearbyPlaceViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> fetchNearbyPlace() {

        if (responseLiveData == null) {
            responseLiveData = new MutableLiveData<ApiResponse>();
        }
        return responseLiveData;
    }

    public void getNearbyPlaces(String location, String radius, String key) {
        if(responseLiveData.getValue() != null)
            return;
        disposables.add(repository.executeGetNearbyPlaces(location, radius, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));

    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
