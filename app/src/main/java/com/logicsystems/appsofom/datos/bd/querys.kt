package com.logicsystems.appsofom.datos.bd

import android.content.Context
import com.logicsystems.appsofom.datos.DataManagerHelper

class querys (_context: Context) : DataManagerHelper(_context) {
    val db = readableDatabase
    fun getIMEI(){
        val cursor = db.query(
            Configuracion.TABLE_NAME,   // The table to query
            arrayOf(""),             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
    }
}