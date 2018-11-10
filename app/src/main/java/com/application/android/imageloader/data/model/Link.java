package com.application.android.imageloader.data.model;

import com.google.gson.annotations.SerializedName;

public class Link {
    @SerializedName("html")
    private String html;
    @SerializedName("self")
    private String self;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
