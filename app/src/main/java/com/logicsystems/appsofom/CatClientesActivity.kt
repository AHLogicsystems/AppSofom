package com.logicsystems.appsofom

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.datos.*
import kotlinx.android.synthetic.main.activity_cat_clientes.*
import java.util.*


open class CatClientesActivity() : AppCompatActivity() {
    private var StrProblema: String = ""
    private val service = Service()
    private val calendar = Calendar.getInstance()

    lateinit var progress: Dialog

    lateinit var spGenero: Spinner
    lateinit var spEntidadFedNac: Spinner
    lateinit var spNacionalidad: Spinner
    lateinit var spEstadoCivil: Spinner
    lateinit var spEscolaridad: Spinner

    lateinit var _dateDisplay: TextView
    lateinit var _dateSelectButton: Button

    lateinit var Nombre1: EditText
    lateinit var Nombre2: EditText
    lateinit var ApellidoPat: EditText
    lateinit var ApellidoMat: EditText
    lateinit var RFC: EditText
    lateinit var Curp: EditText
    lateinit var Salario: EditText
    lateinit var OtrosIngresos: EditText
    lateinit var RetencionNomina: EditText
    lateinit var Renta: EditText
    lateinit var OtrosGastos: EditText
    lateinit var cOtrosIngresos: EditText

    private var IntTypeSearch = 0
    private var Idcliente = 0
    private var bNuevo = false
    lateinit var IMenuAdd: MenuItem
    lateinit var IMenuTels: MenuItem

    lateinit var OCapa: ClsCapaNegocios

    lateinit var BtnSrchRefLab1: Button
    lateinit var BtnDelRefLab1: Button
    lateinit var txtIdRefLab1: EditText
    lateinit var txtRefLab1: EditText

    lateinit var BtnSrchRefLab2: Button
    lateinit var BtnDelRefLab2: Button
    lateinit var txtIdRefLab2: EditText
    lateinit var txtRefLab2: EditText

    lateinit var BtnSrchRefVec1: Button
    lateinit var BtnDelRefVec1: Button
    lateinit var txtIdRefVec1: EditText
    lateinit var txtRefVec1: EditText

    lateinit var BtnSrchRefVec2: Button
    lateinit var BtnDelRefVec2: Button
    lateinit var txtIdRefVec2: EditText
    lateinit var txtRefVec2: EditText

    lateinit var BtnSrchNegocio: Button
    lateinit var BtnDelNegocio: Button
    lateinit var txtIdNegocio: EditText
    lateinit var txtNegocio: EditText

    lateinit var txtIdDomicilio: EditText
    lateinit var txtDomicilio: EditText
    lateinit var txtIdClienteDireccion: EditText
    lateinit var dateDialog: DatePickerDialog
    var muestraErrorCurp = true

//    constructor(parcel: Parcel) : this() {
//        IntTypeSearch = parcel.readInt()
//        Idcliente = parcel.readInt()
//        bNuevo = parcel.readByte() != 0.toByte()
//        muestraErrorCurp = parcel.readByte() != 0.toByte()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_clientes)

        progress = ProgressDialog.progressDialog(this)

        IntTypeSearch = intent.extras?.getInt("TypeSearch") ?: 0
        Idcliente = intent.extras?.getInt("IdCliente") ?: 0
        bNuevo = (Idcliente == 0)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Catálogo de Clientes"

        Nombre1 = findViewById(R.id.txtClienteNombre1)
        Nombre2 = findViewById(R.id.txtClienteNombre2)
        ApellidoPat = findViewById(R.id.txtClienteApellidoPat)
        ApellidoMat = findViewById(R.id.txtClienteApellidoMat)
        Curp = findViewById(R.id.txtClienteCurp)
        Salario = findViewById(R.id.txtClienteSalario)
        OtrosIngresos = findViewById(R.id.txtClienteMontoOtrosIngresos)
        cOtrosIngresos = findViewById(R.id.txtClientecOtrosIngresos)

        RetencionNomina = findViewById(R.id.txtClienteRetencionNomina)
        Renta = findViewById(R.id.txtClienteRenta)
        OtrosGastos = findViewById(R.id.txtClienteOtrosGastos)
        RFC = findViewById(R.id.txtClienteRFC)

        spGenero = findViewById(R.id.spGenero)
        spEntidadFedNac = findViewById(R.id.spEntidadFedNac)
        spNacionalidad = findViewById(R.id.spNacionalidad)
        spEstadoCivil = findViewById(R.id.spEstadoCivil)
        spEscolaridad = findViewById(R.id.spEscolaridad)

