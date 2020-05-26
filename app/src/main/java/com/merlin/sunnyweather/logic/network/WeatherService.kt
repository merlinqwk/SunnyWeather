package com.merlin.sunnyweather.logic.network

import com.merlin.sunnyweather.SunnyWeatherApplication
import com.merlin.sunnyweather.logic.model.DailyResponse
import com.merlin.sunnyweather.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealTimeWeather(@Path("lng")lng: String, @Path("lat")lat: String) : Call<RealTimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String, @Path("lat")lat: String) : Call<DailyResponse>
}