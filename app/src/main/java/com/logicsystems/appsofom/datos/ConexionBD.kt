package com.logicsystems.appsofom.datos

import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.IOException
import java.io.InputStreamReader

class DataManagerHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//    val dbHelper = DataManagerHelper(context)
    override fun onCreate(db: SQLiteDatabase) {
        Log.e(DATABASE_NAME, "Creando la base de datos")
        val archivo: String = String.format("Assets/databse/versiones/script_v{0}.sql", DATABASE_VERSION)
        EjecutarArchivoScriptSQL(db, this.context, archivo)

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        var version = oldVersion
        while(version < newVersion){
            val archivo: String = String.format("Assets/databse/upgrades/upgrade_{0}_to_{1}.sql", version, (version + 1))
            EjecutarArchivoScriptSQL(db, context, archivo)
            version++
        }
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "AppSofomCIB"
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
            Log.e(DATABASE_NAME, "IOException:", CreateThrowable(e),)
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