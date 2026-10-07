package com.example.segurofaciladmin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.segurofaciladmin.viewmodel.CotizacionViewModel
import com.example.segurofaciladmin.worker.SyncWorker

@Composable
fun CotizacionesListScreen(
    viewModel: CotizacionViewModel = viewModel()
) {
    val context = LocalContext.current
    val cotizaciones by viewModel.cotizaciones.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(Unit) { viewModel.cargar() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Cotizaciones de clientes", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                WorkManager.getInstance(context)
                    .enqueue(OneTimeWorkRequestBuilder<SyncWorker>().build())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sincronizar ahora")
        }

        Spacer(modifier = Modifier.height(12.dp))

        mensaje?.let { Text(text = it); Spacer(modifier = Modifier.height(8.dp)) }

        if (cotizaciones.isEmpty()) {
            Text("No hay cotizaciones registradas todavía")
        } else {
            LazyColumn {
                items(cotizaciones) { cotizacion ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Usuario #${cotizacion.usuarioId} - Plan #${cotizacion.planId}", style = MaterialTheme.typography.titleSmall)
                            Text(text = "Prima estimada: S/ ${"%.2f".format(cotizacion.primaEstimada)}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = cotizacion.fecha, style = MaterialTheme.typography.labelSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.registrarPoliza(cotizacion) }) {
                                Text("Registrar póliza")
                            }
                        }
                    }
                }
            }
        }
    }
}