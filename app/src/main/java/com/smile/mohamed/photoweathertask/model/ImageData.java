package com.smile.mohamed.photoweathertask.model;

import android.graphics.Bitmap;

public class ImageData{
    public String url;
    public Bitmap bitmap;

    public ImageData(String url, Bitmap bitmap) {
        this.url = url;
        this.bitmap = bitmap;
    }
}
