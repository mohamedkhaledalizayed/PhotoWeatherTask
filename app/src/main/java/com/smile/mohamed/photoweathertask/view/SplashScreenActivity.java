package com.smile.mohamed.photoweathertask.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smile.mohamed.photoweathertask.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView imageView=findViewById(R.id.splash_image);

        Glide.with(this)
                .load(R.drawable.splash)
                .into(imageView);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(SplashScreenActivity.this,AddPictureActivity.class));
           }
       },3000);
    }
}
