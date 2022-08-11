package com.logicsystems.appsofom

import android.os.Bundle
import android.widget.ListView
import android.widget.Toolbar
import com.logicsystems.appsofom.datos.ClsGenerica
import com.logicsystems.appsofom.datos.Metodos

class SearchResultActivity : ClsGenerica() {
    lateinit var listSearchResult: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        listSearchResult = findViewById(R.id.listPrestamos)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.title = "Resultados de la búsqueda"

        val StrFolio = intent.extras?.getString("Folio") ?: ""
        val StrCliente = intent.extras?.getString("Cliente") ?: ""
        val IntTypeSearch = intent.extras?.getInt("TypeSearch") ?: 0

        service.Url = config.URLWSFull()

        service.MultiWebMethodsApp(config.cNameEmpresa, ClaseNegocios, Metodos.APPSEARCH, "", UserApp.StrUser, UserApp.StrUser, config.cIMEI)
    }
}