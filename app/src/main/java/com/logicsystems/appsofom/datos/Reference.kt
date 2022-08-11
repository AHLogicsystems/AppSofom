package com.logicsystems.appsofom.datos

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.logicsystems.appsofom.R
import java.net.URI


open class Reference {
    private var useDefaultCredentialsSetExplicitly: Boolean = false
    open var Url = ""
    var UseDefaultCredentials: Boolean = false
    open var StrProblema = ""
    fun WSAppSofom() {
        this.Url = "https://cib.logicsystems.com.mx/APPCOBRO/WSAppSofom.asmx"
        if (this.IsLocalFileSystemWebService(this.Url)) {
            this.UseDefaultCredentials = true
            this.useDefaultCredentialsSetExplicitly = false
        } else {
            this.useDefaultCredentialsSetExplicitly = true
        }
    }
    fun IsLocalFileSystemWebService(url: String): Boolean{
        var BlnReturn = false
        if (url == ""){
            BlnReturn = false
        }
        val wsUri = URI(url)
        if ((wsUri.port >= 1024)){
            BlnReturn = true
        }
        return BlnReturn
    }

//    fun AppLoginAsync(spinner: Spinner, StrUser: String, StrPass: String, StrEmpresa: String, StrIMEI: String) {
//        AppLoginAsync(spinner, StrUser, StrPass, StrEmpresa, StrIMEI, null)
//    }
//
//    fun AppLoginAsync(spinner: Spinner, StrUser: String, StrPass: String, StrEmpresa: String, StrIMEI: String, userState: Any?) {
//        Utils().doMyTask(spinner, "AppLogin", listOf(StrUser, StrPass, StrEmpresa, StrIMEI))
//    }

//    fun AppGetEmpresasAsync(txtTest : TextView) {
//        AppGetEmpresasAsync(txtTest, null)
//    }

//    fun AppGetEmpresasAsync(txtTest: TextView, userState: Any?) {
//        Utils().doMyTask(txtTest, "AppGetEmpresas", listOf())
//    }
    open fun alertasError(_context: Context, StrProblema: String){
        AlertDialog.Builder(_context).apply {
            setTitle("Mensaje del sistema")
            setMessage(StrProblema)
            setCancelable(false)
            setPositiveButton("Aceptar", null)
        }.show()
    }

    open fun progresBarWAlert(_context: Context, StrTexto: String) : AlertDialog.Builder{
        return AlertDialog.Builder(_context).apply {
            setMessage(StrTexto)
        }
    }

    open fun progressBarCIB(_context: Context) : AlertDialog{
        val builder = AlertDialog.Builder(_context)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        return builder.create()
    }
}