package com.example.segurofaciladmin.data.local.dao

import androidx.room.*
import com.example.segurofaciladmin.data.local.entities.PlanSeguroEntity

@Dao
interface PlanSeguroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(planes: List<PlanSeguroEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(plan: PlanSeguroEntity)

    @Query("SELECT * FROM planes_seguro_local")
    suspend fun obtenerTodos(): List<PlanSeguroEntity>

    @Query("DELETE FROM planes_seguro_local WHERE id = :id")
    suspend fun eliminar(id: Int)
}