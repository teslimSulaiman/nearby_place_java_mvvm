package com.example.owner.myapplication.mapper;

import com.example.owner.myapplication.model.NearbyPlace;
import com.example.owner.myapplication.model.NearbyPlaceInfo;
import com.example.owner.myapplication.model.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NearbyPlaceInfoMapper {


    public NearbyPlaceInfoMapper() {
    }

    public List<NearbyPlaceInfo> mapNearbyPlaceInfo(NearbyPlace response) {
        List<NearbyPlaceInfo> nearbyPlaceList = new ArrayList<>();

        if (response != null) {
            List<Result> nearbyPlaceResult = response.getResults();
            if (nearbyPlaceResult != null) {
                for (Result result  : nearbyPlaceResult) {
                    NearbyPlaceInfo nearbyPlaceInfo = new NearbyPlaceInfo();
                    nearbyPlaceInfo.setName(result.getName());
                    nearbyPlaceInfo.setIcon(result.getIcon());
                    nearbyPlaceInfo.setVicinity(result.getVicinity());
                    nearbyPlaceInfo.setLatitude(result.getGeometry().getLocation().getLat());
                    nearbyPlaceInfo.setLongitude(result.getGeometry().getLocation().getLng());
                    String photoLink ="";
                    if(result.getPhotos() != null){

                        photoLink = result.getPhotos().get(0).getPhotoReference();
                    }
                    nearbyPlaceInfo.setPhoto(photoLink);
                    nearbyPlaceList.add(nearbyPlaceInfo);
                }
            }
        }
        return nearbyPlaceList;
    }
}
