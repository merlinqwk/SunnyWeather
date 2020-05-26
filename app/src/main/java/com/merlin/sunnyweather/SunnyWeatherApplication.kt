package com.merlin.sunnyweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    companion object{
        lateinit var context: Context
        const val TOKEN = "n6U5AaNTcHPofB3m"
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}