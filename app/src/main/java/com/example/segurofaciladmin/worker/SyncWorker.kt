package com.example.segurofaciladmin.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.segurofaciladmin.data.repository.SyncRepository

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val (exitosos, fallidos) = SyncRepository(applicationContext).procesarPendientes()
            Log.d("SYNC_ADMIN", "Sincronizado: $exitosos exitosos, $fallidos fallidos")
            Result.success()
        } catch (e: Exception) {
            Log.e("SYNC_ADMIN", "Error en sync: ${e::class.java.simpleName} - ${e.message}")
            Result.retry()
        }
    }
}