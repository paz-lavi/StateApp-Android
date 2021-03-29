package com.paz.stateapp.application

import android.app.Application
import com.paz.stateapp.data_manager.DataManager

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // create an instance. this call will start the networking task (loading the data)
        DataManager.instance.startNetworkTask(context = applicationContext)
    }
}