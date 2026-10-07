package com.example.segurofaciladmin.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PolizaDto(
    val id: Int? = null,
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("planId") val planId: Int,
    @SerializedName("cotizacionId") val cotizacionId: Int?,
    val estado: String,
    @SerializedName("uuid_operacion") val uuidOperacion: String
)