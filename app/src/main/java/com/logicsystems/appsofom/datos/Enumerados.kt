package com.logicsystems.appsofom.datos

//region RequestCodes
enum class REQUEST_CODES {
    PICK_OR_CAPTURE_FILE,
    SEARCH_CLIENTEPLD_PARENTESCO,
    LIST_DOMICILIO,
    PICK_DOMICILIO,
    PICK_ASENTAMIENTO,
    SEARCH_NEGOCIO_LIST,
    SEARCH_NEGOCIO_PICK,
    SEARCH_HORARIO_PICK_LU,
    SEARCH_HORARIO_PICK_MA,
    SEARCH_HORARIO_PICK_MI,
    SEARCH_HORARIO_PICK_JU,
    SEARCH_HORARIO_PICK_VI,
    SEARCH_HORARIO_PICK_SA,
    SEARCH_HORARIO_PICK_DO,
    SEARCH_GIRO_PICK,
    SEARCH_CAT_CLIENTES,
    SEARCH_CAT_CLIENTES_REF,
    PICK_REFERENCIA_LABORAL_1,
    PICK_REFERENCIA_LABORAL_2,
    PICK_REFERENCIA_VECINAL_1,
    PICK_REFERENCIA_VECINAL_2,
    TELEFONOS_CLIENTE,
    TELEFONOS_EMPRESA,
    SEARCH_CAT_OPERADORES,
    PICK_OPERADOR,
    SEARCH_GRUPO_SOLIDARIO_LIST,
    SEARCH_GRUPO_SOLIDARIO_PICK,
    NONE,
    LIST
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