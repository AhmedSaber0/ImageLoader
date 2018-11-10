
package com.application.android.imageloader.data.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("photo_count")
    private int photoCount;
    @SerializedName("links")
    private Link link;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

}
