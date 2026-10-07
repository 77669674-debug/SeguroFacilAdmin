package com.example.segurofaciladmin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurofaciladmin.data.local.entities.CotizacionEntity
import com.example.segurofaciladmin.data.repository.CotizacionRepository
import com.example.segurofaciladmin.data.repository.SyncRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CotizacionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CotizacionRepository(application)
    private val syncRepository = SyncRepository(application)

    private val _cotizaciones = MutableStateFlow<List<CotizacionEntity>>(emptyList())
    val cotizaciones: StateFlow<List<CotizacionEntity>> = _cotizaciones

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun cargar() {
        viewModelScope.launch {
            _cotizaciones.value = try { repository.sincronizar() } catch (e: Exception) { repository.obtenerLocal() }
        }
    }

    fun registrarPoliza(cotizacion: CotizacionEntity) {
        viewModelScope.launch {
            syncRepository.encolarPoliza(
                usuarioId = cotizacion.usuarioId,
                planId = cotizacion.planId,
                cotizacionId = cotizacion.id,
                estado = "activa"
            )
            _mensaje.value = "Póliza registrada (pendiente de sincronizar)"
        }
    }
}