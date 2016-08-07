package com.techtask.android.picscramble.model;

/**
 * Model class for Photos
 */
public class PhotoInfo {
    private int id;
    private String title;
    private String url;

    public PhotoInfo(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
