package com.logicsystems.appsofom.datos


import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
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

    fun sendPostRequest(userName:String, password:String) {

        var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
        val mURL = URL("<Your API Link>")

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "POST"

            val wr = OutputStreamWriter(outputStream)
            wr.write(reqParam)
            wr.flush();

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                println("Response : $response")
            }
        }
    }
    fun AppGetEmpresas() : Boolean {
//        var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
//        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
            var BlnReturn = false
            try {

                val url = URL(this.Url + "/AppGetEmpresas?")
                val conn = url.openConnection() as HttpURLConnection
                conn.readTimeout = 10000
                conn.connectTimeout = 15000
                conn.requestMethod = "GET"
                conn.doInput = true
                conn.connect()
                val parserFactory = XmlPullParserFactory.newInstance()
                val parser = parserFactory.newPullParser()

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

                parser.setInput(conn.inputStream, null)

                var tag: String? = ""
                var text: String? = ""
                var event = parser.eventType
                while (event != XmlPullParser.END_DOCUMENT) {
                    tag = parser.name
                    when (event) {
                        XmlPullParser.START_TAG -> if (tag == "string") this.cJSON = text.toString()
                        XmlPullParser.TEXT -> text = parser.text
                        XmlPullParser.END_TAG -> if (tag == "string") this.cJSON = text.toString()
                    }
                    event = parser.next()
                }
            } catch (e: FileNotFoundException){
                e.printStackTrace()
                this.StrProblema = "La base de datos no existe"
            } catch (e: IOException) {
                e.printStackTrace()
                this.StrProblema = e.message.toString()
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
                this.StrProblema = e.message.toString()
            }
        if (this.StrProblema == "") BlnReturn = true
            return BlnReturn
    }

    fun AppLogin(StrUser: String, StrPass: String, StrEmpresa: String, StrIMEI: String) : Boolean {
        var reqParam = URLEncoder.encode("StrUser", "UTF-8") + "=" + URLEncoder.encode(StrUser, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrPass", "UTF-8") + "=" + URLEncoder.encode(StrPass, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrEmpresa", "UTF-8") + "=" + URLEncoder.encode(StrEmpresa, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrIMEI", "UTF-8") + "=" + URLEncoder.encode(StrIMEI, "UTF-8")
        var BlnReturn = false
        try {

            val url = URL(this.Url + "/AppLogin?$reqParam")
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "GET"
            conn.doInput = true
            conn.connect()
            val parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

            parser.setInput(conn.inputStream, null)

            var tag: String? = ""
            var text: String? = ""
            var event = parser.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                tag = parser.name
                when (event) {
                    XmlPullParser.START_TAG -> if (tag == "string") this.cJSON = text.toString()
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tag == "string") this.cJSON = text.toString()
                }
                event = parser.next()
            }
        } catch (e: FileNotFoundException){
            e.printStackTrace()
            this.StrProblema = "La base de datos no existe"
        } catch (e: IOException) {
            e.printStackTrace()
            this.StrProblema = e.message.toString()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
            this.StrProblema = e.message.toString()
        }
        if (this.StrProblema == "") BlnReturn = true
        return BlnReturn
    }
}