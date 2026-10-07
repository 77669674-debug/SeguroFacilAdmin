package com.example.segurofaciladmin.data.repository

import android.content.Context
import com.example.segurofaciladmin.data.local.AppDatabase
import com.example.segurofaciladmin.data.mapper.toDomain
import com.example.segurofaciladmin.data.mapper.toDto
import com.example.segurofaciladmin.data.mapper.toEntity
import com.example.segurofaciladmin.data.remote.RetrofitClient
import com.example.segurofaciladmin.data.remote.api.ApiService
import com.example.segurofaciladmin.model.PlanSeguro

class PlanSeguroRepository(context: Context) {
    private val dao = AppDatabase.getDatabase(context).planSeguroDao()
    private val api = RetrofitClient.instance.create(ApiService::class.java)

    suspend fun sincronizar(): List<PlanSeguro> {
        val dtos = api.obtenerPlanes()
        dao.insertarTodos(dtos.map { it.toEntity() })
        return dao.obtenerTodos().map { it.toDomain() }
    }

    suspend fun obtenerLocal(): List<PlanSeguro> = dao.obtenerTodos().map { it.toDomain() }

    suspend fun crear(plan: PlanSeguro): Boolean {
        return try {
            val creado = api.crearPlan(plan.toDto()).first()
            dao.insertar(creado.toEntity())
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizar(plan: PlanSeguro): Boolean {
        return try {
            api.actualizarPlan("eq.${plan.id}", plan.toDto())
            dao.insertar(plan.toEntity())
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminar(id: Int): Boolean {
        return try {
            api.eliminarPlan("eq.$id")
            dao.eliminar(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}