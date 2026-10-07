package com.example.segurofaciladmin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segurofaciladmin.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var esModoRegistro by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val mensaje by viewModel.mensaje.collectAsState()
    val loginExitoso by viewModel.loginExitoso.collectAsState()

    LaunchedEffect(loginExitoso) {
        if (loginExitoso) onLoginExitoso()
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(text = if (esModoRegistro) "Crear cuenta Administrador" else "Panel Administrador", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        if (esModoRegistro) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        mensaje?.let { Text(text = it); Spacer(modifier = Modifier.height(8.dp)) }

        Button(
            onClick = { if (esModoRegistro) viewModel.registrar(nombre, correo, password) else viewModel.login(correo, password) },
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (esModoRegistro) "Registrarse" else "Ingresar") }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { esModoRegistro = !esModoRegistro }) {
            Text(if (esModoRegistro) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate")
        }
    }
}