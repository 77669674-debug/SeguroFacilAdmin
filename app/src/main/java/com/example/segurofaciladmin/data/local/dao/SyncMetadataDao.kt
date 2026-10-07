package com.example.segurofaciladmin.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.segurofaciladmin.data.local.entities.SyncMetadataEntity

@Dao
interface SyncMetadataDao {
    @Upsert
    suspend fun actualizar(metadata: SyncMetadataEntity)

    @Query("SELECT * FROM sync_metadata WHERE recurso = :recurso")
    suspend fun obtener(recurso: String): SyncMetadataEntity?
}