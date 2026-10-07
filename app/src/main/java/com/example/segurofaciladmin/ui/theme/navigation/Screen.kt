package com.example.segurofaciladmin.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Planes : Screen("planes")
    object FormPlan : Screen("form_plan?planId={planId}") {
        fun crearRuta(planId: Int? = null) = "form_plan?planId=${planId ?: -1}"
    }
    object Cotizaciones : Screen("cotizaciones")
    object Sync : Screen("sync")
}