        spGenero.tag = "spGenero"
        spEntidadFedNac.tag = "spEntidadFedNac"
        spNacionalidad.tag = "spNacionalidad"
        spEstadoCivil.tag = "spEstadoCivil"
        spEscolaridad.tag = "spEscolaridad"

        val AdGenero = ArrayAdapter.createFromResource(
            this,
            R.array.Genero_array,
            android.R.layout.simple_spinner_item
        )
        val AdEntidadFedNac = ArrayAdapter.createFromResource(
            this,
            R.array.Estado_array,
            android.R.layout.simple_spinner_item
        )
        val AdNacionalidad = ArrayAdapter.createFromResource(
            this,
            R.array.Pais_array,
            android.R.layout.simple_spinner_item
        )
        val AdEstadoCivil = ArrayAdapter.createFromResource(
            this,
            R.array.EstadoCivil_array,
            android.R.layout.simple_spinner_item
        )
        val AdEscolaridad = ArrayAdapter.createFromResource(
            this,
            R.array.Escolaridad_array,
            android.R.layout.simple_spinner_item
        )

        AdGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AdEntidadFedNac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AdNacionalidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AdEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AdEscolaridad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spGenero.adapter = AdGenero
        spEntidadFedNac.adapter = AdEntidadFedNac
        spNacionalidad.adapter = AdNacionalidad
        spEstadoCivil.adapter = AdEstadoCivil
        spEscolaridad.adapter = AdEscolaridad
    }

    fun spinner_ItemSelected() {

    }

    fun BtnDelRefLab1_Click() {
        this.txtIdRefLab1.setText("0")
        this.txtRefLab1.setText("")
        this.BtnDelRefLab1.visibility = View.GONE
        this.BtnSrchRefLab1.visibility = View.VISIBLE
    }

    fun BtnDelRefLab2_Click() {
        this.txtIdRefLab2.setText("0")
        this.txtRefLab2.setText("")
        this.BtnDelRefLab2.visibility = View.GONE
        this.BtnSrchRefLab2.visibility = View.VISIBLE
    }

    fun BtnDelRefVec1_Click() {
        this.txtIdRefVec1.setText("0")
        this.txtRefVec1.setText("")
        this.BtnDelRefVec1.visibility = View.GONE
        this.BtnSrchRefVec1.visibility = View.VISIBLE
    }

    fun BtnDelRefVec2_Click() {
        this.txtIdRefVec2.setText("0")
        this.txtRefVec2.setText("")
        this.BtnDelRefVec2.visibility = View.GONE
        this.BtnSrchRefVec2.visibility = View.VISIBLE
    }

    fun BtnDelNegocio_Click() {
        this.txtIdNegocio.setText("0")
        this.txtNegocio.setText("")
        this.BtnDelNegocio.visibility = View.GONE
        this.BtnSrchNegocio.visibility = View.VISIBLE
    }

    fun BtnDelDomicilio_Click() {
        this.txtIdDomicilio.setText("0")
        this.txtIdClienteDireccion.setText("")
        this.txtDomicilio.setText("")
        this.BtnDelDomicilio.visibility = View.GONE
        this.BtnSrchDomicilio.visibility = View.VISIBLE
    }

    fun BtnSrchRefLab1_Click() {
        val intent = Intent(this, ClientesActivity::class.java)
        intent.putExtra("TypeSearch", REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.ordinal)
        this.startActivityForResult(intent, REQUEST_CODES.PICK_REFERENCIA_LABORAL_1.ordinal)
    }

    fun BtnSrchRefLab2_Click() {
        val intent = Intent(this, ClientesActivity::class.java)
        intent.putExtra("TypeSearch", REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.ordinal)
        this.startActivityForResult(intent, REQUEST_CODES.PICK_REFERENCIA_LABORAL_2.ordinal)
    }

    fun BtnSrchRefVec1_Click() {
        val intent = Intent(this, ClientesActivity::class.java)
        intent.putExtra("TypeSearch", REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.ordinal)
        this.startActivityForResult(intent, REQUEST_CODES.PICK_REFERENCIA_VECINAL_1.ordinal)
    }

    fun BtnSrchRefVec2_Click() {
        val intent = Intent(this, ClientesActivity::class.java)
        intent.putExtra("TypeSearch", REQUEST_CODES.SEARCH_CAT_CLIENTES_REF.ordinal)
        this.startActivityForResult(intent, REQUEST_CODES.PICK_REFERENCIA_VECINAL_2.ordinal)
    }

    fun BtnSrchNegocio_Click() {
        val intent = Intent(this, SearchNegocioActivity::class.java)
        intent.putExtra("TypeSearch", REQUEST_CODES.SEARCH_NEGOCIO_PICK.ordinal)
        this.startActivityForResult(intent, REQUEST_CODES.SEARCH_NEGOCIO_PICK.ordinal)
    }

    //        fun DateSelect_OnClick() {
