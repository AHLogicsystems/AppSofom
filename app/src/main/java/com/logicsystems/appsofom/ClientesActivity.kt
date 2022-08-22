package com.logicsystems.appsofom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.datos.Catalogos
import com.logicsystems.appsofom.datos.REQUEST_CODES
import com.logicsystems.appsofom.datos.Service
import kotlinx.android.synthetic.main.activity_clientes.*

class ClientesActivity : AppCompatActivity() {
    var service = Service()
    var StrProblema: String = ""
    lateinit var txtClienteNombres: EditText
    lateinit var txtClienteApellidos: EditText
    lateinit var txtClienteRFC: EditText
    lateinit var txtViewTituloClienteBusqueda: TextView
    lateinit var IMenuAdd: MenuItem
    private var IntTypeSearch: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        txtClienteNombres = findViewById(R.id.txtClienteNombres)
        txtClienteApellidos = findViewById(R.id.txtClienteApellidos)
        txtClienteRFC = findViewById(R.id.txtClienteRFC)

        IntTypeSearch = intent.extras?.getInt("TypeSearch") ?: 0

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = when(IntTypeSearch){
            Catalogos.CLIENTES.ordinal,
            Catalogos.PLD.ordinal -> "Clientes"
            Catalogos.REFERENCIAS.ordinal,
            REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.ordinal -> "Referencias"
            else -> "Clientes"
        }

        txtViewTituloClienteBusqueda.text = "Buscar" + ActionBar.DISPLAY_SHOW_TITLE
        btnSearch.setOnClickListener {
            val intent = Intent(this, SearchResultClienteActivity::class.java)
            intent.putExtra("Nombres", txtClienteNombres.text)
            intent.putExtra("Apellidos", txtClienteApellidos.text)
            intent.putExtra("RFC", txtClienteRFC.text)
            intent.putExtra("TypeSearch", IntTypeSearch)
            ClienteApp.IntIdCliente = 0
            when(IntTypeSearch){
                REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.ordinal -> {
                    resultLauncher.launch(intent)
                }
            }
        }

    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            this.startActivity(intent)
        }
    }
}