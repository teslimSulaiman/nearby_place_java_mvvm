
package com.example.owner.myapplication.model;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Viewport {

    @Expose
    private Northeast northeast;
    @Expose
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public static class Builder {

        private Northeast northeast;
        private Southwest southwest;

        public Viewport.Builder withNortheast(Northeast northeast) {
            this.northeast = northeast;
            return this;
        }

        public Viewport.Builder withSouthwest(Southwest southwest) {
            this.southwest = southwest;
            return this;
        }

        public Viewport build() {
            Viewport viewport = new Viewport();
            viewport.northeast = northeast;
            viewport.southwest = southwest;
            return viewport;
        }

    }

}
