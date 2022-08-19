package com.logicsystems.appsofom

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class ActivityGrupoSolidario : AppCompatActivity() {
    lateinit var listIntegrantes: RecyclerView
    lateinit var IMenuSave: MenuItem
    lateinit var container: LinearLayout
    lateinit var containerScroll: LinearLayout
    lateinit var scrollView1: ScrollView

    private var IdgrupoSolidario = 0
    private var bNuevo = false
    protected var porcentajeGarantia = 0.0
    protected var showRolGrupal = true
    private var nRenovacion = 0
    protected var editable = true
    lateinit var listaIntegrantes: Array<AppClienteGrupo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grupo_solidario)
    }


}

interface IGrupoSolidarioProvider {
    fun IsEditable(): Boolean
    fun IsShowRolGrupal(): Boolean
    fun GetPorcentajeGarantia(): Double
    fun GetBaseContext(): Context
    fun IsForPago(): Boolean
}

class IntegrantesAdapter(
    context: Activity,
    var datos: Array<AppClienteGrupo>,
    var master: IGrupoSolidarioProvider
) : RecyclerView.Adapter<IntegrantesAdapter.IntegranteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntegranteViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: IntegranteViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    class IntegranteViewHolder(item: View, master: IGrupoSolidarioProvider) : RecyclerView.ViewHolder(item){

    }
}