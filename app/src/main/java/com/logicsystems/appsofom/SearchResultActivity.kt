package com.logicsystems.appsofom

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.Adapters.GenericBasicAdapter
import com.logicsystems.appsofom.datos.*


class SearchResultActivity : AppCompatActivity() {
    protected var StrProblema: String = ""
    val service = Service()
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

        service.Url = AppSofomConfigs.getURLFUll(AppSofomConfigs.NameEntorno)
        val StrIMEI = AppSofomConfigs.getIMEI(this)
        val OCapa = ClsCapaNegocios()
        var bl = false
        when(IntTypeSearch){
            SolicitudCredito.SOLICITUD.ordinal,
            SolicitudCredito.RENOVAR.ordinal,
            SolicitudCredito.ENTREGAR.ordinal,
            SolicitudCredito.COBRAR.ordinal -> {
                if (!OCapa.getXMLSearch(StrFolio, StrCliente, IntTypeSearch)){
                    service.alertasError(this).apply {
                        setMessage(OCapa.StrProblema)
                        setPositiveButton("Aceptar") { dialog, which ->
                                bl = true
                            }
                    }
                    if (bl){
                        val intent = Intent(this, MainActivity::class.java)
                        this.setResult(RESULT_CANCELED, intent)
                        this.finish()
                    }
                }
                else{
                    if (service.callApi(MetodosApp.MultiWebMethodsApp, arrayOf(AppSofomConfigs.NameEmpresa, ClsCapaNegocios.ClaseNegocios, Metodos.APPSEARCH, OCapa.StrXMLReturn, UserApp.StrUser, UserApp.StrPass, StrIMEI))) {
                        service_MultiWebMethodsAppCompleted()
                    }
                }
            }
            SolicitudCredito.REESTRUCTURAR.ordinal -> {
                if (AppSofomConfigs.isOnLine(this)){
                    if (!OCapa.getXMLSearch(StrFolio, StrCliente, IntTypeSearch)){
                        service.alertasError(this).apply {
                            setMessage(OCapa.StrProblema)
                            setPositiveButton("Aceptar") { dialog, which ->
                                bl = true
                            }
                        }
                        if (bl){
                            val intent = Intent(this, MainActivity::class.java)
                            this.setResult(RESULT_CANCELED, intent)
                            this.finish()
                        }
                    }
                    else{
                        if (service.callApi(MetodosApp.MultiWebMethodsApp, arrayOf(AppSofomConfigs.NameEmpresa, ClsCapaNegocios.ClaseNegocios, Metodos.APPSEARCH, OCapa.StrXMLReturn, UserApp.StrUser, UserApp.StrPass, StrIMEI))) {
                            service_MultiWebMethodsAppCompleted()
                        }
                    }
                }
                else{
                    val ORespuesa = AppSearchRespuesta()
                    if (IntTypeSearch == SolicitudCredito.ENTREGAR.ordinal){
                        val Data = ClsOfflineDisposicion()
                        var appCredito: AppCredito
                        Data.SetContext(this)
                        val args = arrayOf(StrFolio, StrCliente)
                        if (Data.Search(1, args)){
                            ORespuesa.Exitoso = true
                            if (Data.MoveToFirst()){
                                do {
                                    appCredito = AppCredito()
                                    appCredito.IdPrestamo = Data.IdPrestamo
                                    appCredito.Folio = Data.cFolio
                                    appCredito.Cliente = Data.cCliente
                                    appCredito.Monto = Data.nMontoTotal
                                    ORespuesa.Creditos.add(AppCredito())
                                } while (Data.MoveNext())
                            }
                        }
                        else{
                            ORespuesa.Error = Data.cProblema
                        }
                    }
                    else{
                        if (IntTypeSearch == SolicitudCredito.LISTACREDITOXCOBRAR.ordinal){
                            val Data = ClsOfflinePrestamoXOperador()
                            var appCredito = AppCredito()
                            Data.SetContext(this)
                            val args = arrayOf(StrFolio, StrCliente)
                            if (Data.Search(1, args)){
                                ORespuesa.Exitoso = true
                                if (Data.MoveToFirst()){
                                    do {
                                        appCredito = AppCredito()
                                        appCredito.IdPrestamo = Data.IdPrestamo
                                        appCredito.Folio = Data.cFolio
                                        appCredito.Cliente = Data.cCliente
                                        appCredito.Direccion = Data.cDireccion
                                        ORespuesa.Creditos.add(appCredito)
                                    } while (Data.MoveNext())
                                }
                            }
                            else{
                                ORespuesa.Error = Data.cProblema
                            }
                        }
                        else{
                            val Data = ClsOfflineCobranza()
                            var appCredito: AppCredito
                            val args = arrayOf(StrFolio, StrCliente)
                            if (Data.Search(1, args)){
                                ORespuesa.Exitoso = true
                                if (Data.MoveToFirst()){
                                    do {
                                        appCredito = AppCredito()
                                        appCredito.IdPrestamo = Data.IdPrestamo
                                        appCredito.Folio = Data.cFolio
                                        appCredito.Cliente = Data.cCliente
                                        ORespuesa.Creditos.add(appCredito)
                                    } while (Data.MoveNext())
                                }
                            }
                            else{
                                ORespuesa.Error = Data.cProblema
                            }
                        }
                    }
                    this.SetInfoList(ORespuesa)
                }
            }
        }

