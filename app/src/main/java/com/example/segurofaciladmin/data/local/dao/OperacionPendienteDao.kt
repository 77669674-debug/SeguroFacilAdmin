package com.example.segurofaciladmin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.segurofaciladmin.data.local.entities.OperacionPendienteEntity

@Dao
interface OperacionPendienteDao {
    @Insert
    suspend fun insertar(operacion: OperacionPendienteEntity): Long

    @Query("SELECT * FROM operaciones_pendientes WHERE estado IN ('PENDIENTE', 'ERROR')")
    suspend fun obtenerPendientes(): List<OperacionPendienteEntity>

    @Query("UPDATE operaciones_pendientes SET estado = :estado, mensajeError = :mensajeError, intentos = intentos + 1 WHERE id = :id")
    suspend fun actualizarEstado(id: Int, estado: String, mensajeError: String?)

    @Query("SELECT COUNT(*) FROM operaciones_pendientes WHERE estado = 'PENDIENTE'")
    suspend fun contarPendientes(): Int

    @Query("SELECT COUNT(*) FROM operaciones_pendientes WHERE estado = 'ERROR'")
    suspend fun contarErrores(): Int
}