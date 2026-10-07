package com.example.segurofaciladmin.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CotizacionDto(
    val id: Int,
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("planId") val planId: Int,
    @SerializedName("primaEstimada") val primaEstimada: Double,
    val fecha: String
)