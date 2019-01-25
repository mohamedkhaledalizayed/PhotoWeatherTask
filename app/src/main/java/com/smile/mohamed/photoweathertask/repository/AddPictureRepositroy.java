package com.smile.mohamed.photoweathertask.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.smile.mohamed.photoweathertask.services.RetrofitModule;
import com.smile.mohamed.photoweathertask.services.ServiceApi;
import com.smile.mohamed.photoweathertask.services.response.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smile.mohamed.photoweathertask.data.Constants.API_KEY;

public class AddPictureRepositroy {

    public static ServiceApi service= RetrofitModule.getInstance().getService();

    final MutableLiveData<WeatherResponse> data = new MutableLiveData<>();

    public LiveData<WeatherResponse> getWeatherData(Double lat, Double lan){

        service.getWeatherData(lat,lan,API_KEY).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.e("True",response.body().getName()+"");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });

        return data;
    }
}
