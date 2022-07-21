package com.logicsystems.appsofom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.logicsystems.appsofom.datos.Dao.Configuracion

@Dao
interface ConfigDao {
    @Query("SELECT * FROM Configuracion")
    suspend fun getConfig(): Configuracion

    @Query("Select 1 id from Configuracion")
    fun getIdConfig(): Int

    @Update
    fun update (configuracion: Configuracion)

    @Insert
    fun insert (configuracion: Configuracion)
}