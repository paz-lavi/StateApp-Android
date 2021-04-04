package com.paz.stateapp.callbacks

import com.paz.stateapp.model.CountryModel

interface DataReadyCallback {
    fun onDataReady(list: ArrayList<CountryModel?>)
    fun onError(statusCode: Int, errorMsg: String)
    fun onFailure(errorMsg: String)

}