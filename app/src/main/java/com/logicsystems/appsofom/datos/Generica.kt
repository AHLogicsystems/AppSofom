package com.logicsystems.appsofom.datos

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


open class ClsGenerica : BaseColumns {
    protected var StrProblema: String = ""
    val cProblema: String = this.StrProblema

    lateinit var myContext: Context

    private lateinit var _helper: DataManagerHelper
    fun SetContext(context: Context) {
        this.myContext = context
        _helper = DataManagerHelper(context)
    }

    fun Limpiar() {
        this.StrProblema = ""
    }

    fun LoadAll(): Boolean {
        var BlnReturn = false
        val db: SQLiteDatabase = _helper.writableDatabase
        try {
            if (!LoadAll(db)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun LoadReg(): Boolean {
        var BlnReturn: Boolean = false
        val db: SQLiteDatabase = _helper.writableDatabase
        val nMaxRegistros = 30
        try {
            if (!LoadReg(db, nMaxRegistros)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun Search(IntTipoConsulta: Int, StrValues: Array<String>): Boolean {
        var BlnReturn: Boolean = false
        val db: SQLiteDatabase = _helper.writableDatabase
        try {
            if (!Search(db, IntTipoConsulta, StrValues)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun Guardar(): Boolean {
        var BlnReturn = false

        val db: SQLiteDatabase = _helper.writableDatabase
        val dbHelper = DataManagerHelper(myContext)
        dbHelper.writableDatabase
        db.beginTransaction()
        try {
            if (Guardar(db)) {
                db.setTransactionSuccessful()
            } else {
                this.StrProblema = "Error: " + this.StrProblema
            }
            db.endTransaction()
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
            db.endTransaction()
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun Delete(): Boolean {
        var BlnReturn = false
        val db: SQLiteDatabase = _helper.writableDatabase
        db.beginTransaction()
        try {
            if (Delete(db)) {
                db.setTransactionSuccessful()
            } else {
                this.StrProblema = "Error: " + this.StrProblema
            }
            db.endTransaction()
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
            db.endTransaction()
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    open fun Guardar(OCom: SQLiteDatabase): Boolean {
        return true
    }

    private fun Delete(OCom: SQLiteDatabase): Boolean {
        return true
    }

    open fun LoadAll(OCom: SQLiteDatabase): Boolean {
        return true
    }

    private fun LoadReg(OCom: SQLiteDatabase, nMaxRegistros: Int): Boolean {
        return true
    }

    private fun Load(OCom: SQLiteDatabase): Boolean {
        return true
    }

    private fun Search(OCom: SQLiteDatabase,IntTipoConsulta: Int, StrValues: Array<String>): Boolean{
        val BlnReturn = false
        val db: SQLiteDatabase = _helper.writableDatabase

        try {
            if (!this.Search(db, IntTipoConsulta, StrValues)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        return BlnReturn
    }

    private fun getCursor(): Cursor? {
        return null
    }

    fun FetchData(): Boolean {
        return false
    }

    fun MoveToFirst(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToFirst() == true) {
                return this.FetchData()
            }
        }
        return false
    }

    fun MoveToLast(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToLast() == true) {
                return FetchData()
            }
        }
        return false
    }

    fun MoveNext(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToNext() == true) {
                return FetchData()
            }
        }
        return false
    }

    fun MovePrevious(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToPrevious() == true) {
                return FetchData()
            }
        }
        return false
    }
}
class GeoCoordinate{
//    fun GeoCoordinate(Latitude: Double, Longitude:Double)
//    {
//        this.Latitude = Latitude
//        this.Longitude = Longitude
//    }
    var Latitude: Double = 0.0
    var Longitude: Double = 0.0
}