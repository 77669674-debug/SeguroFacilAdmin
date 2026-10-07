package com.example.segurofaciladmin.data.mapper

import com.example.segurofaciladmin.data.local.entities.PlanSeguroEntity
import com.example.segurofaciladmin.data.remote.dto.PlanSeguroDto
import com.example.segurofaciladmin.model.PlanSeguro
import com.example.segurofaciladmin.model.TipoSeguro

fun PlanSeguroDto.toEntity() = PlanSeguroEntity(
    id = id ?: 0,
    nombre = nombre,
    tipoSeguro = tipoSeguro,
    aseguradora = aseguradora,
    cobertura = cobertura,
    primaBase = primaBase
)

fun PlanSeguroEntity.toDomain() = PlanSeguro(
    id = id,
    nombre = nombre,
    tipoSeguro = TipoSeguro.valueOf(tipoSeguro),
    aseguradora = aseguradora,
    cobertura = cobertura,
    primaBase = primaBase
)

fun PlanSeguro.toDto() = PlanSeguroDto(
    id = if (id == 0) null else id,
    nombre = nombre,
    tipoSeguro = tipoSeguro.name,
    aseguradora = aseguradora,
    cobertura = cobertura,
    primaBase = primaBase
)

fun PlanSeguro.toEntity() = PlanSeguroEntity(
    id = id,
    nombre = nombre,
    tipoSeguro = tipoSeguro.name,
    aseguradora = aseguradora,
    cobertura = cobertura,
    primaBase = primaBase
)