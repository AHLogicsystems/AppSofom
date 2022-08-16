package com.logicsystems.appsofom.datos

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.logicsystems.appsofom.AppCobranzaRespuesta
import com.logicsystems.appsofom.PrestamoApp
import com.logicsystems.appsofom.datos.bd.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


open class ClsOfflineDisposicion : ClsGenerica() {
    lateinit var c: Cursor
    var Id by Delegates.notNull<Int>()
    var IdPrestamo by Delegates.notNull<Int>()
    var cFolio: String
    var cCliente: String
    var nMontoTotal by Delegates.notNull<Double>()
    var nSaldoPendiente by Delegates.notNull<Double>()
    lateinit var DteSaveInfo: Date
    var BlnDeleteAll by Delegates.notNull<Boolean>()
    var BlnDeleteAllPrestamo by Delegates.notNull<Boolean>()
    var IdCliente by Delegates.notNull<Int>()
    var IdClienteMoral by Delegates.notNull<Int>()
    var IdGrupoSolidario by Delegates.notNull<Int>()

    val OD = OfflineDisposicion

    init{
        this.Limpiar()
        this.Id = 0
        this.IdPrestamo = 0
        this.cFolio = ""
        this.cCliente = ""
        this.nMontoTotal = 0.0
        this.nSaldoPendiente = 0.0
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
        try {
            if (this.Id == 0){
                val values = ContentValues()
                values.put(OD.IdPrestamo, this.IdPrestamo)
                values.put(OD.cFolio, this.cFolio)
                values.put(OD.cCliente, this.cCliente)
                values.put(OD.nMontoTotal, this.nMontoTotal)
                values.put(OD.nSaldoPendiente, this.nSaldoPendiente)
                values.put(OD.DteSaveInfo, this.DteSaveInfo.time)
                values.put(OD.IdCliente, this.IdCliente)
                values.put(OD.IdClienteMoral, this.IdClienteMoral)
                values.put(OD.IdGrupoSolidario, this.IdGrupoSolidario)
                val LastId: Long = OCom.insert(OD.TABLE_NAME, null, values)
                this.Id = LastId.toInt()
            }
        }
        catch (ex: Exception){
            Log.e("Error al guardar", ex.message.toString())
        }
        return this.StrProblema == ""
    }

