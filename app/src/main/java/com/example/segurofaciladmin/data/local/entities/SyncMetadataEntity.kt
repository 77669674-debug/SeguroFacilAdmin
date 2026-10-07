package com.example.segurofaciladmin.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_metadata")
data class SyncMetadataEntity(
    @PrimaryKey val recurso: String,
    val ultimaSincronizacionExitosa: Long,
    val cursorRemoto: String? = null
)