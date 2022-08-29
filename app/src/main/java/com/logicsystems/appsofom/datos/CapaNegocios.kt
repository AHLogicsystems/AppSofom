package com.logicsystems.appsofom

import android.app.Application
import com.logicsystems.appsofom.Adapters.IBasicListElement
import com.logicsystems.appsofom.datos.EdosCorte
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.io.StringWriter
import java.util.*


open class ClsCapaNegocios {
    var StrXMLReturn = ""
    var StrProblema = ""
    companion object{
        val ClaseNegocios = "AppSofom"
    }
    //region getXML
    fun getXMLSearch(StrFolio: String, StrCliente: String, IntTypeSearch: Int): Boolean {
        try {
            val OSearch = AppSofomSearch()
            OSearch.Folio = StrFolio
            OSearch.Cliente = StrCliente
            OSearch.TypeSearch = IntTypeSearch
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    fun getXMLSetUbicacionesGPS(GeoLocalizacion: String, DteUbicacion: Date): Boolean {
        try {
            val OSearch = UbicacionesGPS()
            val oUbicacion = UbicacionGPS()
            oUbicacion.GeoLocalizacion = GeoLocalizacion
            oUbicacion.DteUbicacion = DteUbicacion
            OSearch.Ubicacion.add(oUbicacion)
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    fun getXMLSetUbicacionesGPS(Ubicaciones: Array<UbicacionGPS>): Boolean{
        try {
            val OSearch = UbicacionesGPS()
            for (oEach: UbicacionGPS in Ubicaciones){
                OSearch.Ubicacion.add(oEach)
            }
            this.StrXMLReturn = SerializeXML(OSearch)
        }
        catch (ex: Exception)
        {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLClientes(Nombres: String, Apellidos: String, RFC: String): Boolean {
        try {
            val OSearch = BuscarCliente()
            val CLApp = ClienteApp()
            OSearch.Nombres = Nombres
            OSearch.Apellidos = Apellidos
            OSearch.RFC = RFC
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLGetClienteComplete(IdCliente: Int): Boolean {
        try {
            val OSearch = AppSofomInfoCliente()
            OSearch.IdCliente = IdCliente
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLGetGrupoSolidarioComplete(IdGrupoSolidario: Int): Boolean {
        try {
            val OSearch = AppSofomInfoGrupoSolidario()
            OSearch.IdGrupoSolidario = IdGrupoSolidario
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLGetGrupoSolidarioSearch(nombre: String): Boolean {
        try {
            val OSearch = AppSofomInfoGrupoSolidario()
            OSearch.cNombreGrupo = nombre
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLGrupoSolidarioComplete(grupo: AppGrupoSolidario): Boolean {
        try {
            this.StrXMLReturn = SerializeXML(grupo)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLTelefono(IdCliente: Int, nTipoCliente: Int): Boolean {
        try {
            val OSearch = AppSofomInfoTelefono()
            OSearch.IdTel = 0
            OSearch.IdCliente = IdCliente
            OSearch.nTipoCliente = nTipoCliente
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSaveTelefono(IdCliente: Int, nTipoCliente: Int, IdTipoTel: Int, cTel: String): Boolean {
        try {
            val OSearch = AppClienteTelefono()
            OSearch.IdTel = 0 //NUEVOS!
            OSearch.nTipoCliente = nTipoCliente
            OSearch.IdCliente = IdCliente
            OSearch.IdTipoTel = IdTipoTel
            OSearch.cTel = cTel
            OSearch.Exitoso = true
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLDeleteTelefono(IdTelefono: Int, nTipoCliente: Int, IdCliente: Int): Boolean {
        try {
            val OSearch = AppSofomInfoTelefono()
            //ClienteApp CLApp = new ClienteApp();
            OSearch.IdTel = IdTelefono
            OSearch.nTipoCliente = nTipoCliente
            OSearch.IdCliente = IdCliente
            OSearch.Exitoso = true
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSaveClienteSimple(oCliente: AppClienteSimple): Boolean {
        try {
            this.StrXMLReturn = SerializeXML(oCliente)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSaveClienteComplete(oCliente: AppClienteComplete): Boolean {
        try {
            this.StrXMLReturn = SerializeXML(oCliente)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLEntregarPrestamo(IdPrestamo: Int, nMonto: Double): Boolean {
        return getXMLEntregarPrestamo(IdPrestamo, nMonto, Date(Long.MIN_VALUE), 0)
    }

    open fun getXMLEntregarPrestamo(IdPrestamo: Int, nMonto: Double, DteEntrega: Date, IdDispAPP: Int): Boolean {
        try {
            val OSearch = GetEntregaPrestamo()
            OSearch.IdPrestamo = IdPrestamo
            OSearch.MontoEntrega = nMonto
            OSearch.FechaEntrega = DteEntrega
            OSearch.IdDispAPP = IdDispAPP
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLInfoCredito(IntIdPrestamo: Int): Boolean {
        try {
            val OInfo = AppSofomInfoCred()
            OInfo.IdPrestamo = IntIdPrestamo
            this.StrXMLReturn = SerializeXML(OInfo)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSolRenovar(IntIdPrestamo: Int, IntPlazo: Int, DblMonto: Double, IdTipoPagoPrestamo: Int, DteVencimiento: Date): Boolean {
        try {
            val OInfo = AppSofomSolRenovar()
            OInfo.IdPrestamo = IntIdPrestamo
            OInfo.Plazo = IntPlazo
            OInfo.Monto = DblMonto
            OInfo.IdTipoPagoPrestamo = IdTipoPagoPrestamo
            OInfo.DteVencimiento = DteVencimiento
            this.StrXMLReturn = SerializeXML(OInfo)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSolReestructurar(IntIdPrestamo: Int, DblMontoCA: Double, DteVencimiento: Date): Boolean {
        try {
            val OInfo = AppSofomSolReestructurar()
            OInfo.IdPrestamo = IntIdPrestamo
            OInfo.MontoAdicional = DblMontoCA
            OInfo.DteVencimiento = DteVencimiento
            this.StrXMLReturn = SerializeXML(OInfo)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLPago(oPago: AppSofomPay): Boolean {
        try {
            this.StrXMLReturn = SerializeXML(oPago)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLPago(IdPrestamo: Int, nPago: Double, IdMedioPago: Int, cNumCheque: String, nTipoEmisor: Int, cEmisor: String, nTipoAdelanto: Int): Boolean {
        return getXMLPago(IdPrestamo, nPago, IdMedioPago, cNumCheque, nTipoEmisor, cEmisor, nTipoAdelanto, Date(Long.MIN_VALUE), 0)
    }

    open fun getXMLPago(IdPrestamo: Int, nPago: Double, IdMedioPago: Int, cNumCheque: String, nTipoEmisor: Int, cEmisor: String, nTipoAdelanto: Int, DtePago: Date, IdPagoAPP: Int): Boolean {
        val OPago = AppSofomPay()
        OPago.IdPrestamo = IdPrestamo
        OPago.Pago = nPago
        OPago.IdMedioPago = IdMedioPago
        OPago.NumeroCheque = cNumCheque
        OPago.TipoEmisor = nTipoEmisor as Boolean
        OPago.Emisor = cEmisor
        OPago.TipoAdelanto = nTipoAdelanto
        OPago.FechaPago = DtePago
        OPago.IdPagoAPP = IdPagoAPP
        return getXMLPago(OPago)
    }

    open fun getXMLTicketPago(IdPrestamo: Int, IdPagoPrestamo: Int): Boolean {
        try {
            val OTicket = SolicitudTicketPP()
            OTicket.IdPrestamo = IdPrestamo
            OTicket.IdPagoPrestamo = IdPagoPrestamo
            this.StrXMLReturn = SerializeXML(OTicket)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLReportes(IdPrestamo: Int, IdDetalle: Int): Boolean {
        try {
            val OPago = PeticionReportes()
            OPago.IdReferencia = IdPrestamo
            OPago.IdDetalle = IdDetalle
            this.StrXMLReturn = SerializeXML(OPago)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLCorteCaja(_IdCorteCaja: Int, _nSaldoDeclarado: Double): Boolean {
        try {
            val OCierre = CerrarCaja()
            OCierre.IdCorteCaja = _IdCorteCaja
            OCierre.SaldoDeclarado = _nSaldoDeclarado
            this.StrXMLReturn = SerializeXML(OCierre)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLPlantilla(cNombrePlantilla: String, cDescripcion: String): Boolean {
        try {
            val OPlantilla = GetPlantilla()
            OPlantilla.Nombre = cNombrePlantilla
            OPlantilla.Descripcion = cDescripcion
            this.StrXMLReturn = SerializeXML(OPlantilla)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLZonas(cZona: String): Boolean {
        try {
            val OZona = GetZonas()
            OZona.Nombre = cZona
            this.StrXMLReturn = SerializeXML(OZona)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSolicitud(IdPrestamo: Int, IdPlantillaCred: Int, nMontoTotal: Double, IdTipoPlazo: Int, nPlazo: Int, IdZona: Int, IdCliente: Int, IdGrupoSol: Int, DteVencimiento: Date, IdTipoPagoPrestamo: Int): Boolean {
        try {
            val OSolicitud = SolicitudCredApp()
            OSolicitud.IdPrestamo = IdPrestamo
            OSolicitud.IdPlantillaCred = IdPlantillaCred
            OSolicitud.MontoTotal = nMontoTotal
            OSolicitud.IdTipoPlazo = IdTipoPlazo
            OSolicitud.Plazo = nPlazo
            OSolicitud.IdZona = IdZona
            OSolicitud.IdCliente = IdCliente
            OSolicitud.IdGrupoSolidario = IdGrupoSol
            OSolicitud.DteVencimiento = DteVencimiento
            OSolicitud.IdTipoPagoPrestamo = IdTipoPagoPrestamo
            this.StrXMLReturn = SerializeXML(OSolicitud)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLInfoNegocio(IdNegocio: Int): Boolean {
        try {
            val OInfo = AppEmpresaBusqueda()
            OInfo.IdEmpresa = IdNegocio
            this.StrXMLReturn = SerializeXML(OInfo)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLNegocio(IdNegocio: Int, NombreNegocio: String, AntiguedadNeg: Int, NumEmpleados: Int, Ingreso: Double, Egreso: Double, IdGiro: Int, IdDomNegocio: Int, IdSituacionDom: Int, AntiguedadDom: Int, IdHLunes: Int, IdHMartes: Int, IdHMiercoles: Int, IdHJueves: Int, IdHViernes: Int, IdHSabado: Int, IdHDomingo: Int): Boolean{
        try {
            val OSolicitud = NegocioSave()
            OSolicitud.IdNegocio = IdNegocio
            OSolicitud.NombreNegocio = NombreNegocio
            OSolicitud.AnguedadNegocio = AntiguedadNeg
            OSolicitud.NumeroEmpleados = NumEmpleados
            OSolicitud.Ingreso = Ingreso
            OSolicitud.Egreso = Egreso
            OSolicitud.IdGiroNegocio = IdGiro
            OSolicitud.IdDomicilio = IdDomNegocio
            OSolicitud.IdSituacionDom = IdSituacionDom
            OSolicitud.AntiguedadDom = AntiguedadDom
            OSolicitud.IdHorarioLU = IdHLunes
            OSolicitud.IdHorarioMA = IdHMartes
            OSolicitud.IdHorarioMI = IdHMiercoles
            OSolicitud.IdHorarioJU = IdHJueves
            OSolicitud.IdHorarioVI = IdHViernes
            OSolicitud.IdHorarioSA = IdHSabado
            OSolicitud.IdHorarioDO = IdHDomingo
            this.StrXMLReturn = SerializeXML(OSolicitud)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLNegocios(Nombre: String): Boolean {
        try {
            val OSearch = GetEmpresa()
            OSearch.Nombre = Nombre
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLHorariosNeg(Nombre: String): Boolean {
        try {
            val OSearch = GetHorario()
            OSearch.Horario = Nombre
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLGirosNeg(Nombre: String): Boolean{
        try {
            val OSearch = GetGiro()
            OSearch.Nombre = Nombre
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSolRepCorteC(IdCorteCaja: Int): Boolean {
        try {
            val OReporte = SolicitudReporteCorteC()
            OReporte.IdCorteCaja = IdCorteCaja
            this.StrXMLReturn = SerializeXML(OReporte)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLOperadores(Nombres: String, Apellidos: String): Boolean {
        try {
            val OSearch = BuscarOperador()
//            val OperApp = OperadorApp()
            OSearch.Nombres = Nombres
            OSearch.Apellidos = Apellidos
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLPermiso(StrPermiso: String): Boolean {
        try {
            val OSearch = BuscarPermisoOperador()
//            val OperApp = OperadorApp()
            OSearch.Permiso = StrPermiso
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSinPagoCapital(IdOperador: Int, MinDias: Int): Boolean {
        try {
            val OSearch = GetSinPagoCapital()
            OSearch.IdOperadorCobranza = IdOperador
            OSearch.nDias = MinDias
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLCarteraOperador(IdOperador: Int, DteFechaIni: Date, DteFechaFin: Date): Boolean {
        try {
            val OSearch = GetCarteraOperador()
            OSearch.IdOperador = IdOperador
            OSearch.DteFechaIni = DteFechaIni
            OSearch.DteFechaFin = DteFechaFin
            this.StrXMLReturn = SerializeXML(OSearch)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun getXMLSaveCobroAccesorios(OCobranza: RespuestaAccesorios): Boolean {
        try {
            this.StrXMLReturn = SerializeXML(OCobranza)
        } catch (ex: Exception) {
            this.StrProblema = "Error: " + ex.message
        }
        return this.StrProblema == ""
    }

    open fun SerializeXML(OXML: Any): String{
        val serializer: Serializer = Persister()
        val writer = StringWriter()
        serializer.write(OXML, writer)
        val StrXMLRet = writer.buffer.toString()
        return StrXMLRet
    }
    //endregion

    fun FindMedioPago(StrMedioPago: String): Int {
        return when (StrMedioPago) {
            "Efectivo" -> 0
            "Referenciado" -> 1
            "Transferencia" -> 2
            "Cheque" -> 4
            "Deposito" -> 5
            "Por Pagar" -> 6
            "Nota De Crédito" -> 7
            "Deposito En Efectivo" -> 8
            "Bonificacion" -> 9
            else -> {
                -1
            }
        }
    }

    fun FindTipoAdelanto(StrTipoAdelanto: String): Int{
        return when (StrTipoAdelanto) {
            "Reducir Plazo" -> 1
            "Reducir Pagare" -> 2
            "Siguiente Letra" -> 3
            "Reducir Intereses" -> 4
            "Abono a Garantia" -> 5
            else -> {
                -1
            }
        }
    }
}

@Root(name = "AppListaEmpresa")
open class AppListaEmpresa
{
    @field:ElementList(name="Empresas")
    var Empresas = arrayListOf<AppEmpresa>()
}
@Root(name = "AppEmpresa")
open class AppEmpresa
{
    @field:Element(name = "Empresa")
    var Empresa = ""
}

@Root(name = "AppRespuesta")
open class AppRespuesta {
    @field:Element(name = "Exitoso")
    var Exitoso = false

    @field:Element(name = "Error", required = false)
    var Error = ""

    @field:Element(name = "Metodo", required = false)
    var Metodo = ""
}

@Root(name = "AppRespuestaLogIn")
open class AppRespuestaLogIn : AppRespuesta()
{
    @field:Element(name = "NickName")
    var NickName = ""

    @field:Element(name = "InfoTicket")
    var InfoTicket = ""
}

@Root(name = "AppSofomSearch")
open class AppSofomSearch {
    @field:Element(name = "Folio")
    var Folio = ""

    @field:Element(name = "Cliente")
    var Cliente = ""

    @field:Element(name = "TypeSearch")
    var TypeSearch = 0
}

@Root(name = "AppSearchRespuesta")
open class AppSearchRespuesta : AppRespuesta()
{
    @field:ElementList(name="Creditos")
    var Creditos = arrayListOf<AppCredito>()
}

@Root(name = "AppCredito")
open class AppCredito : IBasicListElement {
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "IdClienteMoral")
    var IdClienteMoral = 0

    @field:Element(name = "IdGrupoSolidario")
    var IdGrupoSolidario = 0

    @field:Element(name = "Folio")
    var Folio = ""

    @field:Element(name = "Cliente")
    var Cliente = ""

    @field:Element(name = "Monto")
    var Monto = 0.0

    @field:Element(name = "Estado", required = false)
    var Estado = ""

    @field:Element(name = "Solicitud")
    var Solicitud = ""

//    protected var DteSolicitud = SimpleDateFormat("yyyy-MM-dd'T'HH: mm: ssZ").parse(this.Solicitud) as Date

    @field:Element(name = "RFC", required = false)
    var RFC = ""

    @field:Element(name = "Direccion", required = false)
    var Direccion = ""

    @field:Element(name = "nEstadoRegistro", required = false)
    var nEstadoRegistro = 0

    @field:Element(name = "GeoLocalizacion", required = false)
    var GeoLocalizacion = ""

    @field:Element(name = "Color", required = false)
    var Color = ""

    override fun getId(): Int {
        return IdPrestamo;
    }

    override fun getDescription(): String {
        return Folio;
    }

    override fun getDetailDescription(): String {
        return Cliente;
    }

    override fun getDetailDescription2(): String {
        return Direccion;
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdPrestamo = Id
        this.Folio = description
        this.Cliente = detailDescription
    }
}

@Root(name = "AppSofomPay")
open class AppSofomPay {
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "Pago")
    var Pago = 0.0

    @field:Element(name = "IdMedioPago")
    var IdMedioPago = 0

    @field:Element(name = "NumeroCheque")
    var NumeroCheque = ""

    @field:Element(name = "TipoEmisor")
    var TipoEmisor = false

    @field:Element(name = "Emisor")
    var Emisor = ""

    @field:Element(name = "TipoAdelanto")
    var TipoAdelanto = 0

    @field:Element(name = "FechaPago")
    var FechaPago = Date(Long.MIN_VALUE)

    @field:Element(name = "IdPagoAPP")
    var IdPagoAPP = 0

    @field:ElementList(name="Integrantes")
    var Integrantes = arrayListOf<AppClienteGrupo>()
}

@Root(name = "AppCobranzaRespuesta")
open class AppCobranzaRespuesta : AppRespuesta()
{
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "Folio")
    var Folio = ""

    @field:Element(name = "Cliente")
    var Cliente = ""

    @field:Element(name = "Pendiente")
    var Pendiente = 0.0

    @field:Element(name = "AlDia")
    var AlDia = 0.0

    @field:Element(name = "Liquidar")
    var Liquidar = 0.0

    @field:Element(name = "IdPagoAPP")
    var IdPagoAPP = 0


    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "IdClienteMoral")
    var IdClienteMoral = 0

    @field:Element(name = "IdGrupoSolidario")
    var IdGrupoSolidario = 0


    @field:ElementList(name="Integrantes")
    var Integrantes = arrayListOf<AppClienteGrupo>()
}

@Root(name = "PeticionReportes")
open class PeticionReportes {
    @field:Element(name = "IdReferencia")
    var IdReferencia = 0

    @field:Element(name = "IdDetalle")
    var IdDetalle = 0
}

@Root(name = "HistorialPagos")
open class HistorialPagos : AppRespuesta()
{
    @field:ElementList(name="Pagos")
    var Pagos = arrayListOf<GeneralPagos>()
}

@Root(name = "GeneralPagos")
open class GeneralPagos : AppRespuesta()
{
    @field:Element(name = "IdPagoPrestamo")
    var IdPagoPrestamo = 0

    @field:Element(name = "Operador")
    var Operador = ""

    @field:Element(name = "FechaPago")
    var FechaPago = Date(Long.MIN_VALUE)

    @field:Element(name = "PagoTotal")
    var PagoTotal = 0.0
}

@Root(name = "DetallePagos")
open class DetallePagos : GeneralPagos()
{
    @field:Element(name = "Capital")
    var Capital = 0.0

    @field:Element(name = "Interes")
    var Interes = 0.0

    @field:Element(name = "InteresIVA")
    var InteresIVA = 0.0

    @field:Element(name = "Mora")
    var Mora = 0.0

    @field:Element(name = "MoraIVA")
    var MoraIVA = 0.0

    @field:Element(name = "Multa")
    var Multa = 0.0

    @field:Element(name = "MultaIVA")
    var MultaIVA = 0.0

    @field:Element(name = "LblCuotaFija")
    var LblCuotaFija = ""

    @field:Element(name = "CuotaFija")
    var CuotaFija = 0.0

    @field:Element(name = "CuotaFijaIVA")
    var CuotaFijaIVA = 0.0

    @field:Element(name = "LblCuotaFija1")
    var LblCuotaFija1 = ""

    @field:Element(name = "CuotaFija1")
    var CuotaFija1 = 0.0

    @field:Element(name = "CuotaFija1IVA")
    var CuotaFija1IVA = 0.0

    @field:Element(name = "LblCuotaFija2")
    var LblCuotaFija2 = ""

    @field:Element(name = "CuotaFija2")
    var CuotaFija2 = 0.0

    @field:Element(name = "CuotaFija2IVA")
    var CuotaFija2IVA = 0.0

    @field:Element(name = "Gastos")
    var Gastos = 0.0

    @field:Element(name = "Adelanto")
    var Adelanto = 0.0

    @field:Element(name = "MedioPago")
    var MedioPago = ""

    @field:Element(name = "Caja")
    var Caja = ""
}

@Root(name = "HistorialCredito")
class HistorialCredito : AppRespuesta()
{
    @field:ElementList(name="GeneralHistorico")
    var GeneralHistorico = arrayListOf<GeneralHistorialCredito>()
}

@Root(name = "GeneralHistorialCredito")
open class GeneralHistorialCredito : AppRespuesta()
{
    @field:Element(name = "IdCF")
    var IdCF = 0

    @field:Element(name = "NumPago")
    var NumPago = 0

    @field:Element(name = "FechaVencimiento")
    var FechaVencimiento = Date(Long.MIN_VALUE)

    @field:Element(name = "TotalPagare")
    var TotalPagare = 0.0

    @field:Element(name = "SaldoPagare")
    var SaldoPagare = 0.0
}

@Root(name = "DetalleHistorialCredito")
open class DetalleHistorialCredito : GeneralHistorialCredito()
{
    @field:Element(name = "FechaSaldado")
    var FechaSaldado = Date(Long.MIN_VALUE)

    @field:Element(name = "Capital")
    var Capital = 0.0

    @field:Element(name = "SaldoCapital")
    var SaldoCapital = 0.0

    @field:Element(name = "Interes")
    var Interes = 0.0

    @field:Element(name = "SaldoInteres")
    var SaldoInteres = 0.0

    @field:Element(name = "Atraso")
    var Atraso = 0

    @field:Element(name = "Multa")
    var Multa = 0.0

    @field:Element(name = "SaldoMulta")
    var SaldoMulta = 0.0

    @field:Element(name = "Mora")
    var Mora = 0.0

    @field:Element(name = "MoraPagada")
    var MoraPagada = 0.0

    @field:Element(name = "MoraCondonada")
    var MoraCondonada = 0.0

    @field:Element(name = "ExistCuotaFija")
    var ExistCuotaFija = false

    @field:Element(name = "LblCuotaFija")
    var LblCuotaFija = ""

    @field:Element(name = "CuotaFija")
    var CuotaFija = 0.0

    @field:Element(name = "SaldoCuotaFija")
    var SaldoCuotaFija = 0.0

    @field:Element(name = "ExistCuotaFija1")
    var ExistCuotaFija1 = false

    @field:Element(name = "LblCuotaFija1")
    var LblCuotaFija1 = ""

    @field:Element(name = "CuotaFija1")
    var CuotaFija1 = 0.0

    @field:Element(name = "SaldoCuotaFija1")
    var SaldoCuotaFija1 = 0.0

    @field:Element(name = "ExistCuotaFija2")
    var ExistCuotaFija2 = false

    @field:Element(name = "LblCuotaFija2")
    var LblCuotaFija2 = ""

    @field:Element(name = "CuotaFija2")
    var CuotaFija2 = 0.0

    @field:Element(name = "SaldoCuotaFija2")
    var SaldoCuotaFija2 = 0.0
}

@Root(name = "CorteCajaRespuesta")
open class CorteCajaRespuesta : AppRespuesta()
{
    @field:Element(name = "IdCorteCaja")
    var IdCorteCaja = 0

    @field:Element(name = "Caja")
    var Caja = ""

    @field:Element(name = "Operador")
    var Operador = ""

    @field:Element(name = "PermitirOper")
    var PermitirOper = false

    @field:Element(name = "EstadoCorte")
    var EstadoCorte = 0

    @field:Element(name = "EdoCierre")
    var EdoCierre = 0

    @field:Element(name = "SaldoInicial")
    var SaldoInicial = 0.0

    @field:Element(name = "SaldoFinal")
    var SaldoFinal = 0.0

    @field:Element(name = "SaldoDeclarado")
    var SaldoDeclarado = 0.0

    fun NameEstadoCorte(): String {
        val Name = when (EstadoCorte) {
            EdosCorte.Abierto.ordinal -> "Abierto"
            EdosCorte.Cerrado.ordinal -> "Cerrado"
            EdosCorte.Cancelado.ordinal -> "Cancelado"
            EdosCorte.Revisado.ordinal -> "Revisado"
            else -> {
                ""
            }
        }
        return Name
    }
}

@Root(name = "CerrarCaja")
open class CerrarCaja{
    @field:Element(name = "IdCorteCaja")
    var IdCorteCaja = 0

    @field:Element(name = "SaldoDeclarado")
    var SaldoDeclarado = 0.0
}


@Root(name = "SolicitudTicketPP")
open class SolicitudTicketPP{
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "IdPagoPrestamo")
    var IdPagoPrestamo = 0
}

@Root(name = "RespuestaTicketPP")
class RespuestaTicketPP : AppRespuesta()
{
    @field:Element(name = "FileTicket")
    var FileTicket = ""
}

open class UserApp : Application()
{
    companion object{
        var StrUser = ""
        var StrPass = ""
        var StrNickName = ""
    }
}

open class PrestamoApp : Application()
{
    companion object{
        var IntIdPrestamo = 0
        var IntIdCobranza = 0
        var IntIdDisposicion = 0
        var DblLiquidar = 0.0
        var IntIdGrupoSolidario = 0
        var Integrantes = arrayListOf<AppClienteGrupo>()
    }
}

@Root(name = "CobranzaRepuesta")
open class CobranzaRepuesta : AppRespuesta()
{
    @field:ElementList(name="Cobranzas")
    var Cobranzas = arrayListOf<AppCobranzaRespuesta>()
}

//region GetPrestamosDetalle
@Root(name = "DisposicionMasivaRespuesta")
open class DisposicionMasivaRespuesta : AppRespuesta()
{
    @field:ElementList(name="DetalleDisposicion")
    var DetalleDisposicion = arrayListOf<RespuestaPrestamoDetalle>()
}

@Root(name = "RespuestaPrestamoDetalle")
open class RespuestaPrestamoDetalle : AppRespuesta(){
    //PRESTAMO
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "Folio")
    var Folio = ""

    @field:Element(name = "Cliente")
    var Cliente = ""

    @field:Element(name = "Monto")
    var Monto = 0.0

    @field:Element(name = "Estado")
    var Estado = ""

    @field:Element(name = "FechaSolicitud")
    var FechaSolicitud = Date(Long.MIN_VALUE)

    @field:Element(name = "RFC")
    var RFC = ""

    @field:Element(name = "SaldoPendiente")
    var SaldoPendiente = 0.0

    @field:Element(name = "IdDispAPP")
    var IdDispAPP = 0

    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "IdClienteMoral")
    var IdClienteMoral = 0

    @field:Element(name = "IdGrupoSolidario")
    var IdGrupoSolidario = 0

    @field:Element(name = "cDireccion")
    var cDireccion = ""


    @field:ElementList(name="Integrantes")
    var Integrantes = arrayListOf<AppClienteGrupo>()
}

@Root(name = "RespuestaAccesorios")
open class RespuestaAccesorios : RespuestaPrestamoDetalle(){
    @field:Element(name = "lComision")
    var lComision = false

    @field:Element(name = "nMontoComision")
    var nMontoComision = 0.0

    @field:Element(name = "nSaldoComision")
    var nSaldoComision = 0.0

    @field:Element(name = "lComisionSaldado")
    var lComisionSaldado = false

    @field:Element(name = "DtePagoComision")
    var DtePagoComision = Date(Long.MIN_VALUE)

    @field:Element(name = "nMontoComisionPagando")
    var nMontoComisionPagando = 0.0


    @field:Element(name = "lGarantia")
    var lGarantia = false
    @field:Element(name = "nMontoGarantia")
    var nMontoGarantia = 0.0
    @field:Element(name = "nSaldoGarantia")
    var nSaldoGarantia = 0.0
    @field:Element(name = "lGarantiaSaldado")
    var lGarantiaSaldado = false
    @field:Element(name = "DtePagoGarantia")
    var DtePagoGarantia = Date(Long.MIN_VALUE)
    @field:Element(name = "nMontoGarantiaPagando")
    var nMontoGarantiaPagando = 0.0


    @field:Element(name = "lPrima")
    var lPrima = false
    @field:Element(name = "nMontoPrima")
    var nMontoPrima = 0.0
    @field:Element(name = "nSaldoPrima")
    var nSaldoPrima = 0.0
    @field:Element(name = "lPrimaSaldado")
    var lPrimaSaldado = false
    @field:Element(name = "DtePagoPrima")
    var DtePagoPrima = Date(Long.MIN_VALUE)
    @field:Element(name = "nMontoPrimaPagando")
    var nMontoPrimaPagando = 0.0


    @field:Element(name = "lGastos")
    var lGastos = false

    @field:Element(name = "nMontoGastos")
    var nMontoGastos = 0.0

    @field:Element(name = "nSaldoGastos")
    var nSaldoGastos = 0.0

    @field:Element(name = "lGastosSaldado")
    var lGastosSaldado = false

    @field:Element(name = "DtePagoGastos")
    var DtePagoGastos = Date(Long.MIN_VALUE)

    @field:Element(name = "nMontoGastosPagando")
    var nMontoGastosPagando = 0.0


    @field:Element(name = "lOtros")
    var lOtros = false

    @field:Element(name = "nMontoOtros")
    var nMontoOtros = 0.0

    @field:Element(name = "nSaldoOtros")
    var nSaldoOtros = 0.0

    @field:Element(name = "lOtrosSaldado")
    var lOtrosSaldado = false

    @field:Element(name = "DtePagoOtros")
    var DtePagoOtros = Date(Long.MIN_VALUE)

    @field:Element(name = "nMontoOtrosPagando")
    var nMontoOtrosPagando = 0.0


    @field:Element(name = "nMontoTotalAccesorios")
    var nMontoTotalAccesorios = 0.0

    @field:Element(name = "nMontoPendienteAccesorios")
    var nMontoPendienteAccesorios = 0.0

    @field:Element(name = "IdMedioPago")
    var IdMedioPago = 0

    @field:Element(name = "IdIntegrantePagando")
    var IdIntegrantePagando = 0
}
//endregion

//region EntregaPrestamo
open class SpDetEntregaPrestamo{
    var Id: Int
    var Descripcion: String
    constructor(){
        this.Id = 0
        this.Descripcion = ""
    }
    constructor(_Id: Int, _Descripcion: String) {
        this.Id = _Id
        this.Descripcion = _Descripcion
    }
}

@Root(name = "GetEntregaPrestamo")
open class GetEntregaPrestamo{
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "MontoEntrega")
    var MontoEntrega = 0.0

    @field:Element(name = "FechaEntrega")
    var FechaEntrega = Date(Long.MIN_VALUE)

    @field:Element(name = "IdDispAPP")
    var IdDispAPP = 0
}
//endregion

//region SetUbicacionGPS
@Root(name = "UbicacionesGPS")
open class UbicacionesGPS{
    @field:ElementList(name="Ubicacion")
    var Ubicacion = arrayListOf<UbicacionGPS>()
}

@Root(name = "UbicacionGPS")
open class UbicacionGPS{
    @field:Element(name = "GeoLocalizacion")
    var GeoLocalizacion = ""

    @field:Element(name = "DteUbicacion")
    var DteUbicacion = Date(175111)
}

@Root(name = "RespuestaSetUbicacionesGPS")
open class RespuestaSetUbicacionesGPS : AppRespuesta(){
    @field:Element(name = "Respuesta")
    var Respuesta = ""
}
//endregion

@Root(name = "AppSofomInfoCred")
open class AppSofomInfoCred{
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0
}

@Root(name = "AppSofomSolRenovar")
open class AppSofomSolRenovar : AppSofomInfoCred(){
    @field:Element(name = "Plazo")
    var Plazo = 0

    @field:Element(name = "Monto")
    var Monto = 0.0

    @field:Element(name = "CapitalAdicional")
    var CapitalAdicional = 0.0

    @field:Element(name = "IdTipoPagoPrestamo")
    var IdTipoPagoPrestamo = 0

    @field:Element(name = "DteVencimiento")
    var DteVencimiento = Date(Long.MIN_VALUE)
}

@Root(name = "AppSofomSolReestructurar")
open class AppSofomSolReestructurar : AppSofomInfoCred(){
    @field:Element(name = "MontoAdicional")
    var MontoAdicional = 0.0

    @field:Element(name = "DteVencimiento")
    var DteVencimiento = Date(Long.MIN_VALUE)
}

@Root(name = "AppSofomRespInfoGnal")
open class AppSofomRespInfoGnal : AppRespuesta(){
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "Folio")
    var Folio = ""

    @field:Element(name = "Cliente")
    var Cliente = ""

    @field:Element(name = "MontoPrestamo")
    var MontoPrestamo = 0.0

    @field:Element(name = "IdTipoPagoPrestamo")
    var IdTipoPagoPrestamo = 0

    @field:Element(name = "IdTipoPlazo")
    var IdTipoPlazo = 0

    @field:Element(name = "DteVencimiento")
    var DteVencimiento = Date(Long.MIN_VALUE)
}

@Root(name = "AppSofomRespReest")
open class AppSofomRespReest : AppSofomRespInfoGnal(){
    @field:Element(name = "Capital")
    var Capital = 0.0

    @field:Element(name = "Interes")
    var Interes = 0.0

    @field:Element(name = "IdReestructura")
    var IdReestructura = 0

    @field:Element(name = "FolioReestructura")
    var FolioReestructura = ""

    @field:Element(name = "PlazoReestructura")
    var PlazoReestructura = 0

    @field:Element(name = "MontoReestructura")
    var MontoReestructura = 0.0

    @field:Element(name = "MontoAdicionalReest")
    var MontoAdicionalReest = 0.0

    @field:Element(name = "IsBuenCliente")
    var IsBuenCliente = false
}

@Root(name = "AppSofomRespRenov")
open class AppSofomRespRenov : AppSofomRespInfoGnal(){
    @field:Element(name = "MontoMinPlantilla")
    var MontoMinPlantilla = 0.0

    @field:Element(name = "MontoMaxPlantilla")
    var MontoMaxPlantilla = 0.0

    @field:Element(name = "IdRenovacion")
    var IdRenovacion = 0

    @field:Element(name = "FolioRenovacion")
    var FolioRenovacion = ""

    @field:Element(name = "PlazoRenovacion")
    var PlazoRenovacion = 0

    @field:Element(name = "MontoRenovacion")
    var MontoRenovacion = 0.0
}

class ClienteApp : Application(){
    companion object{
        var IntIdCliente = 0
    }
}

@Root(name = "BuscarCliente")
open class BuscarCliente{
    @field:Element(name = "Nombres")
    var Nombres = ""

    @field:Element(name = "Apellidos")
    var Apellidos = ""

    @field:Element(name = "RFC")
    var RFC = ""
}

class OperadorApp : Application(){
    companion object{
        var IntIdOperador = -1
    }
}

@Root(name = "BuscarOperador")
open class BuscarOperador{
    @field:Element(name = "Nombres")
    var Nombres = ""

    @field:Element(name = "Apellidos")
    var Apellidos = ""
}

@Root(name = "RespuestaGetClientes")
open class RespuestaGetClientes : AppRespuesta(){
    @field:Element(name = "Respuesta")
    var Respuesta = ""
}

@Root(name = "AppSofomInfoCliente")
open class AppSofomInfoCliente{
    @field:Element(name = "IdCliente")
    var IdCliente = 0
}

@Root(name = "AppSofomInfoGrupoSolidario")
open class AppSofomInfoGrupoSolidario : IBasicListElement {
    @field:Element(name = "IdGrupoSolidario")
    var IdGrupoSolidario = 0

    @field:Element(name = "cNombreGrupo")
    var cNombreGrupo = ""

    @field:Element(name = "nRenovacion")
    var nRenovacion = 0

    @field:Element(name = "nMonto")
    var nMonto = 0.0

    override fun getId(): Int {
        return IdGrupoSolidario;
    }

    override fun getDescription(): String {
        return cNombreGrupo;
    }

    override fun getDetailDescription(): String {
        return ""
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdGrupoSolidario = Id
        this.cNombreGrupo = description
    }
}

@Root(name = "AppGrupoSolidario")
open class AppGrupoSolidario : AppRespuesta(){
    @field:Element(name = "IdGrupoSolidario")
    var IdGrupoSolidario = 0

    @field:Element(name = "cNombre")
    var cNombre = ""

    @field:Element(name = "nMonto")
    var nMonto = 0.0

    @field:Element(name = "nGarantiaLiquida")
    var nGarantiaLiquida = 0.0

    @field:Element(name = "cObservaciones")
    var cObservaciones = ""

    @field:Element(name = "nRenovacion")
    var nRenovacion = 0

    @field:Element(name = "IdGrupoRenovacion")
    var IdGrupoRenovacion = 0

    @field:Element(name = "cNombreRenovacion")
    var cNombreRenovacion = ""


    @field:ElementList(name = "Integrantes")
    var Integrantes = arrayListOf<AppClienteGrupo>()

    @field:Element(name = "showRolGrupal")
    var showRolGrupal = true

    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "IdEdoPrestamo")
    var IdEdoPrestamo = 0

    @field:Element(name = "nPorcentajeGarantia")
    var nPorcentajeGarantia = 0.0
}

@Root(name = "AppClienteGrupo")
open class AppClienteGrupo : AppClienteSimple(){
    @field:Element(name = "IdRelGrupoSCliente")
    var IdRelGrupoSCliente = 0

    @field:Element(name = "nMonto")
    var nMonto = 0.0

    @field:Element(name = "nGarantiaLiquida")
    var nGarantiaLiquida = 0.0

    @field:Element(name = "IdRolGrupal")
    var IdRolGrupal = 1

    @field:Element(name = "nMontoPago")
    var nMontoPago = 0.0

    @field:Element(name = "nGarantiaPagada")
    var nGarantiaPagada = 0.0

    @field:Element(name = "lGarantiaSaldada")
    var lGarantiaSaldada = false

    @field:Element(name = "DteSaldadoGarantia")
    var DteSaldadoGarantia = Date(Long.MIN_VALUE)
}

@Root(name = "AppGrupoSolidarioBusquedaRespuesta")
open class AppGrupoSolidarioBusquedaRespuesta : AppRespuesta(){
    @field:ElementList(name = "Grupos")
    var Grupos = arrayListOf<AppSofomInfoGrupoSolidario>()
}

@Root(name = "AppClienteComplete")
open class AppClienteComplete : AppClienteSimple(){
    @field:Element(name = "Genero")
    var Genero = ""

    @field:Element(name = "DteNacimiento")
    var DteNacimiento = Date(Long.MIN_VALUE)

    @field:Element(name = "IdEntidadFederativa")
    var IdEntidadFederativa = 0

    @field:Element(name = "EntidadFederativa")
    var EntidadFederativa = ""

    @field:Element(name = "IdNacionalidad")
    var IdNacionalidad = 0

    @field:Element(name = "Nacionalidad")
    var Nacionalidad = ""

    @field:Element(name = "Curp")
    var Curp = ""

    @field:Element(name = "IdEstadoCivil")
    var IdEstadoCivil = 0

    @field:Element(name = "EstadoCivil")
    var EstadoCivil = ""

    @field:Element(name = "IdEscolaridad")
    var IdEscolaridad = 0

    @field:Element(name = "Escolaridad")
    var Escolaridad = ""

    @field:Element(name = "Salario")
    var Salario = 0.0

    @field:Element(name = "OtrosIngresos")
    var OtrosIngresos = 0.0

    @field:Element(name = "cOtrosIngresos")
    var cOtrosIngresos = ""

    @field:Element(name = "GastoRenta")
    var GastoRenta = 0.0

    @field:Element(name = "OtrosGastos")
    var OtrosGastos = 0.0

    @field:Element(name = "GastoNomina")
    var GastoNomina = 0.0


    @field:Element(name = "IdRefLab1")
    var IdRefLab1 = 0

    @field:Element(name = "IdRefLab2")
    var IdRefLab2 = 0

    @field:Element(name = "IdRefVec1")
    var IdRefVec1 = 0

    @field:Element(name = "IdRefVec2")
    var IdRefVec2 = 0


    @field:Element(name = "RefLab1")
    var RefLab1 = ""

    @field:Element(name = "RefLab2")
    var RefLab2 = ""

    @field:Element(name = "RefVec1")
    var RefVec1 = ""

    @field:Element(name = "RefVec2")
    var RefVec2 = ""


    @field:Element(name = "IdNegocio")
    var IdNegocio = 0

    @field:Element(name = "Negocio")
    var Negocio = ""

    @field:Element(name = "IdDomicilio")
    var IdDomicilio = 0

    @field:Element(name = "Domicilio")
    var Domicilio = ""

    @field:Element(name = "IdClienteDireccion")
    var IdClienteDireccion = 0

    @field:Element(name = "CurpSolicitada")
    var CurpSolicitada = false
}

@Root(name = "AppClienteSimple")
open class AppClienteSimple : AppRespuesta(), IBasicListElement {
    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "Nombre1")
    var Nombre1 = ""

    @field:Element(name = "Nombre2")
    var Nombre2 = ""

    @field:Element(name = "ApellidoPat")
    var ApellidoPat = ""

    @field:Element(name = "ApellidoMat")
    var ApellidoMat = ""

    @field:Element(name = "RFC")
    var RFC = ""

    @field:Element(name = "Observaciones")
    var Observaciones = ""

    fun getNombreCompleto(): String {
        return ((this.Nombre1.trim() + " " + this.Nombre2.trim()).trim() + " " + (this.ApellidoPat.trim() + " " + this.ApellidoMat.trim()).trim()).trim()
    }
    override fun getId(): Int {
        return IdCliente;
    }

    override fun getDescription(): String {
        return this.getNombreCompleto()
    }

    override fun getDetailDescription(): String {
        return RFC
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdCliente = Id
        this.Nombre1 = description
        this.RFC = detailDescription
    }
}

@Root(name = "ClienteRepuesta")
open class ClienteRepuesta : AppRespuesta(){
    @field:ElementList(name = "Clientes")
    var Clientes = arrayListOf<AppClienteSimple>()
}

@Root(name = "AppOperadorSimple")
open class AppOperadorSimple : AppRespuesta(), IBasicListElement {
    @field:Element(name = "IdOperador")
    var IdOperador = 0

    @field:Element(name = "Operador")
    var Operador = ""

    @field:Element(name = "Nivel")
    var Nivel = ""

    override fun getId(): Int {
        return this.IdOperador
    }

    override fun getDescription(): String {
        return this.Operador
    }

    override fun getDetailDescription(): String {
        return this.Nivel;
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdOperador = Id
        this.Operador = description
        this.Nivel = detailDescription
    }
}

@Root(name = "BuscarPermisoOperador")
open class BuscarPermisoOperador{
    @field:Element(name = "Permiso")
    var Permiso = ""
}

@Root(name = "AppOperadorPermiso")
open class AppOperadorPermiso : AppOperadorSimple(){
    @field:Element(name = "PermisoResult")
    var PermisoResult = 0
}

@Root(name = "AppOperadorCartera")
open class AppOperadorCartera : AppOperadorSimple(){
    @field:Element(name = "SaldoCapital")
    var SaldoCapital = 0.0

    @field:Element(name = "SaldoInteres")
    var SaldoInteres = 0.0

    @field:Element(name = "TotalCapitalCobrado")
    var TotalCapitalCobrado = 0.0

    @field:Element(name = "TotalInteresCobrado")
    var TotalInteresCobrado = 0.0

    @field:Element(name = "CapitalEsperado")
    var CapitalEsperado = 0.0

    @field:Element(name = "InteresEsperado")
    var InteresEsperado = 0.0

    @field:Element(name = "CapitalCobradoRango")
    var CapitalCobradoRango = 0.0

    @field:Element(name = "InteresCobradoRango")
    var InteresCobradoRango = 0.0
}

@Root(name = "GetSinPagoCapital")
open class GetSinPagoCapital : AppRespuesta(){
    @field:Element(name = "nDias")
    var nDias = 0

    @field:Element(name = "IdOperadorCobranza")
    var IdOperadorCobranza = 0

    @field:Element(name = "IsMovil")
    var IsMovil = true
}

@Root(name = "GetCarteraOperador")
open class GetCarteraOperador : AppRespuesta(){
    @field:Element(name = "IdOperador")
    var IdOperador = 0

    @field:Element(name = "DteFechaIni")
    var DteFechaIni = Date(Long.MIN_VALUE)

    @field:Element(name = "DteFechaFin")
    var DteFechaFin = Date(Long.MIN_VALUE)
}

@Root(name = "OperadorRepuesta")
open class OperadorRepuesta : AppRespuesta(){
    @field:ElementList(name = "Operadores")
    var Operadores = arrayListOf<AppOperadorSimple>()
}

@Root(name = "OperadorCarteraRepuesta")
open class OperadorCarteraRepuesta : AppRespuesta(){
    @field:ElementList(name = "CarteraOperador")
    var CarteraOperador = arrayListOf<AppOperadorCartera>()
}

@Root(name = "AppSinPagoCapital")
open class AppSinPagoCapital{
    @field:Element(name = "Operador")
    var Operador = ""

    @field:Element(name = "cFolio")
    var cFolio = ""

    @field:Element(name = "cCliente")
    var cCliente = ""

    @field:Element(name = "cTelefono")
    var cTelefono = ""

    @field:Element(name = "Dias")
    var Dias = 0

    @field:Element(name = "CapEsperado")
    var CapEsperado = 0.0
}

@Root(name = "SinPagoCapitalRespuesta")
open class SinPagoCapitalRespuesta : AppRespuesta(){
    @field:ElementList(name = "SinPagar")
    var SinPagar = arrayListOf<AppSinPagoCapital>()
}

//region Datos PLD
@Root(name = "ClientePLD")
open class ClientePLD : AppRespuesta(){
    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "Nombre")
    var Nombre = ""

    @field:Element(name = "Apellidos")
    var Apellidos = ""

    @field:Element(name = "DatosPld")
    var DatosPld: DatosClientePLD? = null

    @field:ElementList(name = "Documentos")
    var Documentos = arrayListOf<DatosDocumentoPLD>()
}

@Root(name = "DatosClientePLD")
class DatosClientePLD{
    @field:Element(name = "Funcionario")
    var Funcionario = 0

    @field:Element(name = "Cargo")
    var Cargo = 0

    @field:Element(name = "Parentesco")
    var Parentesco = 0

    @field:Element(name = "IdClienteParentesco")
    var IdClienteParentesco = 0

    @field:Element(name = "NombreClienteParentesco")
    var NombreClienteParentesco = ""

    @field:Element(name = "Activo")
    var Activo = 0

    @field:Element(name = "FechaBaja")
    var FechaBaja = Date(Long.MIN_VALUE)
}

@Root(name = "DatosDocumentoPLD")
open class DatosDocumentoPLD{
    @field:Element(name = "Id")
    var Id = 0

    @field:Element(name = "IdTipoDoc")
    var IdTipoDoc = 0

    @field:Element(name = "NombreTipoDoc")
    var NombreTipoDoc = ""

    @field:Element(name = "Existe")
    var Existe = 0
}

@Root(name = "SolicitudPLD")
open class SolicitudPLD{
    @field:Element(name = "IdCliente")
    var IdCliente = 0
}

@Root(name = "SaveDocumentoPLD")
open class SaveDocumentoPLD{
    @field:Element(name = "Id")
    var Id = 0

    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "IdTipoDoc")
    var IdTipoDoc = 0

    @field:Element(name = "File")
    var File = ""

    @field:Element(name = "FileName")
    var FileName = ""
}

@Root(name = "SaveDocumentoPLDResponse")
class SaveDocumentoPLDResponse : AppRespuesta(){
    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "Id")
    var Id = 0

    @field:Element(name = "Existe")
    var Existe = 0
}
//endregion

//region GetPlantillas
@Root(name = "GetPlantilla")
open class GetPlantilla{
    @field:Element(name = "Nombre")
    var Nombre = ""

    @field:Element(name = "Descripcion")
    var Descripcion = ""
}

@Root(name = "AppPlantilla")
open class AppPlantilla : IBasicListElement {
    @field:Element(name = "IdPlantilla")
    var IdPlantilla = 0

    @field:Element(name = "NombrePlantilla")
    var NombrePlantilla = ""

    @field:Element(name = "Descripcion")
    var Descripcion = ""

    @field:Element(name = "IdTipoPagoPrestamo")
    var IdTipoPagoPrestamo = 0

    override fun getId(): Int {
        return this.IdPlantilla
    }

    override fun getDescription(): String {
        return this.NombrePlantilla
    }

    override fun getDetailDescription(): String {
        return this.Descripcion
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdPlantilla = Id
        this.NombrePlantilla = description
        this.Descripcion = detailDescription
    }
}

@Root(name = "PlantillaRespuesta")
open class PlantillaRespuesta : AppRespuesta(){
    @field:ElementList(name = "Plantilla")
    var Plantilla = arrayListOf<AppPlantilla>()
}
//endregion

//region GetZonas
@Root(name = "GetZonas")
open class GetZonas{
    @field:Element(name = "Nombre")
    var Nombre = ""

    @field:Element(name = "Descripcion")
    var Descripcion = ""
}
@Root(name = "AppZonas")
open class AppZonas : IBasicListElement {
    @field:Element(name = "IdZona")
    var IdZona = 0

    @field:Element(name = "NombreZona")
    var NombreZona = ""

    override fun getId(): Int {
        return this.IdZona
    }

    override fun getDescription(): String {
        return this.NombreZona
    }

    override fun getDetailDescription(): String {
        return ""
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdZona = Id;
        this.NombreZona = description
    }

}

@Root(name = "ZonasRespuesta")
open class ZonasRespuesta : AppRespuesta(){
    @field:ElementList(name = "Plantilla")
    var Plantilla = arrayListOf<AppZonas>()
}
//endregion

//region Response Solicitud de Credito
@Root(name = "SolicitudCredApp")
open class SolicitudCredApp : AppRespuesta(){
    @field:Element(name = "IdPrestamo")
    var IdPrestamo = 0

    @field:Element(name = "IdPlantillaCred")
    var IdPlantillaCred = 0

    @field:Element(name = "NombrePantilla")
    var NombrePantilla = ""

    @field:Element(name = "Folio")
    var Folio = ""

    @field:Element(name = "MontoTotal")
    var MontoTotal = 0.0

    @field:Element(name = "IdTipoPagoPrestamo")
    var IdTipoPagoPrestamo = 0

    @field:Element(name = "IdTipoPlazo")
    var IdTipoPlazo = 0

    @field:Element(name = "Plazo")
    var Plazo = 0

    @field:Element(name = "DteVencimiento")
    var DteVencimiento = Date(Long.MIN_VALUE)

    @field:Element(name = "IdZona")
    var IdZona = 0

    @field:Element(name = "Zona")
    var Zona = ""

    @field:Element(name = "IdCliente")
    var IdCliente = 0

    @field:Element(name = "Cliente")
    var Cliente = ""

    @field:Element(name = "IdEdoPrestamo")
    var IdEdoPrestamo = 0

    @field:Element(name = "IdGrupoSolidario")
    var IdGrupoSolidario = 0

    @field:Element(name = "GrupoSolidario")
    var GrupoSolidario = ""
}
//endregion

//region Direcciones
@Root(name = "GetDireccion")
open class GetDireccion{
    @field:Element(name = "Asentamiento")
    var Asentamiento = ""

    @field:Element(name = "Calle")
    var Calle = ""

    @field:Element(name = "Ext")
    var Ext = ""

    @field:Element(name = "CP")
    var CP = ""
}
@Root(name = "AppDireccion")
open class AppDireccion : AppRespuesta(), IBasicListElement {
    @field:Element(name = "IdDireccion")
    var IdDireccion = 0

    @field:Element(name = "Calle")
    var Calle = ""

    @field:Element(name = "NumExterior")
    var NumExterior = ""

    @field:Element(name = "NumInterior")
    var NumInterior = ""

    @field:Element(name = "Cruzamiento1")
    var Cruzamiento1 = ""

    @field:Element(name = "Cruzamiento2")
    var Cruzamiento2 = ""

    @field:Element(name = "Formato")
    var Formato = 0

    @field:Element(name = "IdSepomex")
    var IdSepomex = 0

    @field:Element(name = "TipoAsentamiento")
    var TipoAsentamiento = ""

    @field:Element(name = "Asentamiento")
    var Asentamiento = ""

    @field:Element(name = "NoEstructurado")
    var NoEstructurado = ""

    @field:Element(name = "Municipio")
    var Municipio = ""

    @field:Element(name = "Estado")
    var Estado = ""

    @field:Element(name = "CP")
    var CP = ""

    @field:Element(name = "Geolocalizacion")
    var Geolocalizacion = ""

    override fun getId(): Int {
        return IdDireccion
    }

    override fun getDescription(): String {
        val d1 = when (Formato) {
            //0: noasignado, 1: numerada
            0, 1 -> "Calle " + Calle + " #" + NumExterior + " " + (if (NumInterior !== "") "Int $NumInterior" else "") + if (Cruzamiento1 !== "") " entre " + Cruzamiento1 + " " + (if (Cruzamiento2 !== "") " y $Cruzamiento2" else "") else ""
            //Nombrada
            2 -> Calle + " " + NumExterior + " " + (if (NumInterior !== "") " $NumInterior" else "") + if (Cruzamiento1 !== "") " entre " + Cruzamiento1 + " " + (if (Cruzamiento2 !== "") " y $Cruzamiento2" else "") else ""
            //No estructurada
            3 -> Calle
            else -> {
                Calle + " " + NumExterior + " " + (if (NumInterior !== "") " $NumInterior" else "") + if (Cruzamiento1 !== "") " entre " + Cruzamiento1 + " " + (if (Cruzamiento2 !== "") " y $Cruzamiento2" else "") else ""
            }
        }
        return d1.trim()
    }

    fun getDetailSepomex(): String {
        return "$Asentamiento C.P:$CP $Municipio, $Estado"
    }

    override fun getDetailDescription(): String {
        return (if (Formato != 3) "$TipoAsentamiento: " else "") + getDetailSepomex()
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdDireccion = Id
        this.Calle = description
        this.Formato = 3
    }
}

@Root(name = "DireccionRespuesta")
open class DireccionRespuesta : AppRespuesta(){
    @field:ElementList(name = "Direccion")
    var Direccion = arrayListOf<AppDireccion>()
}
//endregion

//region Asentamientos
@Root(name = "GetAsentamiento")
open class GetAsentamiento{
    @field:Element(name = "CP")
    var CP = ""

    @field:Element(name = "Asentamiento")
    var Asentamiento = ""

    @field:Element(name = "Municipio")
    var Municipio = ""

    @field:Element(name = "Ciudad")
    var Ciudad = ""

    @field:Element(name = "Estado")
    var Estado = ""
}
@Root(name = "AppAsentamiento")
open class AppAsentamiento : IBasicListElement {
    @field:Element(name = "IdSepomex")
    var IdSepomex = 0

    @field:Element(name = "CP")
    var CP = ""

    @field:Element(name = "Asentamiento")
    var Asentamiento = ""

    @field:Element(name = "Municipio")
    var Municipio = ""

    @field:Element(name = "Ciudad")
    var Ciudad = ""

    @field:Element(name = "Estado")
    var Estado = ""

    override fun getId(): Int {
        return IdSepomex
    }

    override fun getDescription(): String {
        return "$Asentamiento C.P:$CP $Municipio, $Estado"
    }

    override fun getDetailDescription(): String {
        return ""
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdSepomex = Id
        this.Asentamiento = description
    }
}
@Root(name = "AsentamientoRespuesta")
class AsentamientoRespuesta : AppRespuesta(){
    @field:ElementList(name = "Asentamiento")
    var Asentamiento = arrayListOf<AppAsentamiento>()
}
//endregion

//region Telefono
@Root(name = "AppSofomInfoTelefono")
open class AppSofomInfoTelefono : AppRespuesta(){
    @field:Element(name = "IdTel")
    var IdTel = 0

    @field:Element(name = "nTipoCliente")
    var nTipoCliente = 0

    @field:Element(name = "IdCliente")
    var IdCliente = 0
}
@Root(name = "AppClienteTelefono")
open class AppClienteTelefono : AppSofomInfoTelefono(){
    @field:Element(name = "IdTipoTel")
    var IdTipoTel = 0

    @field:Element(name = "cTipoTel")
    var cTipoTel = ""

    @field:Element(name = "cTel")
    var cTel = ""
}
@Root(name = "ClienteTelefonoRepuesta")
open class ClienteTelefonoRepuesta : AppRespuesta(){
    @field:ElementList(name = "Telefonos")
    var Telefonos = arrayListOf<AppClienteTelefono>()
}
//endregion

//region GetEmpresas
@Root(name = "GetEmpresa")
open class GetEmpresa{
    @field:Element(name = "Nombre")
    var Nombre = ""
}

@Root(name = "AppEmpresaBusqueda")
class AppEmpresaBusqueda : IBasicListElement {
    @field:Element(name = "IdEmpresa")
    var IdEmpresa = 0

    @field:Element(name = "Empresa")
    var Empresa = ""

    override fun getId(): Int {
        return this.IdEmpresa
    }

    override fun getDescription(): String {
        return this.Empresa
    }

    override fun getDetailDescription(): String {
        return ""
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdEmpresa = Id
        this.Empresa = description
    }
}
@Root(name = "EmpresaRespuesta")
open class EmpresaRespuesta : AppRespuesta(){
    @field:ElementList(name = "Empresa")
    var Empresa = arrayListOf<AppEmpresaBusqueda>()
}

@Root(name = "NegocioSave")
open class NegocioSave : AppRespuesta(){
    @field:Element(name = "IdNegocio")
    var IdNegocio = 0

    @field:Element(name = "NombreNegocio")
    var NombreNegocio = ""

    @field:Element(name = "AnguedadNegocio")
    var AnguedadNegocio = 0

    @field:Element(name = "NumeroEmpleados")
    var NumeroEmpleados = 0

    @field:Element(name = "Ingreso")
    var Ingreso = 0.0

    @field:Element(name = "Egreso")
    var Egreso = 0.0

    @field:Element(name = "IdGiroNegocio")
    var IdGiroNegocio = 0

    @field:Element(name = "GiroNegocio")
    var GiroNegocio = ""

    @field:Element(name = "IdDomicilio")
    var IdDomicilio = 0

    @field:Element(name = "Domicilio")
    var Domicilio = ""

    @field:Element(name = "IdSituacionDom")
    var IdSituacionDom = 0

    @field:Element(name = "AntiguedadDom")
    var AntiguedadDom = 0

    @field:Element(name = "IdHorarioLU")
    var IdHorarioLU = 0

    @field:Element(name = "HorarioLU")
    var HorarioLU = ""

    @field:Element(name = "IdHorarioMA")
    var IdHorarioMA = 0

    @field:Element(name = "HorarioMA")
    var HorarioMA = ""

    @field:Element(name = "IdHorarioMI")
    var IdHorarioMI = 0

    @field:Element(name = "HorarioMI")
    var HorarioMI = ""

    @field:Element(name = "IdHorarioJU")
    var IdHorarioJU = 0

    @field:Element(name = "HorarioJU")
    var HorarioJU = ""

    @field:Element(name = "IdHorarioVI")
    var IdHorarioVI = 0

    @field:Element(name = "HorarioVI")
    var HorarioVI = ""

    @field:Element(name = "IdHorarioSA")
    var IdHorarioSA = 0

    @field:Element(name = "HorarioSA")
    var HorarioSA = ""

    @field:Element(name = "IdHorarioDO")
    var IdHorarioDO = 0

    @field:Element(name = "HorarioDO")
    var HorarioDO = ""
}
//endregion

//region GetGiros
@Root(name = "GetGiro")
open class GetGiro{
    @field:Element(name = "Nombre")
    var Nombre = ""
}

@Root(name = "AppGiroBusqueda")
open class AppGiroBusqueda : IBasicListElement {
    @field:Element(name = "IdGiro")
    var IdGiro = 0

    @field:Element(name = "Giro")
    var Giro = ""

    override fun getId(): Int {
        return this.IdGiro
    }

    override fun getDescription(): String {
        return this.Giro.trim()
    }

    override fun getDetailDescription(): String {
        return ""
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdGiro = Id;
        this.Giro = description;
    }
}
@Root(name = "GiroRespuesta")
open class GiroRespuesta : AppRespuesta(){
    @field:ElementList(name = "Giro")
    var Giro = arrayListOf<AppGiroBusqueda>()
}
//endregion

//region GetHorarios
@Root(name = "GetHorario")
open class GetHorario{
    @field:Element(name = "Horario")
    var Horario = ""
}
@Root(name = "AppHorario")
open class AppHorario : IBasicListElement {
    @field:Element(name = "IdHorario")
    var IdHorario = 0

    @field:Element(name = "Horario")
    var Horario = ""

    override fun getId(): Int {
        return this.IdHorario
    }

    override fun getDescription(): String {
        return this.Horario.trim()
    }

    override fun getDetailDescription(): String {
        return ""
    }

    override fun getDetailDescription2(): String {
        return ""
    }

    override fun init(
        Id: Int,
        description: String,
        detailDescription: String,
        detailDescription2: String
    ) {
        this.IdHorario = Id
        this.Horario = description
    }
}
@Root(name = "HorarioRespuesta")
open class HorarioRespuesta : AppRespuesta(){
    @field:ElementList(name = "Horario")
    var Horario = arrayListOf<AppHorario>()
}
//endregion

@Root(name = "SolicitudReporteCorteC")
open class SolicitudReporteCorteC{
    @field:Element(name = "IdCorteCaja")
    var IdCorteCaja = 0
}
@Root(name = "RespuestaReporteCorteC")
open class RespuestaReporteCorteC : AppRespuesta(){
    @field:Element(name = "FileReporte")
    var FileReporte = ""
}