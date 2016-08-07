package com.techtask.android.picscramble.model;

public class ScrambleItem {
    private int id;
    private String imageUrl;

    public ScrambleItem(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
