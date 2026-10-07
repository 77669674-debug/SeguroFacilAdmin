package com.example.segurofaciladmin.data.repository

import android.content.Context
import com.example.segurofaciladmin.data.local.AppDatabase
import com.example.segurofaciladmin.data.local.entities.UsuarioEntity
import org.mindrot.jbcrypt.BCrypt

class AuthRepository(context: Context) {
    private val usuarioDao = AppDatabase.getDatabase(context).usuarioDao()

    suspend fun registrar(nombre: String, correo: String, password: String): Boolean {
        val existente = usuarioDao.buscarPorCorreo(correo)
        if (existente != null) return false
        usuarioDao.insertar(
            UsuarioEntity(
                nombre = nombre,
                correo = correo,
                passwordHash = BCrypt.hashpw(password, BCrypt.gensalt()),
                rol = "administrador"
            )
        )
        return true
    }

    suspend fun login(correo: String, password: String): UsuarioEntity? {
        val usuario = usuarioDao.buscarPorCorreo(correo) ?: return null
        return if (BCrypt.checkpw(password, usuario.passwordHash)) usuario else null
    }
}