    override fun LoadAll(OCom: SQLiteDatabase): Boolean {
        val columnas = arrayOf(
            OD.Id,
            OD.IdPrestamo,
            OD.cFolio,
            OD.cCliente,
            OD.nMontoTotal,
            OD.nSaldoPendiente,
            OD.DteSaveInfo,
            OD.IdCliente,
            OD.IdClienteMoral,
            OD.IdGrupoSolidario
        )
        try {
            this.c = OCom.query(OfflineDisposicion.TABLE_NAME, columnas, null, null, null, null, null)
        } catch (ex: SQLiteException) {
            this.StrProblema = "ZX: " + ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Search(OCom: SQLiteDatabase, nTipoSearch: Int, StrValues: Array<String>) : Boolean{
        var columnas = arrayOf(
            OD.Id,
            OD.IdPrestamo,
            OD.cFolio,
            OD.cCliente,
            OD.nMontoTotal,
            OD.nSaldoPendiente,
            OD.DteSaveInfo,
            OD.IdCliente,
            OD.IdClienteMoral,
            OD.IdGrupoSolidario
        )
        var cCondiciones = ""
        var cWhere: Array<String> = emptyArray()
        when (nTipoSearch){
            0 -> {
                cCondiciones = OD.IdPrestamo + "=?"
                cWhere = arrayOf(StrValues[0])
            }
            1 -> {
                cCondiciones = "(" + OD.cFolio + " like and ?<>'') or (" + OD.cCliente + " like ? and ?<> ''"
                cWhere = arrayOf("%" + StrValues[0] + "%", StrValues[0])
            }
            2 -> {
                columnas = emptyArray()
                columnas = arrayOf(OD.DteSaveInfo)
            }
            3 -> {
                cCondiciones = OD.Id + "=?"
                cWhere = arrayOf(StrValues[0])
            }
        }
        try{
            this.c = OCom.query(OD.TABLE_NAME, columnas, cCondiciones, cWhere, null, null, null)
        }
        catch (ex: Exception){
            this.StrProblema = "ZX: " + ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Delete(OCom: SQLiteDatabase): Boolean {
        try {
            var cCondiciones = ""
            var cWhere: Array<String> = emptyArray()
            if (BlnDeleteAll){
                cCondiciones = ""
                cWhere = emptyArray()
            }
            if(BlnDeleteAllPrestamo){
                cCondiciones = OD.IdPrestamo + "=?"
                cWhere = arrayOf(this.IdPrestamo.toString())
            }
            OCom.delete(OD.TABLE_NAME, cCondiciones, cWhere)
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun getCursor(): Cursor? {
        return this.c
    }

    override fun FetchData(): Boolean {
        try {
            this.Id = c.getInt(0)
            this.IdPrestamo = c.getInt(1)
            this.cFolio = c.getString(2)
            this.cCliente = c.getString(3)
            this.nMontoTotal = c.getDouble(4)
            this.nSaldoPendiente = c.getDouble(5)
            this.DteSaveInfo = SimpleDateFormat("dd-MM-yyyy").parse(c.getString(6)) as Date
            this.IdCliente = c.getInt(7)
            this.IdClienteMoral = c.getInt(9)
            this.IdGrupoSolidario = c.getInt(8)
            return true
        }
        catch (ex: Exception){}
        return false
    }
}

open class ClsOfflinePrestamoXCobrar : ClsOfflinePrestamo(){
    lateinit var point1:GeoCoordinate
    var nDistancia = 0.0

    private var OPXC = OfflinePrestamoXCobrar

    init {
        super.Limpiar()
        point1 = GeoCoordinate()
        strTabla = OPXC.TABLE_NAME
    }
    fun getFolio(){
        if (this.nMontoTotal > 0){
            val moneyValue = this.nMontoTotal.toBigDecimal()
            val strMonto = String.format("Esperando: {0:###,###,###,###,###.00}", moneyValue)
            this.cFolio = this.cFolio + " (" + strMonto + ")"
        }
    }

    fun getDireccion(){
        val nDistancia = getDistancia()
        if (nDistancia > 0){
            this.cDireccion = this.cDireccion + " [ " + nDistancia.toString() + " Km. Aprox.]"
        }
    }

    fun getLastPos(): Int{
        var maxId = 0
        val ODB = _helper.writableDatabase
        val cursor: Cursor = ODB.rawQuery("SELECT MAX(nPos) FROM " + strTabla, null)
        if (cursor.moveToFirst()){
            maxId = cursor.getInt(0)
        }
        else{
            maxId = 0
        }
        return maxId +1
    }

    fun getDistancia(): Double{
        nDistancia = 0.0
        if (Latitud() != 0.0 && Longitud() != 0.0){
            val point2 = GeoCoordinate(Latitud(), Longitud())
            nDistancia = AppSofomConfigs().GetDistance(point1, point2)
        }
        return nDistancia
    }

    fun Load() : Boolean{
        val ODB = _helper.writableDatabase
        val StrAgs: Array<String> = arrayOf(this.IdPrestamo.toString())
        try {
            this.Search(ODB, 0, StrAgs)
            if (this.c.count > 0){
                this.MoveToFirst()
            }
            else{
                this.StrProblema = "No se pudo obtejer el objeto"
            }
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    fun Latitud(): Double {
        var nLatitud = 0.0
        if (this.cGeoLocalizacion != ""){
            nLatitud = (cGeoLocalizacion.split(',')[0]).toDouble()
        }
        return nLatitud
    }
    fun Longitud(): Double {
        var nLongitud = 0.0
        if (this.cGeoLocalizacion != ""){
            nLongitud = (cGeoLocalizacion.split(',')[1]).toDouble()
        }
        return nLongitud
    }
}

open class ClsOffLinePrestamoXOperador : ClsOfflinePrestamo(){
    var OPXO = OfflinePrestamoXOperador
    init {
        strTabla = OPXO.TABLE_NAME
    }
}

open class ClsOfflinePrestamo : ClsGenerica(){
    lateinit var c: Cursor
    var Id by Delegates.notNull<Int>()
    var IdPrestamo by Delegates.notNull<Int>()
    var IdCliente by Delegates.notNull<Int>()
    var IdClienteMoral by Delegates.notNull<Int>()
    var IdGrupoSolidario by Delegates.notNull<Int>()
    var cFolio: String
    var cCliente: String
    var nMontoTotal by Delegates.notNull<Double>()
    var nSaldoPendiente by Delegates.notNull<Double>()
    var cDireccion: String
    lateinit var DteSaveInfo: Date
    var BlnDeleteAll by Delegates.notNull<Boolean>()
    var BlnDeleteByPrestamo by Delegates.notNull<Boolean>()
    var nEstadoRegistro by Delegates.notNull<Int>()
    var nPos by Delegates.notNull<Int>()
    lateinit var strTabla: String
    lateinit var cGeoLocalizacion: String
    var cColor: String

    var BlnUpdate by Delegates.notNull<Boolean>()
    var lManual by Delegates.notNull<Int>()

    private val OPXC = OfflinePrestamoXCobrar

    init {
        this.Limpiar()
        this.Id = 0
        this.IdPrestamo = 0
        this.cFolio = ""
        this.cCliente = ""
        this.nMontoTotal = 0.0
        this.nSaldoPendiente = 0.0
        this.BlnDeleteAll = false
        this.BlnDeleteByPrestamo = false
        this.IdCliente = 0
        this.IdClienteMoral = 0
        this.IdGrupoSolidario = 0
        this.cDireccion = ""
        this.nEstadoRegistro = 0
        this.cColor = ""
        //0: Espera
        //1: Visitado sin pago
        //2: No Visitado
        //3: Pagado
        this.lManual = 0
    }

    fun validacion(ODB: SQLiteDatabase) : Boolean{
        return this.StrProblema == ""
    }

    fun RessetProblema(){
        this.StrProblema = ""
    }

    override fun Guardar(OCom: SQLiteDatabase): Boolean {
        try {
            val StrAgs = arrayOf(this.IdPrestamo.toString())
            this.Search(OCom, 0, StrAgs)
            val values = ContentValues()
            values.put(OPXC.IdPrestamo, this.IdPrestamo)
            values.put(OPXC.cFolio, this.cFolio)
            values.put(OPXC.cCliente, this.cCliente)
            values.put(OPXC.nMontoTotal, this.nMontoTotal)
            values.put(OPXC.nSaldoPendiente, this.nSaldoPendiente)
//            values.put(OPXC.DteSaveInfo, this.DteSaveInfo)
            values.put(OPXC.IdCliente, this.IdCliente)
            values.put(OPXC.IdClienteMoral, this.IdClienteMoral)
            values.put(OPXC.IdGrupoSolidario, this.IdGrupoSolidario)
            values.put(OPXC.cDireccion, this.cDireccion)
            values.put(OPXC.nPos, this.nPos)
            values.put(OPXC.cGeoLocalizacion, this.cGeoLocalizacion)
            values.put(OPXC.cColor, this.cColor)
            if (strTabla == OPXC.TABLE_NAME){
                values.put(OPXC.lManual, this.lManual)
            }
            if (this.c.count == 0){
                values.put(OPXC.nEstadoRegistro, this.nEstadoRegistro)
                val LastId: Long = OCom.insert(strTabla, null, values)
                this.Id = LastId.toInt()
            }
            else if (BlnUpdate){
                OCom.update(strTabla, values, "id=?", arrayOf(this.Id.toString()))
            }
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    fun ActualizarPosicion(IdPrestamoUpdate: Int, nNewPos: Int,nMaxPos: Int) : Boolean{
        var nNewPosAux = nNewPos
        val ODB: SQLiteDatabase = _helper.writableDatabase
        try {
            ODB.beginTransaction()
            if (nNewPosAux == -1){
                ODB.execSQL("Update " + strTabla + " Set " +
                        "nPos= nPos - 1 " +
                        "Where nPos >" + nMaxPos.toString() + " And IdPrestamo <>" + IdPrestamoUpdate.toString())
            }
            else{
                if (nNewPosAux == 0){
                    nNewPosAux = 1
                }
                if (nMaxPos == 0){
                    ODB.execSQL("Update " + strTabla + " Set " +
                            "nPos= nPos + 1 " +
                            "Where nPos >=" + nNewPos.toString() + " And IdPrestamo <>" + IdPrestamoUpdate.toString())
                }
                else{
                    if (nNewPosAux <= nMaxPos){
                        ODB.execSQL("Update " + strTabla + " Set " +
                                "nPos= nPos + 1 " +
                                "Where nPos >=" + nNewPos.toString() + " And nPos <" + nMaxPos.toString() + " And IdPrestamo <>" + IdPrestamoUpdate.toString())
                    }
                    else{
                        ODB.execSQL("Update " + strTabla + " Set " +
                                "nPos= nPos - 1 " +
                                "Where nPos >" + nMaxPos.toString() + " And nPos <=" + nNewPos.toString() + " And IdPrestamo <>" + IdPrestamoUpdate.toString())
                    }
                }
            }
            ODB.execSQL("Update " + strTabla + " Set " +
                    " nPos= " + nNewPos.toString() +
                    " Where IdPrestamo =" + IdPrestamoUpdate.toString())
            ODB.setTransactionSuccessful()
            ODB.endTransaction()
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
            ODB.endTransaction()
        }
        return this.StrProblema == ""
    }

    fun ActualizaEstado (IdPrestamo: Int, nNewEstado: Int) : Boolean{
        val db: SQLiteDatabase = _helper.writableDatabase
        try {
            db.beginTransaction()
            db.execSQL("Update " + this.strTabla + " Set " +
                    " nEstadoRegistro= " + nNewEstado.toString() +
                    " Where IdPrestamo =" + IdPrestamo.toString())
            db.setTransactionSuccessful()
            db.endTransaction()
            if (nNewEstado == 1 || nNewEstado == 3){
                ActualizarPosicion(IdPrestamo, -1, 0)
            }
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
            db.endTransaction()
        }
        return this.StrProblema == ""
    }

    override fun LoadAll(OCom: SQLiteDatabase): Boolean {
        val columnas = arrayOf(
            OPXC.Id,
            OPXC.IdPrestamo,
            OPXC.cFolio,
            OPXC.cCliente,
            OPXC.nMontoTotal,
            OPXC.nSaldoPendiente,
            OPXC.DteSaveInfo,
            OPXC.IdCliente,
            OPXC.IdClienteMoral,
            OPXC.IdGrupoSolidario,
            OPXC.cDireccion,
            OPXC.nEstadoRegistro,
            OPXC.nPos,
            OPXC.cGeoLocalizacion,
            OPXC.cColor
        )
        var cCondiciones = ""
        var cWhere: Array<String> = emptyArray()
        var cOrder = ""
        try {
            if (this.strTabla == this.OPXC.TABLE_NAME){
                cCondiciones = this.OPXC.nEstadoRegistro + "<>? AND " + this.OPXC.nEstadoRegistro + "<>?"
                cWhere = arrayOf("1", "3")
                cOrder = this.OPXC.nPos + " ASC"
            }
            this.c = OCom.query(this.strTabla, columnas, cCondiciones, cWhere, null, null, cOrder)
        }
        catch (ex: Exception){
            this.StrProblema = "ZX: " + ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Search(OCom: SQLiteDatabase, IntTipoConsulta: Int, StrValues: Array<String>): Boolean {
        var columnas = arrayOf(
            OPXC.Id,
            OPXC.IdPrestamo,
            OPXC.cFolio,
            OPXC.cCliente,
            OPXC.nMontoTotal,
            OPXC.nSaldoPendiente,
            OPXC.DteSaveInfo,
            OPXC.IdCliente,
            OPXC.IdClienteMoral,
            OPXC.IdGrupoSolidario,
            OPXC.cDireccion,
            OPXC.nEstadoRegistro,
            OPXC.nPos,
            OPXC.cGeoLocalizacion,
            OPXC.cColor
        )
        var cCondiciones = ""
        var cWhere: Array<String> = emptyArray()
        val cOrder = this.OPXC.nPos + " ASC"
        when (IntTipoConsulta){
            0 -> {
                cCondiciones = OPXC.IdPrestamo + "=?"
                cWhere = arrayOf(StrValues[0])
            }
            1 -> {
                cCondiciones = "(" + this.cFolio + " like ? and ?<>'') or (" + this.cCliente + " like ? and ?<>'')"
                cWhere = arrayOf("%" + StrValues[0] + "%", StrValues[0], "%" + StrValues[1] + "%", StrValues[1])
            }
            2 -> {
                columnas = emptyArray()
                columnas = arrayOf("Min(datetime(DteSaveInfo,'unixepoch')) as " + this.OPXC.DteSaveInfo)
            }
            3 -> {
                cCondiciones = this.OPXC.Id + "=?"
                cWhere = arrayOf(StrValues[0])
            }
        }
        try {
            this.c = OCom.query(this.strTabla, columnas, cCondiciones, cWhere, null, null, cOrder)
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Delete(OCom: SQLiteDatabase): Boolean {
        try {
            var cCondiciones = ""
            var cWhere: Array<String> = emptyArray()
            if (BlnDeleteAll){
                cCondiciones = ""
                cWhere = emptyArray()
                if (strTabla == this.OPXC.TABLE_NAME){
                    cCondiciones = "(" + this.OPXC.lManual + " = ?) Or (" + this.OPXC.lManual + " = ? And " + this.OPXC.nEstadoRegistro + "!= ? And " + this.OPXC.nEstadoRegistro + "!= ?)"
                    cWhere = arrayOf("0", "1", "0", "2")
                }
            }
            if (BlnDeleteByPrestamo){
                cCondiciones = this.OPXC.IdPrestamo + "=?"
                cWhere[0] = this.IdPrestamo.toString()
            }
            OCom.delete(strTabla, cCondiciones, cWhere)
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun getCursor(): Cursor? {
        return this.c
    }

    override fun FetchData(): Boolean {
        try {
            this.Id = c.getInt(0)
            this.IdPrestamo = c.getInt(1)
            this.cFolio = c.getString(2)
            this.cCliente = c.getString(3)
            this.nMontoTotal = c.getDouble(4)
            this.nSaldoPendiente = c.getDouble(5)
            this.nSaldoPendiente = c.getDouble(5)
            this.DteSaveInfo = SimpleDateFormat("dd-MM-yyyy").parse(c.getString(6)) as Date
            this.IdCliente = c.getInt(7)
            this.IdClienteMoral = c.getInt(8)
            this.IdGrupoSolidario = c.getInt(9)
            this.cDireccion = c.getString(10)
            this.nEstadoRegistro = c.getInt(11)
            this.nPos = c.getInt(12)
            this.cGeoLocalizacion = c.getString(13)
            this.cColor = c.getString(14)
            return true
        }
        catch (ex: Exception){}
        return false
    }
}

open class ClsIntegrantesOffline : ClsGenerica(){
    lateinit var c: Cursor
    var Id by Delegates.notNull<Int>()
    var IdRef by Delegates.notNull<Int>()
    var IRef by Delegates.notNull<Int>()
    var IdTipoRef by Delegates.notNull<Int>()
    var IdPrestamo by Delegates.notNull<Int>()
    var IdGrupoSolidario by Delegates.notNull<Int>()
    var IdRelGrupoCliente by Delegates.notNull<Int>()
    var IdCliente by Delegates.notNull<Int>()
    var IdRol by Delegates.notNull<Int>()
    var cNombre: String
    var nMonto by Delegates.notNull<Double>()
    var nGarantia by Delegates.notNull<Double>()
    var nMontoPago by Delegates.notNull<Double>()
    var TipoDelete: TipoDeleteIntegrantes

    var IGO = IntegrantesGrupoOffline

    init {
        this.Limpiar()
        this.Id = 0
        this.IdRef = 0
        this.IdTipoRef = 0
        this.IdPrestamo = 0
        this.IdGrupoSolidario = 0
        this.IdRelGrupoCliente = 0
        this.IdCliente = 0
        this.cNombre = ""
        this.nMonto = 0.0
        this.nGarantia = 0.0
        this.IdRol = 0
        this.nMontoPago = 0.0
        this.TipoDelete = TipoDeleteIntegrantes.Ninguno
    }

    fun validacion(ODB: SQLiteDatabase) : Boolean{
        return this.StrProblema == ""
    }

    fun RessetProblema(){
        this.StrProblema = ""
    }

    override fun Guardar(OCom: SQLiteDatabase): Boolean {
        try {
            val values = ContentValues()
            if (this.Id == 0){
                values.put(IGO.IdRef, this.IdRef)
                values.put(IGO.IdTipoRef, this.IdTipoRef)
                values.put(IGO.IdPrestamo, this.IdPrestamo)
                values.put(IGO.IdGrupoSolidario, this.IdGrupoSolidario)
                values.put(IGO.IdRelGrupoCliente, this.IdRelGrupoCliente)
                values.put(IGO.IdCliente, this.IdCliente)
                values.put(IGO.cNombre, this.cNombre)
                values.put(IGO.nMonto, this.nMonto)
                values.put(IGO.nGarantia, this.nGarantia)
                values.put(IGO.IdRol, this.IdRol)
                values.put(IGO.nMontoPago, this.nMontoPago)
                val LastId: Long = OCom.insert(IGO.TABLE_NAME, null, values)
                this.Id = LastId.toInt()
            }
            else{
                values.put(IGO.IdRef, this.IdRef)
                values.put(IGO.IdTipoRef, this.IdTipoRef)
                values.put(IGO.IdPrestamo, this.IdPrestamo)
                values.put(IGO.IdGrupoSolidario, this.IdGrupoSolidario)
                values.put(IGO.IdRelGrupoCliente, this.IdRelGrupoCliente)
                values.put(IGO.IdCliente, this.IdCliente)
                values.put(IGO.cNombre, this.cNombre)
                values.put(IGO.nMonto, this.nMonto)
                values.put(IGO.nGarantia, this.nGarantia)
                values.put(IGO.IdRol, this.IdRol)
                values.put(IGO.nMontoPago, this.nMontoPago)
                OCom.update(IGO.TABLE_NAME, values, IGO.Id + "=?", arrayOf(this.Id.toString()))
            }
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun LoadAll(OCom: SQLiteDatabase): Boolean {
        val columnas = arrayOf(
            IGO.Id,
            IGO.IdRef,
            IGO.IdTipoRef,
            IGO.IdPrestamo,
            IGO.IdGrupoSolidario,
            IGO.IdRelGrupoCliente,
            IGO.IdCliente,
            IGO.cNombre,
            IGO.nMonto,
            IGO.nGarantia,
            IGO.IdRol,
            IGO.nMontoPago
        )
        try {
            this.c = OCom.query(IGO.TABLE_NAME, columnas, null, null, null, null, null)
        }
        catch (ex: Exception){
            this.StrProblema = "ZX: " + ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Search(OCom: SQLiteDatabase, IntTipoConsulta: Int, StrValues: Array<String>): Boolean {
        val columnas = arrayOf(
            IGO.Id,
            IGO.IdRef,
            IGO.IdTipoRef,
            IGO.IdPrestamo,
            IGO.IdGrupoSolidario,
            IGO.IdRelGrupoCliente,
            IGO.IdCliente,
            IGO.cNombre,
            IGO.nMonto,
            IGO.nGarantia,
            IGO.IdRol,
            IGO.nMontoPago
        )
        var cCondiciones = ""
        var cWhere = emptyArray<String>()

        when(IntTipoConsulta){
            0 -> {
                cCondiciones = this.IGO.IdRef + "=? AND " + this.IGO.IdRef + "=? AND " + this.IGO.IdGrupoSolidario + "=?" //Todo el grupo
                cWhere = arrayOf(StrValues[0], StrValues[1], StrValues[2])
            }
            1 -> {
                cCondiciones = this.IGO.IdRef + "=? AND " + this.IGO.IdRef + "=? AND " + this.IGO.IdGrupoSolidario + "=? AND " + this.IGO.IdCliente + "=?"//Integrante
                cWhere = arrayOf(StrValues[0], StrValues[1], StrValues[2], StrValues[3])
            }
            2 -> {
                cCondiciones = IGO.Id + "=?"
                cWhere[0] = StrValues[0]
            }
            3 -> {
                cCondiciones = this.IGO.IdRef + "=? AND " + this.IGO.IdRef + "=?";//Todo de la referencia
                cWhere = arrayOf(StrValues[0], StrValues[1])
            }
        }
        try {
            this.c = OCom.query(IGO.TABLE_NAME, columnas, cCondiciones, cWhere, null, null, null)
        }
        catch (ex: Exception){
            this.StrProblema = "ZX: " + ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Delete(OCom: SQLiteDatabase): Boolean {
        var cWhere = emptyArray<String>()
        var cCondiciones = ""
        val StringArray = AppSofomConfigs()
        try {
            when (TipoDelete){
                TipoDeleteIntegrantes.All -> {}
                TipoDeleteIntegrantes.Grupo -> {
                    cCondiciones = this.IGO.IdRef + "=? AND " + this.IGO.IdTipoRef + "=? AND " + this.IGO.IdGrupoSolidario + "=?"
                    cWhere = StringArray.ObjectToStringArray(arrayOf(this.Id, this.IdTipoRef, this.IdGrupoSolidario))
                }
                TipoDeleteIntegrantes.Prestamo -> {
                    cCondiciones = this.IGO.IdTipoRef + "=? AND " + this.IGO.IdPrestamo + "=?"
                    cWhere = StringArray.ObjectToStringArray(arrayOf(this.IdTipoRef, this.IdPrestamo))
                }
                TipoDeleteIntegrantes.Tipo -> {
                    cCondiciones = this.IGO.IdTipoRef + "=?"
                    cWhere = StringArray.ObjectToStringArray(arrayOf(this.IdTipoRef))
                }
                TipoDeleteIntegrantes.Referencia -> {
                    cCondiciones = this.IGO.IdRef + "=? AND " + this.IGO.IdTipoRef + "=?"
                    cWhere = StringArray.ObjectToStringArray(arrayOf(this.Id, this.IdTipoRef))
                }
                TipoDeleteIntegrantes.Integrante -> {
                    cCondiciones = this.IGO.Id + "=?"
                    cWhere = StringArray.ObjectToStringArray(arrayOf(this.Id))
                }
                else -> {}
            }
            OCom.delete(IGO.TABLE_NAME, cCondiciones, cWhere)
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun getCursor(): Cursor? {
        return this.c
    }

    override fun FetchData(): Boolean {
        try {
            this.Id = c.getInt(0)
            this.IdRef = c.getInt(1)
            this.IdTipoRef = c.getInt(2)
            this.IdPrestamo = c.getInt(3)
            this.IdGrupoSolidario = c.getInt(4)
            this.IdRelGrupoCliente = c.getInt(5)
            this.IdCliente = c.getInt(5)
            this.cNombre = c.getString(6)
            this.nMonto = c.getDouble(7)
            this.nGarantia = c.getDouble(8)
            this.IdRol = c.getInt(9)
            this.nMontoPago = c.getDouble(10)
            return true
        }
        catch (ex: Exception){}
        return false
    }
}

open class ClsOffLineCobranza : ClsGenerica() {
    lateinit var c: Cursor
    var Id by Delegates.notNull<Int>()
    var IdPrestamo by Delegates.notNull<Int>()
    var cFolio: String
    var cCliente: String
    var nPendiente by Delegates.notNull<Double>()
    var nAlDia by Delegates.notNull<Double>()
    var nLiquidar by Delegates.notNull<Double>()
    lateinit var DteSaveInfo: Date
    var IdCliente by Delegates.notNull<Int>()
    var IdClienteMoral by Delegates.notNull<Int>()
    var IdGrupoSolidario by Delegates.notNull<Int>()
    var BlnDeleteAll by Delegates.notNull<Boolean>()

    var BlnDeleteAllPrestamo by Delegates.notNull<Boolean>()

    var OC = OfflineCobranza

    init {
        this.Limpiar()
        this.Id = 0
        this.IdPrestamo = 0
        this.cFolio = ""
        this.cCliente = ""
        this.nPendiente = 0.0
        this.nLiquidar = 0.0
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
        try {
            if (this.Id == 0){
                val values = ContentValues()
                values.put(this.OC.IdPrestamo, this.IdPrestamo)
                values.put(this.OC.cFolio, this.cFolio)
                values.put(this.OC.cCliente, this.cCliente)
                values.put(this.OC.nPendiente, this.nPendiente)
                values.put(this.OC.nAlDia, this.nAlDia)
                values.put(this.OC.nLiquidar, this.nLiquidar)
                values.put(this.OC.DteSaveInfo, this.DteSaveInfo.time)
                values.put(this.OC.IdCliente, this.IdCliente)
                values.put(this.OC.IdClienteMoral, this.IdClienteMoral)
                values.put(this.OC.IdGrupoSolidario, this.IdGrupoSolidario)
                val LastId: Long = OCom.insert(this.OC.TABLE_NAME, null, values)
                this.Id = LastId.toInt()
            }
        }
        catch (ex: Exception){
            Log.e("Error al guardar", ex.message.toString())
        }
        return this.StrProblema == ""
    }

    override fun LoadAll(OCom: SQLiteDatabase): Boolean {
        val columnas = arrayOf(
            this.OC.Id,
            this.OC.IdPrestamo,
            this.OC.cFolio,
            this.OC.cCliente,
            this.OC.nPendiente,
            this.OC.nAlDia,
            this.OC.nLiquidar,
            this.OC.DteSaveInfo,
            this.OC.IdCliente,
            this.OC.IdClienteMoral,
            this.OC.IdGrupoSolidario
        )
        try {
            this.c = OCom.query(this.OC.TABLE_NAME, columnas, null, null, null, null, null)
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Search(OCom: SQLiteDatabase, IntTipoConsulta: Int, StrValues: Array<String>): Boolean {
        var columnas = arrayOf(
            this.OC.Id,
            this.OC.IdPrestamo,
            this.OC.cFolio,
            this.OC.cCliente,
            this.OC.nPendiente,
            this.OC.nAlDia,
            this.OC.nLiquidar,
            this.OC.DteSaveInfo,
            this.OC.IdCliente,
            this.OC.IdClienteMoral,
            this.OC.IdGrupoSolidario
        )
        var cCondiciones = ""
        var cWhere = emptyArray<String>()
        when(IntTipoConsulta){
            0 -> {
                cCondiciones = this.OC.IdPrestamo + "=?"
                cWhere[0] = StrValues[0]
            }
            1 -> {
                cCondiciones = "(" + this.OC.cFolio + " like ? and ?<>'') or (" + this.OC.cCliente + " like ? and ?<>'')"
                cWhere = arrayOf( "%" + StrValues[0] + "%", StrValues[0], "%" + StrValues[1] + "%", StrValues[1] )
            }
            2 -> {
                columnas = emptyArray()
                columnas = arrayOf("Min(datetime(DteSaveInfo,'unixepoch')) as " + this.OC.DteSaveInfo)
            }
            3 -> {
                cCondiciones = this.OC.Id + "=?"
                cWhere[0] = StrValues[0]
            }
        }
        try {
            this.c = OCom.query(this.OC.TABLE_NAME, columnas, cCondiciones, cWhere, null, null, null)
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun Delete(OCom: SQLiteDatabase): Boolean {
        try {
            var cWhere = arrayOf(this.Id.toString())
            var cCondicion = this.OC.Id + "=?"
            if (BlnDeleteAll){
                cCondicion = ""
                cWhere = emptyArray()
            }
            if (BlnDeleteAllPrestamo){
                cCondicion = this.OC.IdPrestamo + "=?"
                cWhere[0] = this.IdPrestamo.toString()
            }
            OCom.delete(this.OC.TABLE_NAME, cCondicion, cWhere)
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        return this.StrProblema == ""
    }

    override fun FetchData(): Boolean {
        try {
            this.Id = c.getInt(0)
            this.IdPrestamo = c.getInt(1)
            this.cFolio = c.getString(2)
            this.cCliente = c.getString(3)
            this.nAlDia = c.getDouble(4)
            this.nLiquidar = c.getDouble(5)
            this.DteSaveInfo = SimpleDateFormat("dd-MM-yyyy").parse(c.getString(6)) as Date
            this.IdCliente = c.getInt(6)
            this.IdClienteMoral = c.getInt(7)
            this.IdGrupoSolidario = c.getInt(8)
            return true
        }
        catch (ex: Exception){}
        return false
    }
}

open class ClsPagos : ClsGenerica(){
    lateinit var c: Cursor
    var Id by Delegates.notNull<Int>()
    var IdPrestamo by Delegates.notNull<Int>()
    var cFolio: String
    var cCliente: String
    var nPago by Delegates.notNull<Double>()
    var IdMedioPago by Delegates.notNull<Int>()
    var cNumeroCheque: String
    var lTipoEmisor by Delegates.notNull<Boolean>()
    var cEmisor: String
    var nTipoAdelanto by Delegates.notNull<Int>()
    lateinit var DteSaveInfo: Date
    var cErrorWS: String
    var BlnDeleteAll by Delegates.notNull<Boolean>()
    var BlnDeleteAllPrestamo by Delegates.notNull<Boolean>()
    var BlnUpdateAll: Boolean
    lateinit var ORespuesta: AppCobranzaRespuesta

    init {
        this.Limpiar()
        this.Id = 0
        this.IdPrestamo = 0
        this.cFolio = ""
        this.cCliente = ""
        this.nPago = 0.0
        this.IdMedioPago = 0
        this.cNumeroCheque = ""
        this.lTipoEmisor = false
        this.cEmisor = ""
        this.nTipoAdelanto = 0
        this.cErrorWS = ""
        this.BlnDeleteAll = false
        this.BlnDeleteAllPrestamo = false
        this.BlnUpdateAll = false
    }

    fun validacion(ODB: SQLiteDatabase){
        var IntIdTipoSearch = 0
        var Data = ClsOffLineCobranza()
        ORespuesta = AppCobranzaRespuesta()
        var args = emptyArray<String>()
        if (PrestamoApp.IntIdCobranza == 0){
            args = arrayOf(PrestamoApp.IntIdPrestamo.toString())
        }
        else{
            args = arrayOf(PrestamoApp.IntIdPrestamo.toString())
            IntIdTipoSearch = 3
        }
        if (Data.Search(ODB, IntIdTipoSearch, args)){
            if(Data.c.moveToFirst()){
                ORespuesta.Exitoso = true
                PrestamoApp.IntIdCobranza = Data.c.getString(0).toInt()
                ORespuesta.IdPrestamo = Data.c.getString(1).toInt()
                ORespuesta.Folio = Data.c.getString(2)
                ORespuesta.Cliente = Data.c.getString(3)
                ORespuesta.Pendiente = Data.c.getString(4).toDouble()
                ORespuesta.AlDia = Data.c.getString(5).toDouble()
                ORespuesta.Liquidar = Data.c.getString(6).toDouble()
                var DteTest: Date = SimpleDateFormat("dd-MM-yyyy").parse(Data.c.getString(7)) as Date

            }
        }
    }
}