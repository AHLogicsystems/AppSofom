package com.logicsystems.appsofom.datos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.util.*
import kotlin.properties.Delegates

class ClsOffLineDisposicion : Generica() {
    lateinit var c: Cursor
    var Id by Delegates.notNull<Int>()
    var IdPrestamo by Delegates.notNull<Int>()
    var cFolio: String
    var cCliente: String
    var nMontoTotal by Delegates.notNull<Double>()
    var nSaldoPendiente by Delegates.notNull<Double>()
    var DteSaveInfo: Date
    var BlnDeleteAll by Delegates.notNull<Boolean>()
    var BlnDeleteAllPrestamo by Delegates.notNull<Boolean>()
    var IdCliente by Delegates.notNull<Int>()
    var IdClienteMoral by Delegates.notNull<Int>()
    var IdGrupoSolidario by Delegates.notNull<Int>()

    init{
        this.Limpiar()
        this.Id = 0
        this.IdPrestamo = 0
        this.cFolio = ""
        this.cCliente = ""
        this.nMontoTotal = 0.0
        this.nSaldoPendiente = 0.0
        this.DteSaveInfo = Date(Long.MIN_VALUE)
        this.BlnDeleteAll = false
        this.BlnDeleteAllPrestamo = false
        this.IdCliente = 0
        this.IdClienteMoral = 0
        this.IdGrupoSolidario = 0
    }
    fun validacion(ODB: SQLiteDatabase) : Boolean{
        var BlnReturn = false
        if (this.StrProblema == ""){
            BlnReturn = true
        }
        return BlnReturn
    }

    fun RessetProblema(){
        this.StrProblema = ""
    }

    override fun Guardar(OCom: SQLiteDatabase): Boolean {
        var BlnReturn = false
        try {
            if (this.Id == 0){
                var epochTicks = time
            }
        }
        catch (ex: Exception){
            Log.e("Error al guardar", ex.message.toString())
        }
        return BlnReturn
    }
}