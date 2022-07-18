package com.logicsystems.appsofom.datos

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ProgressBar
import android.widget.Toast
import com.logicsystems.appsofom.AppSofomConfigs
import com.logicsystems.appsofom.ClsCapaNegocios
import com.logicsystems.appsofom.Reference
import com.logicsystems.appsofom.UserApp
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader

open class Utils : Reference(){
    fun MultiWebMethodsAppAsync(context: Context, dialog: ProgressBar, StrMetodo: String, obtenerParametro: ClsCapaNegocios): Boolean {
        val StrIMEI: String = AppSofomConfigs().getIMEI(context)
        val parametro: ClsCapaNegocios = obtenerParametro
        return if (parametro.StrProblema != "") {
            Toast.makeText(context, parametro.StrProblema, Toast.LENGTH_LONG).show()
            false
        } else MultiWebMethodsAppAsync(
            dialog,
            AppSofomConfigs().cNameEmpresa,
            "AppSofom",
            StrMetodo,
            parametro.StrXMLReturn,
            UserApp.StrUser,
            UserApp.StrPass,
            StrIMEI
        )
    }
    fun MultiWebMethodsAppAsync(context: Context, dialog: ProgressBar, StrMetodo: String, parametro: String): Boolean? {
        val StrIMEI: String = AppSofomConfigs().getIMEI(context)
        return this.MultiWebMethodsAppAsync(
            dialog,
            AppSofomConfigs().cNameEmpresa,
            "AppSofom",
            StrMetodo,
            parametro,
            UserApp.StrUser,
            UserApp.StrPass,
            StrIMEI
        )
    }
    fun MultiWebMethodsAppAsync(dialog: ProgressBar?, StrEmpresa: String, StrClaseNegocios: String, StrMetodo: String, StrParametros: String, StrUser: String, StrPass: String, StrIMEI: String): Boolean {
        return true
    }
    companion object {
        val SOAP_NAMESPACE = "http://LogicSystems.org/"

        fun isConnected(context: Context): Boolean {

            // register activity with the connectivity manager service
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // if the android version is equal to M
            // or greater we need to use the
            // NetworkCapabilities to check what type of
            // network has the internet connection
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Returns a Network object corresponding to
                // the currently active default data network.
                val network = connectivityManager.activeNetwork ?: return false

                // Representation of the capabilities of an active network.
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    // Indicates this network uses a Wi-Fi transport,
                    // or WiFi has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    // Indicates this network uses a Cellular transport. or
                    // Cellular has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    // else return false
                    else -> false
                }
            } else {
                // if the android version is below M
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }
    }

//    fun convertXMLtoJSON(cXML: String) : String{
//        val INDENTATION = 4
//        return try {
//            val jsonObj = XML.toJSONObject(cXML)
//            val json = jsonObj.toString(INDENTATION)
//            println(json)
//            json
//        } catch (ex: JSONException) {
//            ex.printStackTrace().toString()
//        }
//    }

    data class Entry (val Any: List<Any>?)
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(type: Any, XmlArray: String, cXML: String) : List<*> {
        val nameClass = type.javaClass.name
        val MOV = mutableListOf<String>()
        val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        factory.setNamespaceAware(true)
        val xpp: XmlPullParser = factory.newPullParser()
        xpp.setInput(StringReader(cXML))
        var eventType = xpp.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {



            if (eventType == XmlPullParser.START_DOCUMENT) {
                println("Start document")
            } else if (eventType == XmlPullParser.START_TAG) {
                println("Start tag " + xpp.name)
            } else if (eventType == XmlPullParser.END_TAG) {
                println("End tag " + xpp.name)
            } else if (eventType == XmlPullParser.TEXT) {
                println("Text " + xpp.text)
                if (xpp.text.trim() != "") MOV.add(xpp.text)
            }
            eventType = xpp.next()
        }
        println("End document")
        return MOV
    }

}