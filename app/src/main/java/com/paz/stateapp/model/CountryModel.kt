package com.paz.stateapp.model

/** CountryModel data holder */
data class CountryModel(
    val name: String,
    val nativeName: String,
    val flag: String,
    val alpha3Code: String,
    val borders: List<String>
) {


}