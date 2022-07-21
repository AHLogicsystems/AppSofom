package com.logicsystems.appsofom.data

import android.app.Application
import androidx.room.Room

class ConexionDB : Application() {
    val room = Room.databaseBuilder(this, AppSofomDB::class.java, "config")
        .build()
}