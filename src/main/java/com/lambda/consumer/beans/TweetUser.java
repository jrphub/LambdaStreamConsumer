package com.lambda.consumer.beans;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TweetUser implements Serializable {

    private static final long serialVersionUID = -1169946739935538169L;

    private String location;

    private boolean geo_enabled;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public void setGeo_enabled(boolean geo_enabled) {
        this.geo_enabled = geo_enabled;
    }
}
