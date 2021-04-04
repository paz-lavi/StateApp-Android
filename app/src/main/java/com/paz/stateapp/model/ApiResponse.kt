package com.paz.stateapp.model

data class ApiResponse(
    val statusCode: Int?,
    val errorMsg: String?,
    val type: ResponseType,
)
