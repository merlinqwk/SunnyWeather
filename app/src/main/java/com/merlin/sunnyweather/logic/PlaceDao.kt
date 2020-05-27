package com.merlin.sunnyweather.logic

import android.content.Context
import com.google.gson.Gson
import com.merlin.sunnyweather.SunnyWeatherApplication
import com.merlin.sunnyweather.logic.model.Place

object PlaceDao {

    fun savePlace(place: Place){
        sharedPreferences().edit().putString("place", Gson().toJson(place)).apply()
    }

    fun getSavedPlace() : Place{
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context
        .getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}