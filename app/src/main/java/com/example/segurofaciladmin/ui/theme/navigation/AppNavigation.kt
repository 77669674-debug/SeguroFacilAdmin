package com.example.segurofaciladmin.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.segurofaciladmin.ui.screens.CotizacionesListScreen
import com.example.segurofaciladmin.ui.screens.FormPlanScreen
import com.example.segurofaciladmin.ui.screens.LoginScreen
import com.example.segurofaciladmin.ui.screens.PlanesListScreen

private val rutasConBarra = listOf(
    Screen.Planes.route,
    Screen.Cotizaciones.route
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (rutaActual in rutasConBarra) {
                NavigationBar {
                    NavigationBarItem(
                        selected = rutaActual == Screen.Planes.route,
                        onClick = { navController.navigate(Screen.Planes.route) },
                        icon = { Icon(Icons.Filled.List, contentDescription = "Planes") },
                        label = { Text("Planes") }
                    )
                    NavigationBarItem(
                        selected = rutaActual == Screen.Cotizaciones.route,
                        onClick = { navController.navigate(Screen.Cotizaciones.route) },
                        icon = { Icon(Icons.Filled.RequestQuote, contentDescription = "Cotizaciones") },
                        label = { Text("Cotizaciones") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginExitoso = {
                        navController.navigate(Screen.Planes.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Planes.route) {
                PlanesListScreen(
                    onAgregarClick = { navController.navigate(Screen.FormPlan.crearRuta()) },
                    onEditarClick = { id -> navController.navigate(Screen.FormPlan.crearRuta(id)) }
                )
            }
            composable(
                Screen.FormPlan.route,
                arguments = listOf(navArgument("planId") { type = NavType.IntType; defaultValue = -1 })
            ) { backStackEntry ->
                FormPlanScreen(
                    planId = backStackEntry.arguments?.getInt("planId") ?: -1,
                    onGuardado = { navController.popBackStack() }
                )
            }
            composable(Screen.Cotizaciones.route) {
                CotizacionesListScreen()
            }
        }
    }
}