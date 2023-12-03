package com.example.caroproject.Data;

public class Background extends StoreItem{
    private int tempImage;
    private int layoutBackground;

    public int getTempImage() {
        return tempImage;
    }

    public void setTempImage(int tempImage) {
        this.tempImage = tempImage;
    }

    public int getLayoutBackground() {
        return layoutBackground;
    }

    public void setLayoutBackground(int layoutBackground) {
        this.layoutBackground = layoutBackground;
    }

    public Background(int tempImage, int layoutBackground) {
        this.tempImage = tempImage;
        this.layoutBackground = layoutBackground;
    }

    public Background(Boolean status, int tempImage, int layoutBackground) {
        super(status);
        this.tempImage = tempImage;
        this.layoutBackground = layoutBackground;
    }
}
