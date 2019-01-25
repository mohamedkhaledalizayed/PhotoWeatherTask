package com.smile.mohamed.photoweathertask.services;

import com.smile.mohamed.photoweathertask.services.response.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET("weather")
    Call<WeatherResponse> getWeatherData(@Query("lat") Double lang, @Query("lon") Double lon, @Query("appid") String api);
}
