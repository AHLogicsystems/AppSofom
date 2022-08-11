package com.logicsystems.appsofom.datos

//region RequestCodes
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
    COBROACCESORIO
}