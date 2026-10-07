package com.example.segurofaciladmin.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cotizaciones_local")
data class CotizacionEntity(
    @PrimaryKey val id: Int,
    val usuarioId: Int,
    val planId: Int,
    val primaEstimada: Double,
    val fecha: String
)