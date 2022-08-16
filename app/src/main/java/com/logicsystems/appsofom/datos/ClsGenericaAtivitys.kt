package com.logicsystems.appsofom.datos

import androidx.appcompat.app.AppCompatActivity


open class GenericaActivitys : AppCompatActivity(){
    protected var StrProblema: String = ""
    val cProblema: String = this.StrProblema
    val config = AppSofomConfigs()
    val ClaseNegocios = "AppSofom"
}