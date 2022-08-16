package com.logicsystems.appsofom.datos


import com.google.gson.Gson
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

open class Service : Reference() {
    var cJSON = ""


    inline fun <reified T : Any> parseJSON(JSON: String) : T {
        return Gson().fromJson(JSON, T::class.java)
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
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            this.StrProblema = "La base de datos no existe"
        } catch (e: IOException) {
            e.printStackTrace()
            this.StrProblema = e.message.toString()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
            this.StrProblema = e.message.toString()
        }
        return this.StrProblema == ""
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
        return this.StrProblema == ""
    }

    fun MultiWebMethodsApp(StrEmpresa: String, StrClaseNegocios: String, StrMetodo:String, StrParametros: String, StrUser: String, StrPass:String, StrIMEI: String) : Boolean{
        var reqParam = URLEncoder.encode("StrEmpresa", "UTF-8") + "=" + URLEncoder.encode(StrEmpresa, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrClaseNegocios", "UTF-8") + "=" + URLEncoder.encode(StrClaseNegocios, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrMetodo", "UTF-8") + "=" + URLEncoder.encode(StrMetodo, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrParametros", "UTF-8") + "=" + URLEncoder.encode(StrParametros, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrUser", "UTF-8") + "=" + URLEncoder.encode(StrUser, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrPass", "UTF-8") + "=" + URLEncoder.encode(StrPass, "UTF-8")
        reqParam += "&" + URLEncoder.encode("StrIMEI", "UTF-8") + "=" + URLEncoder.encode(StrIMEI, "UTF-8")
        try {

            val url = URL(this.Url + "/MultiWebMethodsApp?$reqParam")
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
        return this.StrProblema == ""
    }
}