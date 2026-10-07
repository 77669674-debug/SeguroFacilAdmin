package com.example.segurofaciladmin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.segurofaciladmin.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso

    fun registrar(nombre: String, correo: String, password: String) {
        viewModelScope.launch {
            val ok = authRepository.registrar(nombre, correo, password)
            _mensaje.value = if (ok) "Cuenta creada, ya puedes iniciar sesión" else "Ya existe una cuenta con ese correo"
        }
    }

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            val usuario = authRepository.login(correo, password)
            if (usuario != null) {
                _loginExitoso.value = true
            } else {
                _mensaje.value = "Correo o contraseña incorrectos"
            }
        }
    }
}