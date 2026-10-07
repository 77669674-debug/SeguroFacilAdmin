package com.example.segurofaciladmin.data.local.dao

import androidx.room.*
import com.example.segurofaciladmin.data.local.entities.CotizacionEntity

@Dao
interface CotizacionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(cotizaciones: List<CotizacionEntity>)

    @Query("SELECT * FROM cotizaciones_local ORDER BY fecha DESC")
    suspend fun obtenerTodas(): List<CotizacionEntity>
}