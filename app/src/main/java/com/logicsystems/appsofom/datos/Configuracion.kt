package com.logicsystems.appsofom.datos

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException


open class ClsConfiguracion : ClsGenerica() {
    lateinit var c: Cursor
    var Id: Int = 0
    var cEntorno: String = ""
    var cEmpresa: String = ""
    var nMinUpdateGPS: Int = 0
    var nMinUpdateInfo: Int = 0
    var cLoginUser: String = ""
    var cLoginPass: String = ""
    var cOperador: String = ""
    var cInfoTicket: String = ""

    fun validacion(ODB: SQLiteDatabase): Boolean {
        var BlnReturn = false
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    override fun Guardar(ODB: SQLiteDatabase): Boolean{
        var BlnReturn: Boolean = false
        try {
            if (this.Id == 0) {
                //Insertamos los datos en la tabla Usuarios
                val values = ContentValues()
                values.put("cEntorno", this.cEntorno)
                values.put("cEmpresa", this.cEmpresa)
                values.put("nMinUpdateGPS", this.nMinUpdateGPS)
                values.put("nMinUpdateInfo", this.nMinUpdateInfo)
                values.put("cLoginUser", this.cLoginUser)
                values.put("cLoginPass", this.cLoginPass)
                values.put("cOperador", this.cOperador)
                values.put("cInfoTicket", this.cInfoTicket)
                val LastId: Long = ODB.insert("Configuracion", null, values)
                this.Id = LastId.toInt()
            } else {
                ODB.execSQL(
                    "Update Configuracion Set " +
                            "cEntorno= '" + this.cEntorno + "'," +
                            "cEmpresa='" + this.cEmpresa + "'," +
                            "nMinUpdateGPS= " + this.nMinUpdateGPS + "," +
                            "nMinUpdateInfo= " + this.nMinUpdateInfo + "," +
                            "cLoginUser= '" + this.cLoginUser + "'," +
                            "cLoginPass= '" + this.cLoginPass + "'," +
                            "cOperador= '" + this.cOperador + "'," +
                            "cInfoTicket= '" + this.cInfoTicket + "' " +
                            "Where Id=" + this.Id.toString()
                )
            }
        } catch (ex: Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    override fun LoadAll (ODB: SQLiteDatabase): Boolean{
        var columnas = arrayOf("Id", "cEntorno", "cEmpresa", "nMinUpdateGPS", "nMinUpdateInfo", "cLoginUser", "cLoginPass", "cOperador", "cInfoTicket")
        try {
            this.c = ODB.query("Configuracion", columnas, null, null, null, null, null)
        }
        catch (ex: SQLiteException){
            this.StrProblema = "ZX: ${ex.message}"
        }
        return (this.StrProblema == "")
    }
}