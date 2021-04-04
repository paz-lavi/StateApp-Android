package com.paz.stateapp.data_manager

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.paz.stateapp.callbacks.DataReadyCallback
import com.paz.stateapp.model.ApiResponse
import com.paz.stateapp.model.CountryModel
import com.paz.stateapp.model.ResponseType
import com.paz.stateapp.rest.RestClient

/** A Singleton class to that hold all the countries data.
 * Perform the API request only once */
object DataManager {
    var countries: ArrayList<CountryModel?>? = null

    fun sendBroadcast(context: Context, res: ApiResponse) {
        Intent().also { intent ->
            intent.action = "onDataReady"
            intent.putExtra("res", Gson().toJson(res))
            LocalBroadcastManager.getInstance(context.applicationContext)
                .sendBroadcast(intent)
        }
    }


    /** perform the API request task.
     * save results*/
    private fun getCountries(context: Context) {
        RestClient.getCountryData(object : DataReadyCallback {
            override fun onDataReady(list: ArrayList<CountryModel?>) {
                countries = list
                val res = ApiResponse(null, null, ResponseType.SUCCESS)
                sendBroadcast(context, res)
            }

            override fun onError(statusCode: Int, errorMsg: String) {
                val res = ApiResponse(statusCode, errorMsg, ResponseType.ERROR)
                sendBroadcast(context, res)
            }

            override fun onFailure(errorMsg: String) {
                val res = ApiResponse(null, errorMsg, ResponseType.EXCEPTION)
                sendBroadcast(context, res)
            }
        })
    }


    fun startNetworkTask(context: Context) {
        getCountries(context)
    }

    /** check if countries data id ready */
    fun isDataReady(): Boolean {
        return !countries.isNullOrEmpty()
    }

    /** get countries data list*/
    fun getCountriesList(): ArrayList<CountryModel?>? {
        return countries
    }
}