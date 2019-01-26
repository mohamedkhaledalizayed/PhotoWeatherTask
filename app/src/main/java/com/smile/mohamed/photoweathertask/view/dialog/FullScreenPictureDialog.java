package com.smile.mohamed.photoweathertask.view.dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smile.mohamed.photoweathertask.R;

public class FullScreenPictureDialog extends DialogFragment {


    private String imageUrl;
    public static FullScreenPictureDialog newInstance(String url) {
        FullScreenPictureDialog frag = new FullScreenPictureDialog();
        Bundle args = new Bundle();
        args.putString("url", url);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getArguments().getString("url");

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_picture_dialog, container, false);

        ImageView imageView=view.findViewById(R.id.full_picture);
        imageView.setImageURI(Uri.parse(imageUrl));
        // Do all the stuff to initialize your custom view

        return view;    }


}
