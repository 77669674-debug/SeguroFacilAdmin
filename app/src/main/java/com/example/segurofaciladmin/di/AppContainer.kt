package com.example.segurofaciladmin.di

import android.content.Context
import com.example.segurofaciladmin.data.repository.AuthRepository
import com.example.segurofaciladmin.data.repository.CotizacionRepository
import com.example.segurofaciladmin.data.repository.PlanSeguroRepository
import com.example.segurofaciladmin.data.repository.SyncRepository

class AppContainer(context: Context) {
    val authRepository = AuthRepository(context)
    val planSeguroRepository = PlanSeguroRepository(context)
    val cotizacionRepository = CotizacionRepository(context)
    val syncRepository = SyncRepository(context)
}