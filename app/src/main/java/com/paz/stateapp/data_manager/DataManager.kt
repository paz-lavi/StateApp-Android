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
class DataManager private constructor() {

    private object HOLDER {
        val INSTANCE = DataManager()
        lateinit var countries: ArrayList<CountryModel?>


        /** Check if a lateinit var (countries) is already initialized */
        fun isInit(): Boolean {
            return this::countries.isInitialized
        }

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
        fun getCountries(context: Context) {
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
    }

    companion object {
        val instance: DataManager by lazy {
            HOLDER.INSTANCE
        }
    }

    fun startNetworkTask(context: Context) {
        HOLDER.getCountries(context)
    }

    /** check if countries data id ready */
    fun isDataReady(): Boolean {
        return if (HOLDER.isInit())
            !HOLDER.countries.isNullOrEmpty()
        else false
    }

    /** get countries data list*/
    fun getCountriesList(): ArrayList<CountryModel?> {
        return HOLDER.countries
    }
}