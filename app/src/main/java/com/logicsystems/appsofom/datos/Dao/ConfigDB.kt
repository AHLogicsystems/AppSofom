package com.logicsystems.appsofom.datos.Dao

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Configuracion::class],
    version = 1
)
abstract class ConfigDB : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}