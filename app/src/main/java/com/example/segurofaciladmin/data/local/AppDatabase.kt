package com.example.segurofaciladmin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.segurofaciladmin.data.local.dao.*
import com.example.segurofaciladmin.data.local.entities.*

@Database(
    entities = [
        UsuarioEntity::class,
        PlanSeguroEntity::class,
        CotizacionEntity::class,
        PolizaEntity::class,
        OperacionPendienteEntity::class,
        SyncMetadataEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun planSeguroDao(): PlanSeguroDao
    abstract fun cotizacionDao(): CotizacionDao
    abstract fun polizaDao(): PolizaDao
    abstract fun operacionPendienteDao(): OperacionPendienteDao
    abstract fun syncMetadataDao(): SyncMetadataDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "segurofacil_admin_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}