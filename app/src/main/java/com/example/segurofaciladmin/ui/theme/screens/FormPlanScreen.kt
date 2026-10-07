package com.example.segurofaciladmin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segurofaciladmin.model.TipoSeguro
import com.example.segurofaciladmin.viewmodel.PlanSeguroViewModel

@Composable
fun FormPlanScreen(
    planId: Int,
    onGuardado: () -> Unit,
    viewModel: PlanSeguroViewModel = viewModel()
) {
    val planExistente = if (planId != -1) viewModel.obtenerPorId(planId) else null

    var nombre by remember { mutableStateOf(planExistente?.nombre ?: "") }
    var tipoSeleccionado by remember { mutableStateOf(planExistente?.tipoSeguro ?: TipoSeguro.VEHICULAR) }
    var aseguradora by remember { mutableStateOf(planExistente?.aseguradora ?: "") }
    var cobertura by remember { mutableStateOf(planExistente?.cobertura ?: "") }
    var primaBaseTexto by remember { mutableStateOf(planExistente?.primaBase?.toString() ?: "") }

    val mensaje by viewModel.mensaje.collectAsState()
    val guardadoExitoso by viewModel.guardadoExitoso.collectAsState()

    LaunchedEffect(guardadoExitoso) {
        if (guardadoExitoso) {
            viewModel.resetear()
            onGuardado()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(text = if (planId == -1) "Nuevo plan" else "Editar plan", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del plan") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Tipo de seguro")
        Column {
            TipoSeguro.entries.forEach { tipo ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(selected = tipoSeleccionado == tipo, onClick = { tipoSeleccionado = tipo })
                    Text(text = tipo.name)
                }
            }
        }

        OutlinedTextField(value = aseguradora, onValueChange = { aseguradora = it }, label = { Text("Aseguradora") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = cobertura, onValueChange = { cobertura = it }, label = { Text("Cobertura") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = primaBaseTexto,
            onValueChange = { primaBaseTexto = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Prima base (S/)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        mensaje?.let { Text(text = it, color = MaterialTheme.colorScheme.error); Spacer(modifier = Modifier.height(8.dp)) }

        Button(
            onClick = {
                viewModel.guardar(
                    id = planId.takeIf { it != -1 } ?: 0,
                    nombre = nombre,
                    tipo = tipoSeleccionado,
                    aseguradora = aseguradora,
                    cobertura = cobertura,
                    primaBase = primaBaseTexto.toDoubleOrNull() ?: 0.0
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar") }
    }
}