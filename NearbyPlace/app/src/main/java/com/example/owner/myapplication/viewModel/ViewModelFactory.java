package com.example.owner.myapplication.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.owner.myapplication.home.Repository;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NearbyPlaceViewModel.class)) {
            return (T) new NearbyPlaceViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
