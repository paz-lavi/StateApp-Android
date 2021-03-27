package com.paz.stateapp.rest

import android.util.Log
import com.paz.stateapp.callbacks.DataReadyCallback
import com.paz.stateapp.model.CountryModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {
    companion object {
        private val TAG = "${RestClient::class.java.canonicalName}_tag"

        /**
         * perform network request in order to get all the countries data
         * @param _mCallback - callback to notify the request finished with successful data*/
        fun getCountryData(_mCallback: DataReadyCallback) {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://restcountries.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(RestService::class.java)
            val call: Call<ArrayList<CountryModel?>?>? = service.countryList()

            call?.enqueue(object : Callback<ArrayList<CountryModel?>?> {
                override fun onResponse(
                    call: Call<ArrayList<CountryModel?>?>,
                    response: Response<ArrayList<CountryModel?>?>
                ) {
                    Log.d(TAG, "onResponse: ")
                    if (response.isSuccessful) {
                        Log.d(TAG, "isSuccessful: ")
                        // notify
                        _mCallback.onDataReady(response.body()!! )
                    } else {
                        Log.d(TAG, "error: ${response.raw()} ")
                    }
                }

                override fun onFailure(call: Call<ArrayList<CountryModel?>?>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.printStackTrace()}")
                }
            })
        }
    }
}