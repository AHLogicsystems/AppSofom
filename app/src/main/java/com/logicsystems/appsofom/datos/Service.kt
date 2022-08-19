package com.logicsystems.appsofom.datos


import android.os.StrictMode
import android.util.Log
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister

open class Service : Reference() {
    var StrResult = ""


//    inline fun <reified T : Any> parseJSON(JSON: String) : T {
//        return Gson().fromJson(JSON, T::class.java)
//    }
//    fun sendPostRequest(method:MetodosApp, parameters: Array<String>): Boolean {
//        var reqParam = ""
//            when(method){
//            MetodosApp.AppGetEmpresas -> reqParam = ""
//            MetodosApp.AppLogin -> {
//                reqParam = URLEncoder.encode("StrUser", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrPass", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrEmpresa", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrIMEI", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8")
//            }
//            MetodosApp.MultiWebMethodsApp -> {
//                reqParam = URLEncoder.encode("StrEmpresa", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrClaseNegocios", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrMetodo", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrParametros", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrUser", "UTF-8") + "=" + URLEncoder.encode(parameters[4], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrPass", "UTF-8") + "=" + URLEncoder.encode(parameters[5], "UTF-8")
//                reqParam += "&" + URLEncoder.encode("StrIMEI", "UTF-8") + "=" + URLEncoder.encode(parameters[6], "UTF-8")
//            }
//        }
//        try {
//            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//            StrictMode.setThreadPolicy(policy)
//            val mURL = URL(this.Url + "/" + method.name)
//
//            with(mURL.openConnection() as HttpURLConnection) {
//                requestMethod = "POST"
//
//
//                val wr = OutputStreamWriter(outputStream)
//                wr.write(reqParam)
//                wr.flush()
//
//                println("URL : $url")
//                println("Response Code : $responseCode")
//                var resultString = ""
//                BufferedReader(InputStreamReader(inputStream)).use {
//                    val response = StringBuffer()
//
//                    var inputLine = it.readLine()
//                    while (inputLine != null) {
//                        response.append(inputLine)
//                        inputLine = it.readLine()
//                    }
//                    println("Response : $response")
//
//                    StrResult = response.toString().replace("&lt;", "<").replace("&gt;", ">")
//                    StrResult = StrResult.replace("<string xmlns=\"http://LogicSystems.org/\"><?xml version=\"1.0\" encoding=\"utf-16\"?>", "")
//                    StrResult = StrResult.replace("</string>", "")
//                }
//            }
//        }
//        catch (e: FileNotFoundException) {
//            e.printStackTrace()
//            this.StrProblema = "La base de datos no existe"
//        } catch (e: IOException) {
//            e.printStackTrace()
//            this.StrProblema = e.message.toString()
//        } catch (e: XmlPullParserException) {
//            e.printStackTrace()
//            this.StrProblema = e.message.toString()
//        }
//        return this.StrProblema == ""
//    }

    fun callApi(method:MetodosApp, params: Array<String>): Boolean {
        val SOAP_ACTION = "http://LogicSystems.org/" + method.name
        val soapObject = SoapObject("http://LogicSystems.org/", method.name)

        when (method){
            MetodosApp.AppGetEmpresas -> {}
            MetodosApp.AppLogin -> {
                soapObject.addProperty("StrUser", params[0])
                soapObject.addProperty("StrPass", params[1])
                soapObject.addProperty("StrEmpresa", params[2])
                soapObject.addProperty("StrIMEI", params[3])
            }
            MetodosApp.MultiWebMethodsApp -> {
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
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            httpTransportSE.call(SOAP_ACTION, envelope)
            StrResult = envelope.response.toString()
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
}
inline fun <reified T : Any> DeserializeXML(string: String): T{
    val serializer: Serializer = Persister()
    val dataFetch = serializer.read(T::class.java, string)
    return dataFetch
}