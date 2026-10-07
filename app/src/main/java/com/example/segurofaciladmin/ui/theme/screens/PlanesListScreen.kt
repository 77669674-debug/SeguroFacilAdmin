package com.example.segurofaciladmin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.segurofaciladmin.viewmodel.PlanSeguroViewModel

@Composable
fun PlanesListScreen(
    onAgregarClick: () -> Unit,
    onEditarClick: (Int) -> Unit,
    viewModel: PlanSeguroViewModel = viewModel()
) {
    val planes by viewModel.planes.collectAsState()

    LaunchedEffect(Unit) { viewModel.cargar() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar plan")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            items(planes) { plan ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = plan.nombre, style = MaterialTheme.typography.titleMedium)
                            Text(text = "${plan.tipoSeguro.name} - ${plan.aseguradora}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "S/ ${plan.primaBase}", style = MaterialTheme.typography.bodyMedium)
                        }
                        IconButton(onClick = { onEditarClick(plan.id) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Editar")
                        }
                        IconButton(onClick = { viewModel.eliminar(plan.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}