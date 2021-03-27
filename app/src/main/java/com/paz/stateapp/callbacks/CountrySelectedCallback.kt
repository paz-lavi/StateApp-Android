package com.paz.stateapp.callbacks

import com.paz.stateapp.model.CountryModel

interface CountrySelectedCallback {
    fun onSelected(country: CountryModel)
}