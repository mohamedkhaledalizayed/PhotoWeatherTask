package com.smile.mohamed.photoweathertask.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.smile.mohamed.photoweathertask.R;
import com.smile.mohamed.photoweathertask.databinding.ActivityHomeBinding;
import com.smile.mohamed.photoweathertask.model.ImageData;
import com.smile.mohamed.photoweathertask.services.response.WeatherResponse;
import com.smile.mohamed.photoweathertask.utils.AppUtils;
import com.smile.mohamed.photoweathertask.viewmodel.AddPictureViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import dmax.dialog.SpotsDialog;

import static com.smile.mohamed.photoweathertask.data.Constants.TAKE_PICTURE_REQUEST;
import static com.smile.mohamed.photoweathertask.utils.AppUtils.showAlert;

public class AddPictureActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Double lat;
    private Double lan;
    private String image_url;
    private AddPictureViewModel viewModel;
    private ActivityHomeBinding binding;
    private LocationCallback locationCallback;
    private android.app.AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        dialog = new SpotsDialog.Builder()
                .setContext(this).setMessage(R.string.please_wait)
                .setTheme(R.style.Custom).build();

        binding.sharePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        binding.takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        String[] permissions = {Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                AppUtils.showSuccessToast(AddPictureActivity.this,"Permissions Granted");
                checkGPSStatus();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                AppUtils.showInfoToast(AddPictureActivity.this,"Permissions Denied");
                }
        }

        );
    }

    @SuppressLint("MissingPermission")
    private void checkGPSStatus() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager != null) {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
               showAlert(this);
            } else{

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            lat = location.getLatitude();
                            lan = location.getLongitude();
                            startCaptureImage();
                        }
                        else{
                            requestLocationUpdates();
                        }

                    }
                });
            }
        } else{
            AppUtils.showErrorToast(this,getString(R.string.error));
        }

    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        startCaptureImage();
                        lat = location.getLatitude();
                        lan = location.getLongitude();
                        if (fusedLocationProviderClient != null)
                            fusedLocationProviderClient.removeLocationUpdates(locationCallback);

                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void startCaptureImage() {
        //it is solve this problem : exposed beyond app through ClipData.Item.getUri
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //i cope it from stackoverflow and the link below
        //https://stackoverflow.com/questions/48117511/exposed-beyond-app-through-clipdata-item-geturi?answertab=votes#tab-top
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File out = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        out = new File(out, "IMAGE_"+new Date()+".jpg");
        // I save image url to use the same url to delete the old one and save the image with text
        image_url=out.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent,TAKE_PICTURE_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.history){
            startActivity(new Intent(this,HistoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==TAKE_PICTURE_REQUEST){
            getWeather(lat,lan);
        }
    }

    private Bitmap ProcessingBitmap(String captionString) {
        Bitmap bitmap = null;
        Bitmap newBitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(image_url))));
            Bitmap.Config config = bitmap.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bitmap, 0, 0, null);
            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setColor(Color.BLUE);
            paintText.setTextSize(200);
            paintText.setStyle(Paint.Style.FILL);
            paintText.setShadowLayer(10f, 10f, 10f, Color.BLACK);
            Rect textRect = new Rect();
            paintText.getTextBounds(captionString, 0, captionString.length(), textRect);
            canvas.drawText(captionString, 300, 900, paintText);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newBitmap;
    }

    public void getWeather(Double lat, Double lan){
        dialog.show();
        viewModel = ViewModelProviders.of(this).get(AddPictureViewModel.class);
        viewModel.getWeatherData(lat,lan).observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(@Nullable WeatherResponse weatherResponse) {
                if (weatherResponse==null){
                    dialog.dismiss();
                }else {
                    String text = weatherResponse.getName()+" : "
                            + weatherResponse.getWeather().get(0).getDescription()+" : "
                            +weatherResponse.getMain().getTemp();
                    writeWeatherOnPicture(text);
                }
            }
        });
    }

    private void writeWeatherOnPicture(String text) {

        Bitmap bitmap = ProcessingBitmap(text);
        ImageData dat=new ImageData(image_url,bitmap);
        new SavePicture().execute(dat);

    }

    private class SavePicture extends AsyncTask<ImageData, Void, String> {

        @Override
        protected String doInBackground(ImageData... imageData) {

            OutputStream fOut = null;
            File file = new File(imageData[0].url);
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageData[0].bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return file.getAbsolutePath();
        }

        @Override
        protected void onPostExecute(String url) {
            binding.addImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            binding.addImage.setImageURI(Uri.parse(url));
            binding.sharePicture.setVisibility(View.VISIBLE);
            dialog.dismiss();
        }
    }

    public void share(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        final File photoFile = new File(image_url);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
        startActivity(Intent.createChooser(shareIntent, "Share image using"));
    }
}
