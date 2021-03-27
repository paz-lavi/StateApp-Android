package com.paz.stateapp.data_manager

import com.paz.stateapp.callbacks.DataReadyCallback
import com.paz.stateapp.model.CountryModel
import com.paz.stateapp.rest.RestClient

/** A Singleton class to that hold all the countries data.
 * Perform the API request only once */
class DataManager private constructor() {

    private object HOLDER {
        val INSTANCE = DataManager()
        lateinit var countries: ArrayList<CountryModel?>

        init {
            // perform the network request
            getCountries()
        }

        /** Check if a lateinit var (countries) is already initialized */
        fun isInit(): Boolean {
            return this::countries.isInitialized
        }

        /** perform the API request task.
         * save results*/
        private fun getCountries() {
            RestClient.getCountryData(object : DataReadyCallback {
                override fun onDataReady(list: ArrayList<CountryModel?>) {
                    countries = list
                }
            })
        }
    }

    companion object {
        val instance: DataManager by lazy {
            HOLDER.INSTANCE
        }
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