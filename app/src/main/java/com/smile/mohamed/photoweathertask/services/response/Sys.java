package com.smile.mohamed.photoweathertask.services.response;

import com.google.gson.annotations.SerializedName;

public class Sys{

	@SerializedName("country")
	private String country;

	@SerializedName("sunrise")
	private double sunrise;

	@SerializedName("sunset")
	private double sunset;

	@SerializedName("message")
	private double message;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setSunrise(double sunrise){
		this.sunrise = sunrise;
	}

	public double getSunrise(){
		return sunrise;
	}

	public void setSunset(double sunset){
		this.sunset = sunset;
	}

	public double getSunset(){
		return sunset;
	}

	public void setMessage(double message){
		this.message = message;
	}

	public double getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Sys{" + 
			"country = '" + country + '\'' + 
			",sunrise = '" + sunrise + '\'' + 
			",sunset = '" + sunset + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}