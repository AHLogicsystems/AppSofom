package com.logicsystems.appsofom.datos

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.Executors


open class ClsGenerica : AppCompatActivity(){
    protected var StrProblema: String = ""
    val cProblema: String = this.StrProblema

    lateinit var myContext: Context

    val service = Service()
    val config = AppSofomConfigs()
    val OCom = ConnectSql().dbConn()?.createStatement()

    private lateinit var _helper: DataManagerHelper
    fun SetContext(context: Context) {
        this.myContext = context
        _helper = DataManagerHelper(context)
    }

    open fun readXML(cXML: InputStream): String{
        var cReturn = ""
        // Try and Catch for avoiding the application to crash
        try {

            // creating a user list string hash map arraylist
            val userList = ArrayList<HashMap<String, String?>>()
            var user = HashMap<String, String?>()

            //creating a XmlPull parse Factory instance
            val parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()

            // setting the namespaces feature to false
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

            // setting the input to the parser
            parser.setInput(cXML, null)

            // working with the input stream
            var tag: String? = ""
            var text: String? = ""
            var event = parser.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                tag = parser.name
                when (event) {
                    XmlPullParser.START_TAG -> if (tag == "string") user = HashMap()
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> when (tag) {
                        "string" -> cReturn = text.toString()
                        "user" -> userList.add(user)
                    }
                }
                event = parser.next()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        }
        return cReturn
    }


    val myExecutor = Executors.newSingleThreadExecutor()
    val myHandler = Handler(Looper.getMainLooper())

    fun Limpiar() {
        this.StrProblema = ""
    }

    fun LoadAll(): Boolean {
        var BlnReturn = false
        val db: SQLiteDatabase = _helper.writableDatabase
        try {
            if (!LoadAll(db)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun LoadReg(): Boolean {
        var BlnReturn: Boolean = false
        val db: SQLiteDatabase = _helper.writableDatabase
        val nMaxRegistros = 30
        try {
            if (!LoadReg(db, nMaxRegistros)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun Search(IntTipoConsulta: Int, StrValues: Array<String>): Boolean {
        var BlnReturn: Boolean = false
        val db: SQLiteDatabase = _helper.writableDatabase
        try {
            if (!Search(db, IntTipoConsulta, StrValues)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun Guardar(): Boolean {
        var BlnReturn = false

        val db: SQLiteDatabase = _helper.writableDatabase
        val dbHelper = DataManagerHelper(myContext)
        dbHelper.writableDatabase
        db.beginTransaction()
        try {
            if (Guardar(db)) {
                db.setTransactionSuccessful()
            } else {
                this.StrProblema = "Error: " + this.StrProblema
            }
            db.endTransaction()
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
            db.endTransaction()
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    fun Delete(): Boolean {
        var BlnReturn = false
        val db: SQLiteDatabase = _helper.writableDatabase
        db.beginTransaction()
        try {
            if (Delete(db)) {
                db.setTransactionSuccessful()
            } else {
                this.StrProblema = "Error: " + this.StrProblema
            }
            db.endTransaction()
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
            db.endTransaction()
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    open fun Guardar(OCom: SQLiteDatabase): Boolean {
        return true
    }

    private fun Delete(OCom: SQLiteDatabase): Boolean {
        return true
    }

    open fun LoadAll(OCom: SQLiteDatabase): Boolean {
        return true
    }

    private fun LoadReg(OCom: SQLiteDatabase, nMaxRegistros: Int): Boolean {
        return true
    }

    private fun Load(OCom: SQLiteDatabase): Boolean {
        return true
    }

    private fun Search(OCom: SQLiteDatabase,IntTipoConsulta: Int, StrValues: Array<String>): Boolean{
        val BlnReturn = false
        val db: SQLiteDatabase = _helper.writableDatabase

        try {
            if (!this.Search(db, IntTipoConsulta, StrValues)) {
                this.StrProblema = "Error: " + this.StrProblema
            }
        } catch (ex: java.lang.Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        return BlnReturn
    }

    private fun getCursor(): Cursor? {
        return null
    }

    fun FetchData(): Boolean {
        return false
    }

    fun MoveToFirst(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToFirst() == true) {
                return this.FetchData()
            }
        }
        return false
    }

    fun MoveToLast(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToLast() == true) {
                return FetchData()
            }
        }
        return false
    }

    fun MoveNext(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToNext() == true) {
                return FetchData()
            }
        }
        return false
    }

    fun MovePrevious(): Boolean {
        if (this.getCursor() != null) {
            if (this.getCursor()?.moveToPrevious() == true) {
                return FetchData()
            }
        }
        return false
    }
}
object MD5 {
    fun toMD5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}
class GeoCoordinate{
//    fun GeoCoordinate(Latitude: Double, Longitude:Double)
//    {
//        this.Latitude = Latitude
//        this.Longitude = Longitude
//    }
    var Latitude: Double = 0.0
    var Longitude: Double = 0.0
}