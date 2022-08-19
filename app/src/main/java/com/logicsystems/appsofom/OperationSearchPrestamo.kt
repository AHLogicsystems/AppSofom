package com.logicsystems.appsofom

import android.os.Bundle
import android.widget.GridLayout
import androidx.recyclerview.widget.RecyclerView
import com.logicsystems.appsofom.datos.GenericaActivitys
import com.logicsystems.appsofom.datos.Service

class OperationSearchPrestamo : GenericaActivitys() {
    val service = Service()
    lateinit var OCapa: ClsCapaNegocios
    lateinit var containerEntregaIndividual: GridLayout
    lateinit var listIntegrantes: RecyclerView
    lateinit var listaIntegrantes: Array<AppClienteGrupo>
//    lateinit var adapter: IntegrantesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_search_prestamo)
    }
}