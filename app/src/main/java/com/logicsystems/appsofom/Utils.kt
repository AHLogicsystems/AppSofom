package com.logicsystems.appsofom

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import org.JsonAppSofom.JSONArray
import org.JsonAppSofom.XML
import org.json.JSONException
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import java.util.concurrent.Executors

class Utils : Reference(){
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())
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

    fun callApi(methodName: String, params: List<String>): String {
        var result = ""
        val SOAP_ACTION = SOAP_NAMESPACE + methodName
        val soapObject = SoapObject(SOAP_NAMESPACE, methodName)

        when (methodName){
            "AppGetEmpresas" -> {}
            "AppLogin" -> {
                soapObject.addProperty("StrUser", params[0])
                soapObject.addProperty("StrPass", params[1])
                soapObject.addProperty("StrEmpresa", params[2])
                soapObject.addProperty("StrIMEI", params[3])
            }
            "MultiWebMethodsApp" -> {
                soapObject.addProperty("StrEmpresa", params[0])
                soapObject.addProperty("StrClaseNegocios", params[1])
                soapObject.addProperty("StrMetodo", params[2])
                soapObject.addProperty("StrParametros", params[3])
                soapObject.addProperty("StrUser", params[4])
                soapObject.addProperty("StrPass", params[5])
                soapObject.addProperty("StrIMEI", params[6])
            }
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.setOutputSoapObject(soapObject)
        envelope.dotNet = true

        val httpTransportSE = HttpTransportSE(this.Url)

        val cXML = try {
            httpTransportSE.call(SOAP_ACTION, envelope)
            val soapPrimitive = envelope.response
            soapPrimitive.toString()
        } catch (e: Exception) {
            e.message.toString()
        }

        return cXML
    }

    fun doMyTask(tView : TextView, methodName: String, params: List<String>){
        myExecutor.execute {
            val response = callApi(methodName, params)
            val jsonReturn = convertXMLtoJSON(response)
            val ArrayJson = JSONArray(jsonReturn)

            val empresas = ArrayJson.getJSONObject(1)

            val Result = "Empresa" + empresas.getString("Empresa")
            myHandler.post {
                tView.text = Result
            }
        }
    }

    fun convertXMLtoJSON(cXML: String) : String{
        val INDENTATION = 4
        return try {
            val jsonObj = XML.toJSONObject(cXML)
            val json = jsonObj.toString(INDENTATION)
            println(json)
            json
        } catch (ex: JSONException) {
            ex.printStackTrace().toString()
        }
    }

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