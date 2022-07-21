package com.logicsystems.appsofom.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.logicsystems.appsofom.dao.ConfigDao
import com.logicsystems.appsofom.model.Configuracion

@Database(
    entities = [Configuracion::class],
    version = 1
)
abstract class AppSofomDB : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}