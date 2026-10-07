package com.example.segurofaciladmin.data.repository

import android.content.Context
import com.example.segurofaciladmin.data.local.AppDatabase
import com.example.segurofaciladmin.data.local.entities.CotizacionEntity
import com.example.segurofaciladmin.data.remote.RetrofitClient
import com.example.segurofaciladmin.data.remote.api.ApiService

class CotizacionRepository(context: Context) {
    private val dao = AppDatabase.getDatabase(context).cotizacionDao()
    private val api = RetrofitClient.instance.create(ApiService::class.java)

    suspend fun sincronizar(): List<CotizacionEntity> {
        val dtos = api.obtenerCotizaciones()
        val entidades = dtos.map {
            CotizacionEntity(id = it.id, usuarioId = it.usuarioId, planId = it.planId, primaEstimada = it.primaEstimada, fecha = it.fecha)
        }
        dao.insertarTodos(entidades)
        return dao.obtenerTodas()
    }

    suspend fun obtenerLocal(): List<CotizacionEntity> = dao.obtenerTodas()
}