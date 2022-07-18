package com.logicsystems.appsofom.datos

import android.R
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.gson.Gson
import com.logicsystems.appsofom.AppListaEmpresa
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.util.concurrent.Executors

class CallingsApi : Utils() {
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())
    val Mov = arrayListOf<String>()
    var cJSON = ""

    fun callApi(methodName: String, params: List<String>): Boolean {
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

        return this.StrProblema == ""
    }

    fun getArrayEmpresas(context: Context, sPinnerE : Spinner, methodName: String){
        myExecutor.execute {
            if (callApi(methodName, arrayListOf())){
                val gson = Gson()
                val ORespuesta = gson.fromJson(this.cJSON, AppListaEmpresa::class.java)
                for (OEach in ORespuesta.Empresas){
                    Mov.add(OEach.Empresa)
                }
                val AdapterEmpresas = ArrayAdapter(context, R.layout.simple_spinner_item, Mov)
                myHandler.post {
                    AdapterEmpresas.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    with(sPinnerE)
                    {
                        adapter = AdapterEmpresas
                        setSelection(0, false)
                        prompt = "Selecciona tu ambiente"
                        gravity = Gravity.CENTER
                        visibility = View.VISIBLE
                    }
                }
            }
            else{
                myHandler.post {
                    Toast.makeText(context, this.StrProblema, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}