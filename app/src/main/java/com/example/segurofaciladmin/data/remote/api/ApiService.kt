package com.example.segurofaciladmin.data.remote.api

import com.example.segurofaciladmin.data.remote.dto.CotizacionDto
import com.example.segurofaciladmin.data.remote.dto.PlanSeguroDto
import com.example.segurofaciladmin.data.remote.dto.PolizaDto
import retrofit2.http.*

interface ApiService {

    @GET("planes_seguro")
    suspend fun obtenerPlanes(): List<PlanSeguroDto>

    @POST("planes_seguro")
    suspend fun crearPlan(@Body dto: PlanSeguroDto): List<PlanSeguroDto>

    @PATCH("planes_seguro")
    suspend fun actualizarPlan(@Query("id") filtroId: String, @Body dto: PlanSeguroDto): List<PlanSeguroDto>

    @DELETE("planes_seguro")
    suspend fun eliminarPlan(@Query("id") filtroId: String)

    @GET("cotizaciones")
    suspend fun obtenerCotizaciones(): List<CotizacionDto>

    @POST("polizas")
    suspend fun crearPoliza(@Body dto: PolizaDto): List<PolizaDto>
}