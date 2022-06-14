package com.logicsystems.appsofom

import android.content.Context
import android.provider.Settings
import android.widget.Spinner
import android.widget.Toast
import com.logicsystems.appsofom.datos.ClsConfiguracion


open class AppSofomConfigs{
        var URL_MODOS = mapOf(
            DEBUG_MODE.LOCAL to "https://192.168.201.77/",
            DEBUG_MODE.TESTING_INTERNO to "http://testing.logicsystems.com.mx/",
            DEBUG_MODE.TESTING_EXTERNO to "http://187.141.66.20:5001/",
            DEBUG_MODE.PRODUCCION to "https://bkcib.logicsystems.com.mx/"
        )
        var IdConfiguracion: Int = 0
        var cNameEntorno: String = ""
        var cNameEmpresa: String = ""
        var nMinUpdateGPS = 0
        var nMinUpdateInfo = 0
        var cLogUser: String = ""
        var cLogPass: String = ""
        var cNameOperador: String = ""
        var cInfoTicket: String = ""
        var lLoggin: Boolean = false
        var nDigitosIdDispositivo: Int = 16
        var MODO = DEBUG_MODE.LOCAL
        fun URLWSFull(): String {return getURLFUll(cNameEntorno)}

        fun getURLFUll(entorno: String): String{
            val url: String = URL_MODOS[MODO]!!
            var cEntorno: String = ""
            if (entorno != "/") { cEntorno = entorno + "/" }
            return url + cEntorno + "WSAppSofom.asmx"
        }

        fun getIMEI(_context: Context): String {
            //TelephonyManager tm = (TelephonyManager)_context.GetSystemService(Android.Content.Context.TelephonyService);
            //StrIMEI = tm.DeviceId;
            return getIdInstalacion(_context)
        }

        fun getIdInstalacion(_context: Context): String {
            var IdInstalacionApp = ""
            try {
                IdInstalacionApp = Settings.Secure.ANDROID_ID
                //Formatemos el ID EN CASO DE ACTUALIZACIÓN DE LIBRERÍAS
                if (IdInstalacionApp.length < nDigitosIdDispositivo) {
                    IdInstalacionApp = IdInstalacionApp.padStart(nDigitosIdDispositivo, '0')
                } else {
                    IdInstalacionApp = IdInstalacionApp.substring(0, nDigitosIdDispositivo)
                }
            } catch (ex: Exception) {
                val innerMessage: String =
                    ex.printStackTrace().toString()
                val w: String = innerMessage
                Toast.makeText(_context, w,Toast.LENGTH_LONG).show()
            }
            return IdInstalacionApp.uppercase()
        }

        fun LoadConfig(context: Context) {
            val Data = ClsConfiguracion()
            Data.SetContext(context)
            if (Data.LoadAll()) {
                if (Data.c.moveToFirst()) {
                    IdConfiguracion = Data.c.getInt(0)
                    cNameEntorno = Data.c.getString(1)
                    cNameEmpresa = Data.c.getString(2)
                    nMinUpdateGPS = Data.c.getInt(3)
                    nMinUpdateInfo = Data.c.getInt(4)
                    cLogUser = Data.c.getString(5)
                    cLogPass = Data.c.getString(6)
                    cNameOperador = Data.c.getString(7)
                    cInfoTicket = Data.c.getString(8)
                }
            }
        }

    /*fun isOnLine(X: Context): Boolean {
        val cm = X.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetwork
        return netInfo != null && cm.activeNetwork
        return true
    }*/
}

internal class Recursos{
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