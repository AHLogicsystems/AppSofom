package com.logicsystems.appsofom.datos

//region RequestCodes
enum class REQUEST_CODES(val valor: Int) {
    PICK_OR_CAPTURE_FILE(10001),
    SEARCH_CLIENTEPLD_PARENTESCO(11001),
    LIST_DOMICILIO(12000),
    PICK_DOMICILIO(12001),
    PICK_ASENTAMIENTO(12002),
    SEARCH_NEGOCIO_LIST(21544),
    SEARCH_NEGOCIO_PICK(21550),
    SEARCH_HORARIO_PICK_LU(22000),
    SEARCH_HORARIO_PICK_MA(22001),
    SEARCH_HORARIO_PICK_MI(22002),
    SEARCH_HORARIO_PICK_JU(22003),
    SEARCH_HORARIO_PICK_VI(22004),
    SEARCH_HORARIO_PICK_SA(22005),
    SEARCH_HORARIO_PICK_DO(22006),
    SEARCH_GIRO_PICK(23000),
    SEARCH_CAT_CLIENTES(13000),
    SEARCH_CAT_CLIENTES_REF(13001),
    PICK_REFERENCIA_LABORAL_1(13002),
    PICK_REFERENCIA_LABORAL_2(13003),
    PICK_REFERENCIA_VECINAL_1(13004),
    PICK_REFERENCIA_VECINAL_2(13005),
    TELEFONOS_CLIENTE(14001),
    TELEFONOS_EMPRESA(14002),
    SEARCH_CAT_OPERADORES(15000),
    PICK_OPERADOR(15001),
    SEARCH_GRUPO_SOLIDARIO_LIST(16001),
    SEARCH_GRUPO_SOLIDARIO_PICK(16002),
    NONE(115500),
    LIST(115501),
    PICK(115502)
}

    enum class WS_OPERATION {
    NONE,
    PRELOAD_CLIENTE,
    SAVE_CLIENTE,
    LOAD_DOCUMENTOS,
    SAVE_DOCUMENTO,
    CLEAR_DOCUMENTO,
    REQUEST_IMAGE_PICK,
    SEARCH_DOMICILIOS,
    SEARCH_ASENTAMIENTOS,
    SAVE_DOMICILIO,
    REQUEST_GPS_LOCATION
}
//endregion

//region Prestamo
enum class TipoEmisor
{
    Cliente,
    Tercero
}
enum class TiposAbonoCap
{
    NoEspecificado,
    PlazoYLetra,
    Letra,
    SiguienteLetra,
    ReducirIntereses,
    AbonoGarantiaLiquida
}
//endregion

//region Caja
enum class EdosCorte
{
    nulo,
    Abierto,
    Cerrado,
    Cancelado,
    Revisado
}
enum class EdosCierreCorte
{
    Normal,
    Forzado,
    ForzadoYDeclarado
}
//endregion

enum class PermisoResult {
    Denegado, Otorgado, Pendiente
}

enum class TiposRef
{
    nulo,
    Disposicion,
    Cobranza,
    PagosPrestamo
}

enum class TipoDeleteIntegrantes
{
    Ninguno,
    Integrante,
    Grupo,
    Prestamo,
    Tipo,
    Referencia,
    All
}

enum class DEBUG_MODE {
    LOCAL,
    TESTING_INTERNO,
    TESTING_EXTERNO,
    PRODUCCION
}

enum class SolicitudCredito{
    SOLICITUD,
    RENOVAR,
    REESTRUCTURAR,
    ENTREGAR,
    COBRAR,
    COBROACCESORIO,
    LISTACREDITOXCOBRAR
}

enum class Catalogos{
    CLIENTES,
    GRUPOSSOLIDARIOS,
    REFERENCIAS,
    DOMICILIOS,
    NEGOCIOS,
    PLD
}

enum class MetodosApp{
    AppGetEmpresas,
    AppLogin,
    MultiWebMethodsApp
}