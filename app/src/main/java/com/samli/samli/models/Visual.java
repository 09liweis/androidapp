package com.samli.samli.models;

public class Visual {
    String id;
    String title;
    String doubanId;
    String poster;
    Double doubanRating;

    public Visual() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDoubanId() {
        return doubanId;
    }
    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public double getDoubanRating() {
        return doubanRating;
    }
    public void setDoubanRating(double doubanRating) {
        this.doubanRating = doubanRating;
    }
}
