package com.example.segurofaciladmin.model

data class PlanSeguro(
    val id: Int,
    val nombre: String,
    val tipoSeguro: TipoSeguro,
    val aseguradora: String,
    val cobertura: String,
    val primaBase: Double
)