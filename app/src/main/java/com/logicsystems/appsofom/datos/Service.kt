package com.logicsystems.appsofom.datos


import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.util.concurrent.Executors

class Service : Utils() {
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())
    val gson = Gson()
    var cJSON = ""

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

        try {
            httpTransportSE.call(SOAP_ACTION, envelope)
            val soapPrimitive = envelope.response
            cJSON = soapPrimitive.toString()
        } catch (ex: Exception) {
            if (ex.message.toString().contains("404")){
                this.StrProblema = "El entorno introducido no existe."
            }
            else{
                this.StrProblema = ex.message.toString()
            }
            Log.e("Error", this.StrProblema, ex.cause)
        }

        return this.cJSON
    }

//    fun getArrayEmpresas(context: Context, methodName: String) : AppListaEmpresa{
//        return gson.fromJson(callApi(methodName, arrayListOf()), AppListaEmpresa::class.java)
//    }

    inline fun <reified T : Any> parseJSON(JSON: String) : T {
        return gson.fromJson(JSON, T::class.java)
    }
}