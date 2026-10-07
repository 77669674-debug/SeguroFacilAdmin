package com.example.segurofaciladmin.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "polizas_local")
data class PolizaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,
    val planId: Int,
    val cotizacionId: Int?,
    val estado: String
)