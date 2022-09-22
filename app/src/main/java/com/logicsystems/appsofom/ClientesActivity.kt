package com.logicsystems.appsofom

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.datos.Catalogos
import com.logicsystems.appsofom.datos.ProgressDialog
import com.logicsystems.appsofom.datos.REQUEST_CODES
import com.logicsystems.appsofom.datos.Service
import kotlinx.android.synthetic.main.activity_clientes.*


class ClientesActivity : AppCompatActivity() {
    lateinit var progress: Dialog
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

        progress = ProgressDialog.progressDialog(this)
        txtClienteNombres = findViewById(R.id.txtClienteNombres)
        txtClienteApellidos = findViewById(R.id.txtClienteApellidos)
        txtClienteRFC = findViewById(R.id.txtClienteRFC)
        txtViewTituloClienteBusqueda = findViewById(R.id.txtViewTituloClienteBusqueda)

        IntTypeSearch = intent.extras?.getInt("TypeSearch") ?: 0

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = when(IntTypeSearch){
            Catalogos.CLIENTES.ordinal,
            Catalogos.PLD.ordinal -> "Clientes"
            Catalogos.REFERENCIAS.ordinal,
            REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.valor -> "Referencias"
            else -> "Clientes"
        }

        txtViewTituloClienteBusqueda.text = "Buscar" + ActionBar.DISPLAY_SHOW_TITLE
        btnSearch.setOnClickListener {
            progress.show()
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, SearchResultClienteActivity::class.java)
                intent.putExtra("Nombres", txtClienteNombres.text)
                intent.putExtra("Apellidos", txtClienteApellidos.text)
                intent.putExtra("RFC", txtClienteRFC.text)
                intent.putExtra("TypeSearch", IntTypeSearch)
                ClienteApp.IntIdCliente = 0
                when(IntTypeSearch){
                    REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.valor -> {
                        resultLauncher.launch(intent)
                    }
                    else -> {
                        resultLauncher.launch(intent, 1254)
                    }
                }
                progress.dismiss()
            }, 1000)
        }
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            IntTypeSearch = result.data?.extras?.getInt("TypeSearch") ?: 0
            val IdClienteResult: Int = result.data?.extras?.getInt("IdCliente") ?: 0
            when(IntTypeSearch){
                REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.valor -> {
                    val NombreClienteResult = result.data?.extras?.getString("NombreCliente") ?: ""

                    val intent = Intent(this, CatClientesActivity::class.java)
                        .putExtra("IdClienteResult", IdClienteResult)
                        .putExtra("NombreClienteResult", NombreClienteResult)
                        .putExtra("TypeSearchResult", IntTypeSearch)
                    this.setResult(Activity.RESULT_OK, intent)
                    this.finish()

                }
                Catalogos.CLIENTES.ordinal -> {
                    val intent1 = Intent(this, CatClientesActivity::class.java)
                        .putExtra("TypeSearch", IntTypeSearch)
                        .putExtra("IdCliente", IdClienteResult)
                    this.startActivity(intent1)
                }
                Catalogos.REFERENCIAS.ordinal -> {
                    val intent2 = Intent(this, CatReferenciasActivity::class.java)
                        .putExtra("TypeSearch", IntTypeSearch)
                        .putExtra("IdCliente", IdClienteResult)
                    this.startActivity(intent2)
                }
                Catalogos.PLD.ordinal -> {
                    if (ClienteApp.IntIdCliente > 0){
                        val intent = Intent(this, ClientePldActivity::class.java)
                            .putExtra("idCliente", ClienteApp.IntIdCliente)
                        this.startActivity(intent)
                    }
                    else{
                        this.StrProblema = "Hubo un error al seleccionar al cliente."
                    }
                }
            }
            if(this.StrProblema == "") this.finish()
            else service.alertasError(this, this.StrProblema); this.StrProblema = ""
        }
        else{
            ClienteApp.IntIdCliente = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        ClienteApp.IntIdCliente = 0
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        when(IntTypeSearch){
            Catalogos.CLIENTES.ordinal,
                Catalogos.REFERENCIAS.ordinal -> {
                    menuInflater.inflate(R.menu.menu_cat, menu)
                IMenuAdd = menu.findItem(R.id.menu_add)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add){
            if(IntTypeSearch in arrayListOf(Catalogos.REFERENCIAS.ordinal, REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.valor)){
                val intent2 = Intent(this, CatReferenciasActivity::class.java)
                    .putExtra("TypeSearch", IntTypeSearch)
                    .putExtra("IdCliente", 0)

                resultLauncher2.launch(intent2)
                this.finish()
            }
            else{
                val intent = Intent(this, CatClientesActivity::class.java)
                    .putExtra("TypeSearch", IntTypeSearch)
                    .putExtra("IdCliente", 0)

                resultLauncher2.launch(intent)
                this.finish()
            }
        }
        else{
            this.finish()
        }
        return true
    }

    var resultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            this.startActivity(this.intent)
        }
    }
}