package com.logicsystems.appsofom.datos

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.widget.Spinner
import android.widget.Toast
import com.logicsystems.appsofom.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.truncate


open class AppSofomConfigs{
    companion object{
        var URL_MODOS = mapOf(
            DEBUG_MODE.LOCAL to "https://192.168.201.77/",
            DEBUG_MODE.TESTING_INTERNO to "https://testing.logicsystems.com.mx/",
            DEBUG_MODE.TESTING_EXTERNO to "http://187.141.66.20:5001/",
            DEBUG_MODE.PRODUCCION to "https://cib.logicsystems.com.mx/"
        )
        var IdConfiguracion: Int = 0
        var NameEntorno: String = ""
        var NameEmpresa: String = ""
        var MinUpdateGPS = 0
        var MinUpdateInfo = 0
        var LogUser: String = ""
        var LogPass: String = ""
        var NameOperador: String = ""
        var InfoTicket: String = ""
        var lLoggin: Boolean = false
        var nDigitosIdDispositivo: Int = 16

        var MODO = DEBUG_MODE.TESTING_INTERNO
        var URLWSFull = getURLFUll(NameEntorno)
//        fun URLWSFull(): String {return getURLFUll(NameEntorno)}

        fun getURLFUll(entorno: String = ""): String{
            val url: String = URL_MODOS[MODO]!!
            var cEntorno: String = ""
            if (entorno != "/") { cEntorno = entorno + "/" }
            return url + cEntorno + "WSAppSofom.asmx"
        }

        fun getIMEI(_context: Context) : String{
            val cIMEI = getIdInstalacion(_context)
            return cIMEI
        }

        @SuppressLint("HardwareIds")
        fun getIdInstalacion(_context: Context): String {
            var IdInstalacionApp = ""
            try {
                IdInstalacionApp = Settings.Secure.getString(_context.contentResolver, Settings.Secure.ANDROID_ID)
                if (IdInstalacionApp.count() < nDigitosIdDispositivo)
                {
                    IdInstalacionApp = IdInstalacionApp.padStart(nDigitosIdDispositivo, '0');
                }
                else
                {
                    IdInstalacionApp = IdInstalacionApp.substring(0, nDigitosIdDispositivo);
                }
            } catch (ex: Exception) {
                val innerMessage: String =
                    ex.printStackTrace().toString()
                val w: String = innerMessage
                Toast.makeText(_context, w, Toast.LENGTH_LONG).show()
            }
            return IdInstalacionApp.uppercase()
        }

        fun isOnLine(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            } else {
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }

        fun LoadConfig(context: Context) {
            val Data = ClsConfiguracion()
            Data.SetContext(context)
            if (Data.LoadAll()) {
                if (Data.c.moveToFirst()) {
                    IdConfiguracion = Data.c.getInt(0)
                    NameEntorno = Data.c.getString(1)
                    NameEmpresa = Data.c.getString(2)
                    MinUpdateGPS = Data.c.getInt(3)
                    MinUpdateInfo = Data.c.getInt(4)
                    LogUser = Data.c.getString(5)
                    LogPass = Data.c.getString(6)
                    NameOperador = Data.c.getString(7)
                    InfoTicket = Data.c.getString(8)
                }
            }
        }
    }

    private var EarthRadius = 6371.0
    fun GetDistance(point1: GeoCoordinate, point2: GeoCoordinate): Double{
        var distance = 0.0
        val Lat = (point2.Latitude - point1.Latitude) * (Math.PI / 100)
        val Lon = (point2.Longitude - point1.Longitude) * (Math.PI / 100)
        val a = Math.sin(Lat / 2) * Math.sin(Lat / 2) * Math.cos(point1.Latitude * (Math.PI)) * Math.cos(point2.Latitude * (Math.PI / 100)) * Math.sin(Lon / 2) * Math.sin((Lon / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        distance = EarthRadius * c
        distance = truncate(distance)
        return distance
    }

    fun ObjectToStringArray(parametros: Array<Any>) : Array<String>{
        val arreglo = emptyArray<String>()
        for (i in parametros.indices){
            arreglo[i] = parametros[i].toString()
        }
        return arreglo
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun Boolean.toInt() = if (this) 1 else 0

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}

internal class Recursos{
    companion object{
        fun GetIDCombo(_context: Context, spinner: Spinner): Int{
            var intReturn = 0
            var Opciones: Array<String> = arrayOf()
            var sVal = ""
            when (spinner.tag.toString().uppercase()){
                "SPGENERO" -> {
                    Opciones = _context.resources.getStringArray(R.array.Genero_array_values)
                    sVal = "Genero"
                }
                "SPENTIDADFEDNAC" -> {
                    Opciones = _context.resources.getStringArray(R.array.Estado_array_values)
                    sVal = "Entidad"
                }
                "SPNACIONALIDAD" -> {
                    Opciones = _context.resources.getStringArray(R.array.Pais_array_values)
                    sVal = "Nacionalidad"
                }
                "SPESTADOCIVIL" -> {
                    Opciones = _context.resources.getStringArray(R.array.EstadoCivil_array_values)
                    sVal = "Estado Civil"
                }
                "SPESCOLARIDAD" -> {
                    Opciones = _context.resources.getStringArray(R.array.Escolaridad_array_values)
                    sVal = "Escolaridad"
                }
                "SPTIPOTELEFONO" -> {
                    Opciones = _context.resources.getStringArray(R.array.TiposTelefono_array_values)
                    sVal = "Tipo Telefono"
                }
            }
            intReturn = Opciones[spinner.selectedItemId.toInt()].toInt()
            return intReturn
        }

        fun GetPositionCombo(_context: Context, spinner: Spinner, ValueSearch: String){
            val Opciones: Array<String> = when (spinner.tag.toString().uppercase()){
                "SPGENERO" -> _context.resources.getStringArray(R.array.Genero_array_values)
                "SPENTIDADFEDNAC" -> _context.resources.getStringArray(R.array.Estado_array_values)
                "SPNACIONALIDAD" -> _context.resources.getStringArray(R.array.Pais_array_values)
                "SPESTADOCIVIL" -> _context.resources.getStringArray(R.array.EstadoCivil_array_values)
                "SPESCOLARIDAD" -> _context.resources.getStringArray(R.array.Escolaridad_array_values)
                "SPTIPOTELEFONO" -> _context.resources.getStringArray(R.array.TiposTelefono_array_values)
                else -> arrayOf()
            }

            var i = -1

            for (sValues in Opciones){
                i++
                if(sValues == ValueSearch){
                    spinner.setSelection(i)
                    break
                }
            }
        }
    }
}

fun Int.toBoolean(): Boolean = java.lang.Boolean.parseBoolean(this.toString())