package com.logicsystems.appsofom

import java.net.URI


open class Reference {
    private var useDefaultCredentialsSetExplicitly: Boolean = false
    var Url: String = ""
    var UseDefaultCredentials: Boolean = false
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

    fun AppLoginAsync(StrUser: String, StrPass: String, StrEmpresa: String, StrIMEI: String) {
        AppLoginAsync(StrUser, StrPass, StrEmpresa, StrIMEI, null)
    }

    fun AppLoginAsync(StrUser: String, StrPass: String, StrEmpresa: String, StrIMEI: String, userState: Any?) {
        activity_soap().doMyTask("AppLogin", listOf(StrUser, StrPass, StrEmpresa, StrIMEI))
    }

    fun AppGetEmpresasAsync() {
        AppGetEmpresasAsync(null)
    }

    fun AppGetEmpresasAsync(userState: Any?) {
        activity_soap().doMyTask("AppGetEmpresas", listOf())
    }
}