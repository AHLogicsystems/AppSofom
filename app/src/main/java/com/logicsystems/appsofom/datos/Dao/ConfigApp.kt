package com.logicsystems.appsofom.datos.Dao

import android.app.Application
import androidx.room.Room

open class ConfigApp : Application() {
    val room = Room
        .databaseBuilder(this, ConfigDB::class.java, "config")
        .build()
}