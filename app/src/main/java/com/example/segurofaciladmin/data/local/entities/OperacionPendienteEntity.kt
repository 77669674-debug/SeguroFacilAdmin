package com.example.segurofaciladmin.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operaciones_pendientes")
data class OperacionPendienteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuidOperacion: String,
    val entidad: String,
    val tipoOperacion: String,
    val payload: String,
    val estado: String = "PENDIENTE",
    val intentos: Int = 0,
    val mensajeError: String? = null,
    val fechaRegistro: Long
)