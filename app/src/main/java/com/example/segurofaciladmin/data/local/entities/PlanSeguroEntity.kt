package com.example.segurofaciladmin.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planes_seguro_local")
data class PlanSeguroEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val tipoSeguro: String,
    val aseguradora: String,
    val cobertura: String,
    val primaBase: Double
)