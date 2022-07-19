package com.logicsystems.appsofom.datos.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ConfigDao {

    @Query("SELECT * FROM Configuracion")
    fun getConfig(): Configuracion

    @Query("Select 1 id from Configuracion")
    fun getIdConfig(): Int

    @Update
    fun update (configuracion: Configuracion)

    @Insert
    fun insert (configuracion: Configuracion)
}