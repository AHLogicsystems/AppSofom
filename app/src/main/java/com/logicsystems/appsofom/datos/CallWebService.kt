package com.logicsystems.appsofom.datos

import com.logicsystems.appsofom.Utils
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class CallWebService {
    companion object{
        fun callApi(methodName: String, params: List<String>): String {
            var result = ""
            val SOAP_ACTION = Utils.SOAP_NAMESPACE + methodName
            val soapObject = SoapObject(Utils.SOAP_NAMESPACE, methodName)

            when (methodName){
                "AppGetEmpresas"-> {}
                "AppLogin" -> {
                    soapObject.addProperty("StrUser", params[0])
                    soapObject.addProperty("StrPass", params[1])
                    soapObject.addProperty("StrEmpresa", params[2])
                    soapObject.addProperty("StrIMEI", params[3])
                }
                "MultiWebMethodsApp" ->{
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

            val httpTransportSE = HttpTransportSE(Utils.SOAP_URL)

            result = try {
                httpTransportSE.call(SOAP_ACTION, envelope)
                val soapPrimitive = envelope.response
                soapPrimitive.toString()
            } catch (e: Exception) {
                e.printStackTrace().toString()
            }

            return result
        }
    }
}