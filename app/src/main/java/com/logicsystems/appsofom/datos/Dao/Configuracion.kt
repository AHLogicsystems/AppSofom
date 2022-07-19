package com.logicsystems.appsofom.datos.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Configuracion(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var cEntorno: String = "",
    var cEmpresa: String = "",
    var nMinUpdateGPS: Int = 0,
    var nMinUpdateInfo: Int = 0,
    var cLoginUser: String = "",
    var cLoginPass: String = "",
    var cOperador: String = "",
    var cInfoTicket: String = ""
)