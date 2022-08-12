package com.logicsystems.appsofom

import android.os.Bundle
import com.logicsystems.appsofom.datos.GenericaActivitys

class ClientesActivity : GenericaActivitys() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)
    }
}