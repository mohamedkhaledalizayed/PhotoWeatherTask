package com.smile.mohamed.photoweathertask.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.smile.mohamed.photoweathertask.repository.AddPictureRepositroy;
import com.smile.mohamed.photoweathertask.services.response.WeatherResponse;


public class AddPictureViewModel extends ViewModel {

    private LiveData<WeatherResponse> responseLiveData;

    public LiveData<WeatherResponse> getWeatherData(Double lat,Double lan){
        responseLiveData =new AddPictureRepositroy().getWeatherData(lat,lan);
        return responseLiveData;
    }


}