        listSearchResult.setOnItemClickListener{ parent, view, position, id ->
            val item2: GenericBasicAdapter<AppCredito> = listSearchResult.adapter as GenericBasicAdapter<AppCredito>
            val AppX: AppCredito = item2.getItemAtPosition(position)
            PrestamoApp.IntIdPrestamo = AppX.IdPrestamo

            if (IntTypeSearch == 6){
                val OPrestamoOper = ClsOfflinePrestamoXOperador()
                OPrestamoOper.SetContext(this)
                OPrestamoOper.IdPrestamo = AppX.IdPrestamo
                OPrestamoOper.cFolio = AppX.Folio
                OPrestamoOper.cCliente = AppX.Cliente
                OPrestamoOper.nMontoTotal = AppX.Monto
                OPrestamoOper.nSaldoPendiente = 0.0
                OPrestamoOper.IdCliente = AppX.IdCliente
                OPrestamoOper.IdClienteMoral = AppX.IdClienteMoral
                OPrestamoOper.IdGrupoSolidario = AppX.IdGrupoSolidario
                OPrestamoOper.cDireccion = AppX.Direccion
                OPrestamoOper.nPos = 0
                OPrestamoOper.cGeoLocalizacion = AppX.GeoLocalizacion
                OPrestamoOper.BlnUpdate = true
                OPrestamoOper.cColor = AppX.Color
                OPrestamoOper.Guardar()

                val OPrestamo = ClsOfflinePrestamoXCobrar()
                OPrestamo.SetContext(this)
                OPrestamo.IdPrestamo = AppX.IdPrestamo
                OPrestamo.cFolio = AppX.Folio
                OPrestamo.cCliente = AppX.Cliente
                OPrestamo.nMontoTotal = AppX.Monto
                OPrestamo.nSaldoPendiente = 0.0
                OPrestamo.IdCliente = AppX.IdCliente
                OPrestamo.IdClienteMoral = AppX.IdClienteMoral
                OPrestamo.IdGrupoSolidario = AppX.IdGrupoSolidario
                OPrestamo.cDireccion = AppX.Direccion
                OPrestamo.nPos = 10000
                OPrestamo.cGeoLocalizacion = AppX.GeoLocalizacion
                OPrestamo.cColor = AppX.Color
                OPrestamo.lManual = 1
                OPrestamo.BlnUpdate = true
                OPrestamo.Guardar()
                OPrestamo.ActualizarPosicion(OPrestamo.IdPrestamo, 0, 100000)
                OPrestamo.ActualizaEstado(OPrestamo.IdPrestamo, 0)
            }
            val intent = Intent(this, PrestamosActivity::class.java)
            intent.putExtra("TypeSearch", IntTypeSearch)
            this.setResult(RESULT_OK, intent)
            this.finish()
        }
        if (this.StrProblema != ""){
            service.alertasError(this, this.StrProblema)
            this.StrProblema = ""
        }
    }
    private fun service_MultiWebMethodsAppCompleted() {
        SetInfoList(DeserializeXML(service.StrResult))
    }

    fun SetInfoList(ORespuesa: AppSearchRespuesta) {
        if(ORespuesa.Exitoso){
            if (ORespuesa.Creditos.isNotEmpty()){
                listSearchResult.adapter = GenericBasicAdapter(this, ORespuesa.Creditos, true)
            }
            else{
                var bl = false
                service.alertasError(this).apply {
                    setMessage(StrProblema)
                    setPositiveButton("Aceptar") { dialog, which ->
                        bl = true
                    }
                }
                if (bl){
                    val intent = Intent(this, MainActivity::class.java)
                    this.setResult(RESULT_CANCELED, intent)
                    this.finish()
                }
            }
        }
        else {
            var bl = false
            service.alertasError(this).apply {
                setMessage(ORespuesa.Error)
                setPositiveButton("Aceptar") { dialog, which ->
                    bl = true
                }
            }
            if (bl){
                val intent = Intent(this, MainActivity::class.java)
                this.setResult(RESULT_CANCELED, intent)
                this.finish()
            }
        }
    }
}