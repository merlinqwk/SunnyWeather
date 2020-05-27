package com.merlin.sunnyweather.logic

import androidx.lifecycle.liveData
import com.merlin.sunnyweather.logic.model.Place
import com.merlin.sunnyweather.logic.model.Weather
import com.merlin.sunnyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend() -> Result<T>) =
        liveData(context){
            val result = try{
                block()
            }catch (e: Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("searchPlaces failed," +
                    " response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String,lat: String) = fire(Dispatchers.IO){
        coroutineScope {
            val deferredRealTime = async{
                SunnyWeatherNetWork.getRealTimeWeather(lng,lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetWork.getDailyWeather(lng,lat)
            }
            val realTimeResponse = deferredRealTime.await()
            val dailyResponse = deferredDaily.await()
            if (realTimeResponse.status == "ok" && dailyResponse.status == "ok"){
                Result.success(Weather(realTimeResponse.result.realtime,
                    dailyResponse.result.daily))
            }else{
                Result.failure(RuntimeException("refreshWeather failed," +
                        "realTimeResponse status is ${realTimeResponse.status}," +
                        "dailyResponse status is${dailyResponse.status}"))
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

}