//            /**
//             * Se instancia la fecha para obtener la fecha del dúa de hoy
//             */
//            val calendar = Calendar.getInstance()
//            val miliToday = calendar.timeInMillis
//
//            /**
//             * Se coloca la fecha mínica por default
//             * */
//            val fechaMin = calendar.set(1753, 1,1)
//
//            /**
//             * Se obtienes la fecha del display
//             */
//            val strFecha = _dateDisplay.text
//            var fecha: Date
//            val fechaCapt = calendar.setTime(strFecha as Date)
//
//            /**
//             * Se convierte a milisegundos la fecha del display
//             */
//            val sdf = SimpleDateFormat("dd/MM/yyyy")
//            val dateCreated: Date = sdf.parse(binding.etDate.text.toString())
//
//            val miliDisplay = (strFecha as Date).timeInMillis
//
//            fecha = calendar.time
//
//            if(fechaCapt == fechaMin){
//
//            }
//
//            dateDialog.updateDate()
//        }

    fun BtnSrchDomicilio_Click() {
        val intent = Intent(this, DireccionesSearchActivity::class.java)
        intent.putExtra("action", REQUEST_CODES.PICK_DOMICILIO.ordinal)
        this.startActivityForResult(intent, REQUEST_CODES.PICK_DOMICILIO.ordinal)
    }

    fun dateDialog_DateSet(e: DatePickerDialog) {
        _dateDisplay.text = e.datePicker.toString()
    }
    fun Validation(): Boolean {
        if (Salario.text.toString() == "")
        {
            Salario.setText("0")
        }
        if (OtrosIngresos.text.toString() == "")
        {
            OtrosIngresos.setText("0")
            cOtrosIngresos.setText("")
        }
        if (Renta.text.toString() == "")
        {
            Renta.setText("0")
        }
        if (OtrosGastos.text.toString() == "")
        {
            OtrosGastos.setText("0")
        }
        if (RetencionNomina.text.toString() == "")
        {
            RetencionNomina.setText("0")
        }

        if (Nombre1.text.toString() == "")
        {
            this.StrProblema = "Debe introducir al menos el Nombre 1."
        }

        if (_dateDisplay.text == "" && this.StrProblema == "")
        {
            this.StrProblema = "Debe introducir una Fecha de nacimiento"
        }

        if (spEntidadFedNac.selectedItem.toString() == "" && this.StrProblema == "")
        {
            this.StrProblema = "Debe introducir una Entidad Federativa"
        }

        if (spNacionalidad.selectedItem.toString() == "" && this.StrProblema == "")
        {
            this.StrProblema = "Debe introducir una Nacionalidad"
        }

        if (spEstadoCivil.selectedItem.toString() == "" && this.StrProblema == "")
        {
            this.StrProblema = "Debe introducir un Estado Civil"
        }

        if (spEstadoCivil.selectedItem.toString() == "" && this.StrProblema == "")
        {
            this.StrProblema = "Debe introducir un Estado Civil"
        }

        if (OtrosIngresos.text.toString() != "0" && cOtrosIngresos.text.toString() == "" && this.StrProblema == "")
        {
            this.StrProblema = "Debe introducir una descripcion para los Otros Ingresos"
        }

        if (this.StrProblema != "")
        {
            service.alertasError(this, this.StrProblema)
            this.StrProblema = ""
        }
        return this.StrProblema == ""
    }

    fun SaveCliente(){
        if (this.Validation()){
            val StrIMEI = AppSofomConfigs.getIMEI(this)
            OCapa = ClsCapaNegocios()

            val OClienteComplete = AppClienteComplete()
            OClienteComplete.CurpSolicitada = !this.muestraErrorCurp
            OClienteComplete.IdCliente = Idcliente
            OClienteComplete.Nombre1 = Nombre1.text.toString()
            OClienteComplete.Nombre2 = Nombre2.text.toString()
            OClienteComplete.ApellidoPat = ApellidoPat.text.toString()
            OClienteComplete.ApellidoMat = ApellidoMat.text.toString()
            OClienteComplete.RFC = RFC.text.toString()

            var sGen = "F"
            if (spGenero.selectedItem.toString().uppercase() == "MASCULINO") sGen = "M"

            OClienteComplete.Genero = sGen

            val fecha = _dateDisplay.text as Date

            if (fecha != Date(Long.MIN_VALUE)){
                OClienteComplete.DteNacimiento = fecha
            }
            OClienteComplete.IdEntidadFederativa = Recursos.GetIDCombo(this, spEntidadFedNac)
            OClienteComplete.IdNacionalidad = Recursos.GetIDCombo(this, spNacionalidad)
            OClienteComplete.Curp = Curp.text.toString()
            OClienteComplete.IdEstadoCivil = Recursos.GetIDCombo(this, spEstadoCivil)
            OClienteComplete.IdEscolaridad = Recursos.GetIDCombo(this, spEscolaridad)
            OClienteComplete.Salario = Salario.text.toString().toDouble()
            OClienteComplete.OtrosIngresos = OtrosIngresos.text.toString().toDouble()
            OClienteComplete.cOtrosIngresos = cOtrosIngresos.text.toString()
            OClienteComplete.GastoRenta = Renta.text.toString().toDouble()
            OClienteComplete.OtrosGastos = OtrosGastos.text.toString().toDouble()
            OClienteComplete.GastoNomina = RetencionNomina.text.toString().toDouble()

            OClienteComplete.IdRefLab1 = txtRefLab1.toString().toInt()
            OClienteComplete.IdRefLab2 = txtIdRefLab2.toString().toInt()
            OClienteComplete.IdRefVec1 = txtIdRefVec1.toString().toInt()
            OClienteComplete.IdRefVec2 = txtIdRefVec2.toString().toInt()
            OClienteComplete.IdNegocio = txtIdNegocio.toString().toInt()
            OClienteComplete.IdDomicilio = txtIdDomicilio.toString().toInt()
            OClienteComplete.IdClienteDireccion = txtIdClienteDireccion.toString().toInt()

            OClienteComplete.Exitoso = true

            if (!OCapa.getXMLSaveClienteComplete(OClienteComplete)){
                service.alertasError(this).apply {
                    setMessage(OCapa.StrProblema + ". Intente guardar de nuevo.")
                    setPositiveButton("Aceptar") { dialog, which ->
                    }
                }
            }
            else{
                service.Url = AppSofomConfigs.URLWSFull
                if (service.callApi(MetodosApp.MultiWebMethodsApp, arrayOf(AppSofomConfigs.NameEmpresa, ClsCapaNegocios.ClaseNegocios, Metodos.SAVECLIENTE, OCapa.StrXMLReturn, UserApp.StrUser, UserApp.StrPass, StrIMEI))){
                    serviceSaveCliente_MultiWebMethodsAppCompleted()
                }
                else{
                    service.alertasError(this, service.StrProblema)
                }
            }
        }
    }

    private fun serviceSaveCliente_MultiWebMethodsAppCompleted() {
        try {
            val ORespuesta = DeserializeXML<AppClienteComplete>(service.StrResult)
            if (ORespuesta.Exitoso){
                this.muestraErrorCurp = true
                this.Idcliente = ORespuesta.IdCliente
                this.Nombre1.setText(ORespuesta.Nombre1)
                this.Nombre2.setText(ORespuesta.Nombre2)
                this.ApellidoPat.setText(ORespuesta.ApellidoPat)
                this.ApellidoMat.setText(ORespuesta.ApellidoMat)
                Recursos.GetPositionCombo(this, spGenero, ORespuesta.Genero)
                Recursos.GetPositionCombo(this, spEntidadFedNac, ORespuesta.IdEntidadFederativa.toString())
                Recursos.GetPositionCombo(this, spNacionalidad, ORespuesta.IdNacionalidad.toString())
                Recursos.GetPositionCombo(this, spEstadoCivil, ORespuesta.IdEstadoCivil.toString())
                Recursos.GetPositionCombo(this, spEscolaridad, ORespuesta.IdEscolaridad.toString())

                this._dateDisplay.setText(ORespuesta.DteNacimiento.toString())
                this.Curp.setText(ORespuesta.Curp)
                this.Salario.setText(ORespuesta.Salario.toString())
                this.OtrosIngresos.setText(ORespuesta.OtrosIngresos.toString())
                this.RetencionNomina.setText(ORespuesta.GastoNomina.toString())
                this.Renta.setText(ORespuesta.GastoRenta.toString())
                this.OtrosGastos.setText(ORespuesta.OtrosGastos.toString())

                this.txtIdRefLab1.setText(ORespuesta.IdRefLab1.toString())
                this.txtIdRefLab2.setText(ORespuesta.IdRefLab2.toString())
                this.txtIdRefVec1.setText(ORespuesta.IdRefVec1.toString())
                this.txtIdRefVec2.setText(ORespuesta.IdRefVec2.toString())

                service.alertasSuccess(this, "Cliente Guardado Correctamente.")
            }
            else{
                if (ORespuesta.Error.contains("El CURP es un dato NO OBLIGATORIO"))
                {
                    this.muestraErrorCurp = false
                }
                if (ORespuesta.Error.indexOf("|@FALTA_APLICACION_CAPITAL@|") >= 0)
                {
                    //nTipoMensajeAdelanto = 1;
                }
                this.StrProblema = ORespuesta.Error
            }
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
    }

    private fun GetCliente(){
        val StrIMEI = AppSofomConfigs.getIMEI(this)
        OCapa = ClsCapaNegocios()
        if (!OCapa.getXMLGetClienteComplete(this.Idcliente)){
            service.alertasError(this).apply {
                setMessage(OCapa.StrProblema + ". Intente guardar de nuevo.")
                setPositiveButton("Aceptar") { dialog, which ->
                }
            }
        }
        else{
            service.Url = AppSofomConfigs.URLWSFull
            if (service.callApi(MetodosApp.MultiWebMethodsApp, arrayOf(AppSofomConfigs.NameEmpresa, ClsCapaNegocios.ClaseNegocios, Metodos.GETCLIENTE, OCapa.StrXMLReturn, UserApp.StrUser, UserApp.StrPass, StrIMEI))){
                serviceGetCliente_MultiWebMethodsAppCompleted()
            }
            else{
                service.alertasError(this, service.StrProblema)
            }
        }
    }

    private fun serviceGetCliente_MultiWebMethodsAppCompleted() {
        try {
            val ORespuesta = DeserializeXML<AppClienteComplete>(service.StrResult)
            if (ORespuesta.Exitoso){
                this.Idcliente = ORespuesta.IdCliente
                this.Nombre1.setText(ORespuesta.Nombre1)
                this.Nombre2.setText(ORespuesta.Nombre2)
                this.ApellidoPat.setText(ORespuesta.ApellidoPat)
                this.ApellidoMat.setText(ORespuesta.ApellidoMat)
                //FALTO AGREGAR EL RFC OBTENIDO DEL CLIENTE
                this.RFC.setText(ORespuesta.RFC)
                Recursos.GetPositionCombo(this, spGenero, ORespuesta.Genero);
                Recursos.GetPositionCombo(this, spEntidadFedNac, ORespuesta.IdEntidadFederativa.toString())
                Recursos.GetPositionCombo(this, spNacionalidad, ORespuesta.IdNacionalidad.toString())
                Recursos.GetPositionCombo(this, spEstadoCivil, ORespuesta.IdEstadoCivil.toString())
                Recursos.GetPositionCombo(this, spEscolaridad, ORespuesta.IdEscolaridad.toString())

                this._dateDisplay.text = ORespuesta.DteNacimiento.toString()
                this.Curp.setText(ORespuesta.Curp)
                this.Salario.setText(ORespuesta.Salario.toString())
                this.OtrosIngresos.setText(ORespuesta.OtrosIngresos.toString())
                this.cOtrosIngresos.setText(ORespuesta.cOtrosIngresos)
                this.RetencionNomina.setText(ORespuesta.GastoNomina.toString())
                this.Renta.setText(ORespuesta.GastoRenta.toString())
                this.OtrosGastos.setText(ORespuesta.OtrosGastos.toString())

                this.txtIdRefLab1.setText(ORespuesta.IdRefLab1.toString())
                this.txtIdRefLab2.setText(ORespuesta.IdRefLab2.toString())
                this.txtIdRefVec1.setText(ORespuesta.IdRefVec1.toString())
                this.txtIdRefVec2.setText(ORespuesta.IdRefVec2.toString())

                this.txtRefLab1.setText(ORespuesta.RefLab1)
                this.txtRefLab2.setText(ORespuesta.RefLab2)
                this.txtRefVec1.setText(ORespuesta.RefVec1)
                this.txtRefVec2.setText(ORespuesta.RefVec2)

                this.txtIdNegocio.setText(ORespuesta.IdNegocio.toString())
                this.txtNegocio.setText(ORespuesta.Negocio)
                this.txtIdDomicilio.setText(ORespuesta.IdDomicilio.toString())
                this.txtDomicilio.setText(ORespuesta.Domicilio)
                this.txtIdClienteDireccion.setText(ORespuesta.IdClienteDireccion.toString())

                BotonesVistas()
            }
            else{
                this.StrProblema = ORespuesta.Error
            }
        }
        catch (ex: Exception){
            this.StrProblema = ex.message.toString()
        }
        if (this.StrProblema != ""){
            service.alertasError(this, this.StrProblema)
            this.StrProblema = ""
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_clientes, menu)
        IMenuAdd = menu.findItem(R.id.menu_save)
        IMenuTels = menu.findItem(R.id.menu_telefonos)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> {
                progress.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    this.SaveCliente()
                }, 1000)
            }
            R.id.menu_telefonos -> {
                if (this.Idcliente == 0){
                    service.alertasError(this, "Debe guardar el Cliente antes de guardar un telefono.")
                }
                else{
                    val intent = Intent(this,TelefonosActivity::class.java)
                    intent.putExtra("TypeSearch", REQUEST_CODES.TELEFONOS_CLIENTE.ordinal)
                    intent.putExtra("IdCliente", Idcliente)
                    resultLauncher2.launch(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    var resultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        var TypeSearchResult = 0
        var IdClienteResult = 0
        var NombreClienteResult = ""
        if (result.resultCode == Activity.RESULT_OK) {
            when (result.resultCode){
                REQUEST_CODES.PICK_REFERENCIA_LABORAL_1.ordinal -> {
                    TypeSearchResult = result.data?.extras?.getInt("TypeSearchResult") ?: 0
                    IdClienteResult = result.data?.extras?.getInt("IdClienteResult") ?: 0
                    NombreClienteResult = result.data?.extras?.getString("NombreClienteResult") ?: ""

                    if (IdClienteResult != this.Idcliente)
                    {
                        this.BtnSrchRefLab1.visibility = View.GONE
                        this.BtnDelRefLab1.visibility = View.VISIBLE
                        this.txtIdRefLab1.setText(IdClienteResult.toString())
                        this.txtRefLab1.setText(NombreClienteResult)
                    }
                    else
                    {
                        this.StrProblema = "No puede seleccionar al mismo cliente como Referencia, favor de seleccionar otro!."
                    }
                }
                REQUEST_CODES.PICK_REFERENCIA_LABORAL_2.ordinal -> {
                    TypeSearchResult = result.data?.extras?.getInt("TypeSearchResult") ?: 0
                    IdClienteResult = result.data?.extras?.getInt("IdClienteResult") ?: 0
                    NombreClienteResult = result.data?.extras?.getString("NombreClienteResult") ?: ""

                    if (IdClienteResult != this.Idcliente)
                    {
                        this.BtnSrchRefLab2.visibility = View.GONE
                        this.BtnDelRefLab2.visibility = View.VISIBLE
                        this.txtIdRefLab2.setText(IdClienteResult.toString())
                        this.txtRefLab2.setText(NombreClienteResult)
                    }
                    else
                    {
                        this.StrProblema = "No puede seleccionar al mismo cliente como Referencia, favor de seleccionar otro!."
                    }
                }
                REQUEST_CODES.PICK_REFERENCIA_VECINAL_1.ordinal -> {
                    TypeSearchResult = result.data?.extras?.getInt("TypeSearchResult") ?: 0
                    IdClienteResult = result.data?.extras?.getInt("IdClienteResult") ?: 0
                    NombreClienteResult = result.data?.extras?.getString("NombreClienteResult") ?: ""

                    if (IdClienteResult != this.Idcliente)
                    {
                        this.BtnSrchRefVec1.visibility = View.GONE
                        this.BtnDelRefVec1.visibility = View.VISIBLE
                        this.txtIdRefVec1.setText(IdClienteResult.toString())
                        this.txtRefVec1.setText(NombreClienteResult)
                    }
                    else
                    {
                        this.StrProblema = "No puede seleccionar al mismo cliente como Referencia, favor de seleccionar otro!."
                    }
                }
            }
        }
    }
}