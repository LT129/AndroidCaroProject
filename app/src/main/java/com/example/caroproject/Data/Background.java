package com.example.caroproject.Data;

public class Background {
    private int tempImage;
    private int layoutBackground;
    private int imageButtonBackground;
    private int textViewBackground;

    public Background(int tempImage, int layoutBackground, int imageButtonBackground, int textViewBackground) {
        this.tempImage = tempImage;
        this.layoutBackground = layoutBackground;
        this.imageButtonBackground = imageButtonBackground;
        this.textViewBackground = textViewBackground;
    }

    public int getLayoutBackground() {
        return layoutBackground;
    }

    public void setLayoutBackground(int layoutBackground) {
        this.layoutBackground = layoutBackground;
    }

    public int getImageButtonBackground() {
        return imageButtonBackground;
    }

    public void setImageButtonBackground(int imageButtonBackground) {
        this.imageButtonBackground = imageButtonBackground;
    }

    public int getTextViewBackground() {
        return textViewBackground;
    }

    public void setTextViewBackground(int textViewBackground) {
        this.textViewBackground = textViewBackground;
    }

    public int getTempImage() {
        return tempImage;
    }

    public void setTempImage(int tempImage) {
        this.tempImage = tempImage;
    }
}
