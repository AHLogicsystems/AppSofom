package com.logicsystems.appsofom.datos

import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.logicsystems.appsofom.datos.bd.SQL_CREATE_ENTRIES
import com.logicsystems.appsofom.datos.bd.SQL_DELETE_ENTRIES
import java.io.IOException
import java.io.InputStreamReader

open class DataManagerHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AppSofom.db"
    }

    fun EjecutarArchivoScriptSQL(db: SQLiteDatabase, ctx: Context, fileName: String) {
        if (fileName.trim() == "") {
            Log.d(DATABASE_NAME, "No se ha especificado el nombre del archivo")
            return
        }
        Log.d(DATABASE_NAME, "Creando la base de datos")
        val assetManager: AssetManager = ctx.assets
        val reader: InputStreamReader
        try {
            val inputStream = assetManager.open(fileName)
            reader = InputStreamReader(inputStream)
            EjecutarScriptSQL(db, reader)
        } catch (e: IOException) {
            Log.e(DATABASE_NAME, "IOException:", CreateThrowable(e))
        } finally {
            try {
//                reader.close()
            } catch (e: IOException) {
                Log.e(DATABASE_NAME, "IOException:",  CreateThrowable(e))
            }
        }
    }

    fun EjecutarScriptSQL(db: SQLiteDatabase, reader: InputStreamReader) {
        val line: String = reader.readText()
        var statement = StringBuilder()
        while (line == reader.readText()) {
            statement.append(line)
            statement.append("\n")
            if (line.endsWith(";")) {
                db.execSQL(statement.toString())
                statement = StringBuilder()
            }
        }
    }
    fun CreateThrowable (ex: Exception) : Throwable{
        if (ex is IOException){
            return IOException(ex.message)
        }
        else{
            return Exception(ex.message)
        }
    }
}