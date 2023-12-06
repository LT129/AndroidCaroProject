package com.example.caroproject.Data;

public class Music extends StoreItem{
    private Integer sourceId;
    private String name;
    private int image;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Music(Integer sourceId, String name, int image) {
        this.sourceId = sourceId;
        this.name = name;
        this.image = image;
    }
}
