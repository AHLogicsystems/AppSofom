package com.logicsystems.appsofom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Configuracion(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "cEntorno") var StrEntorno: String = "",
    @ColumnInfo(name = "cEmpresa") var StrEmpresa: String = "",
    @ColumnInfo(name = "nMinUpdateGPS") var IntMinUpdateGPS: Int = 0,
    @ColumnInfo(name = "nMinUpdateInfo") var IntMinUpdateInfo: Int = 0,
    @ColumnInfo(name = "cLoginUser") var StrLoginUser: String = "",
    @ColumnInfo(name = "cLoginPass") var StrLoginPass: String = "",
    @ColumnInfo(name = "cOperador") var StrOperador: String = "",
    @ColumnInfo(name = "cInfoTicket") var StrInfoTicket: String = ""
)