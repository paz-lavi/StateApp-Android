package com.paz.stateapp.rest

import com.paz.stateapp.model.CountryModel
import retrofit2.Call
import retrofit2.http.GET


interface RestService {
    @GET("rest/v2/all?fields=name;nativeName;borders;flag;alpha3Code")
    fun countryList(): Call<ArrayList<CountryModel?>?>?

}