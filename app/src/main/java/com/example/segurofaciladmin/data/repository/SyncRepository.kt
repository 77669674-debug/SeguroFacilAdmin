package com.example.segurofaciladmin.data.repository

import android.content.Context
import com.example.segurofaciladmin.data.local.AppDatabase
import com.example.segurofaciladmin.data.local.entities.OperacionPendienteEntity
import com.example.segurofaciladmin.data.local.entities.SyncMetadataEntity
import com.example.segurofaciladmin.data.remote.RetrofitClient
import com.example.segurofaciladmin.data.remote.api.ApiService
import com.example.segurofaciladmin.data.remote.dto.PolizaDto
import com.google.gson.Gson
import retrofit2.HttpException
import java.util.UUID

class SyncRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val operacionDao = db.operacionPendienteDao()
    private val syncMetadataDao = db.syncMetadataDao()
    private val api = RetrofitClient.instance.create(ApiService::class.java)
    private val gson = Gson()

    suspend fun encolarPoliza(usuarioId: Int, planId: Int, cotizacionId: Int?, estado: String) {
        val uuid = UUID.randomUUID().toString()
        val dto = PolizaDto(
            usuarioId = usuarioId,
            planId = planId,
            cotizacionId = cotizacionId,
            estado = estado,
            uuidOperacion = uuid
        )
        operacionDao.insertar(
            OperacionPendienteEntity(
                uuidOperacion = uuid,
                entidad = "polizas",
                tipoOperacion = "INSERT",
                payload = gson.toJson(dto),
                fechaRegistro = System.currentTimeMillis()
            )
        )
    }

    suspend fun procesarPendientes(): Pair<Int, Int> {
        val pendientes = operacionDao.obtenerPendientes()
        var exitosos = 0
        var fallidos = 0

        for (operacion in pendientes) {
            operacionDao.actualizarEstado(operacion.id, "ENVIANDO", null)
            try {
                when (operacion.entidad) {
                    "polizas" -> {
                        val dto = gson.fromJson(operacion.payload, PolizaDto::class.java)
                        api.crearPoliza(dto)
                    }
                }
                operacionDao.actualizarEstado(operacion.id, "SINCRONIZADO", null)
                syncMetadataDao.actualizar(SyncMetadataEntity(operacion.entidad, System.currentTimeMillis()))
                exitosos++
            } catch (e: HttpException) {
                if (e.code() == 409) {
                    operacionDao.actualizarEstado(operacion.id, "SINCRONIZADO", "Ya existía (duplicado evitado)")
                    exitosos++
                } else {
                    operacionDao.actualizarEstado(operacion.id, "ERROR", "HTTP ${e.code()}: ${e.message()}")
                    fallidos++
                }
            } catch (e: Exception) {
                operacionDao.actualizarEstado(operacion.id, "ERROR", e.message ?: "Error desconocido")
                fallidos++
            }
        }
        return Pair(exitosos, fallidos)
    }
}