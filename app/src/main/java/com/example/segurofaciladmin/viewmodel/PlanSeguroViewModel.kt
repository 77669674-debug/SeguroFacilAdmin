package com.example.segurofaciladmin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurofaciladmin.data.repository.PlanSeguroRepository
import com.example.segurofaciladmin.model.PlanSeguro
import com.example.segurofaciladmin.model.TipoSeguro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanSeguroViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PlanSeguroRepository(application)

    private val _planes = MutableStateFlow<List<PlanSeguro>>(emptyList())
    val planes: StateFlow<List<PlanSeguro>> = _planes

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _guardadoExitoso = MutableStateFlow(false)
    val guardadoExitoso: StateFlow<Boolean> = _guardadoExitoso

    fun cargar() {
        viewModelScope.launch {
            _planes.value = try { repository.sincronizar() } catch (e: Exception) { repository.obtenerLocal() }
        }
    }

    fun obtenerPorId(id: Int): PlanSeguro? = _planes.value.find { it.id == id }

    fun guardar(id: Int, nombre: String, tipo: TipoSeguro, aseguradora: String, cobertura: String, primaBase: Double) {
        if (primaBase <= 0) {
            _mensaje.value = "La prima base debe ser mayor a 0"
            return
        }
        viewModelScope.launch {
            val plan = PlanSeguro(id, nombre, tipo, aseguradora, cobertura, primaBase)
            val ok = if (id == 0) repository.crear(plan) else repository.actualizar(plan)
            if (ok) {
                _guardadoExitoso.value = true
                cargar()
            } else {
                _mensaje.value = "Error al guardar, revisa tu conexión"
            }
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            repository.eliminar(id)
            cargar()
        }
    }

    fun resetear() {
        _guardadoExitoso.value = false
        _mensaje.value = null
    }
}