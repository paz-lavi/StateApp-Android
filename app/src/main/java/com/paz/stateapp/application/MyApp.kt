package com.paz.stateapp.application

import android.app.Application
import com.paz.stateapp.data_manager.DataManager

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DataManager.startNetworkTask(context = applicationContext)
    }
}