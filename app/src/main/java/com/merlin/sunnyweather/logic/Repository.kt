package com.merlin.sunnyweather.logic

import androidx.lifecycle.liveData
import com.merlin.sunnyweather.logic.model.Place
import com.merlin.sunnyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO){
        val result = try{
            val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e : Exception){
            Result.failure(e)
        }
        emit(result as Result<List<Place>>)
    }
}

