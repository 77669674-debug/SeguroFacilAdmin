package com.example.segurofaciladmin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.segurofaciladmin.data.local.entities.PolizaEntity

@Dao
interface PolizaDao {
    @Insert
    suspend fun insertar(poliza: PolizaEntity)
}