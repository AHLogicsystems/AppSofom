package com.logicsystems.appsofom

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
}