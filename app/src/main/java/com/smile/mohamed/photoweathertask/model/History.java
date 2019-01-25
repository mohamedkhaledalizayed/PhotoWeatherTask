package com.smile.mohamed.photoweathertask.model;

import android.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import carbon.widget.ImageView;

public class History {

    public History(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String imageUrl;

    @BindingAdapter("imageUrl")
    public static void loadImage(android.widget.ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }



}
