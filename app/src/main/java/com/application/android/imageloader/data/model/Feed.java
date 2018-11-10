package com.application.android.imageloader.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feed {
    @SerializedName("id")
    private String id;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("color")
    private String color;
    @SerializedName("likes")
    private int likes;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    @SerializedName("user")
    private User user;
    @SerializedName("current_user_collections")
    private List<String> currentUserCollections;
    @SerializedName("urls")
    private Url url;
    @SerializedName("categories")
    private List<Category> categories;
    @SerializedName("links")
    private Link link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getCurrentUserCollections() {
        return currentUserCollections;
    }

    public void setCurrentUserCollections(List<String> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }


}
