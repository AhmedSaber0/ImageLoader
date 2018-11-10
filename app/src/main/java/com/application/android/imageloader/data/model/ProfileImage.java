package com.application.android.imageloader.data.model;

import com.google.gson.annotations.SerializedName;

public class ProfileImage {
    @SerializedName("large")
    private String large;
    @SerializedName("medium")
    private String medium;
    @SerializedName("small")
    private String small;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
