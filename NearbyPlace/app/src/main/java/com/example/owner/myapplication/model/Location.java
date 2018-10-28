
package com.example.owner.myapplication.model;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Location {

    @Expose
    private Double lat;
    @Expose
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public static class Builder {

        private Double lat;
        private Double lng;

        public Location.Builder withLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public Location.Builder withLng(Double lng) {
            this.lng = lng;
            return this;
        }

        public Location build() {
            Location location = new Location();
            location.lat = lat;
            location.lng = lng;
            return location;
        }

    }

}
