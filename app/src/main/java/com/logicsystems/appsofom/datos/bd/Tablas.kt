package com.logicsystems.appsofom.datos.bd


//region Configuracion
object Configuracion {
    const val TABLE_NAME = "Configuracion"

    const val Id = "Id"
    const val cEntorno = "cEntorno"
    const val cEmpresa = "cEmpresa"
    const val nMinUpdateGPS = "nMinUpdateGPS"
    const val nMinUpdateInfo = "nMinUpdateInfo"
    const val cLoginUser = "cLoginUser"
    const val cLoginPass = "cLoginPass"
    const val cOperador = "cOperador"
    const val cInfoTicket = "cInfoTicket"
    const val cIMEI = "cIMEI"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$cEntorno TEXT NOT NULL," +
                "$cEmpresa TEXT NOT NULL," +
                "$nMinUpdateGPS INTEGER DEFAULT 0," +
                "$nMinUpdateInfo INTEGER DEFAULT 0," +
                "$cLoginUser TEXT NOT NULL," +
                "$cLoginPass TEXT NOT NULL," +
                "$cOperador TEXT NOT NULL," +
                "$cInfoTicket TEXT NOT NULL," +
                "$cIMEI TEXT NOT NULL" +
                ");"


    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region UbicacionGPS
object UbicacionGPS {
    const val TABLE_NAME = "UbicacionGPS"

    const val Id = "Id"
    const val cUbicacion = "cUbicacion"
    const val DteSave = "DteSave"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$cUbicacion TEXT NOT NULL," +
                "$DteSave INTEGER DEFAULT (strftime('%s', 'now'));"

    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region OffLineDisposicion
object OfflineDisposicion {
    const val TABLE_NAME = "OffLineDisposicion"

    const val Id = "Id"
    const val IdPrestamo = "IdPrestamo"
    const val cFolio = "cFolio"
    const val cCliente = "cCliente"
    const val nMontoTotal = "nMontoTotal"
    const val nSaldoPendiente = "nSaldoPendiente"
    const val DteSaveInfo = "DteSaveInfo"
    const val IdCliente = "IdCliente"
    const val IdClienteMoral = "IdClienteMoral"
    const val IdGrupoSolidario = "IdGrupoSolidario"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$IdPrestamo INTEGER NOT NULL DEFAULT 0," +
                "$cFolio TEXT NOT NULL," +
                "$cCliente TEXT NOT NULL," +
                "$nMontoTotal REAL NOT NULL,," +
                "$nSaldoPendiente REAL NOT NULL," +
                "$DteSaveInfo INTEGER DEFAULT (strftime('%s', 'now'))," +
                "$IdCliente INTEGER NOT NULL DEFAULT 0," +
                "$IdClienteMoral INTEGER NOT NULL DEFAULT 0," +
                "$IdGrupoSolidario INTEGER NOT NULL DEFAULT 0);"

    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region Disposiciones
object Disposiciones {
    const val TABLE_NAME = "Disposiciones"

    const val Id = "Id"
    const val IdPrestamo = "IdPrestamo"
    const val cFolio = "cFolio"
    const val cCliente = "cCliente"
    const val nMontoTotal = "nMontoTotal"
    const val nSaldoPendiente = "nSaldoPendiente"
    const val nMontoDisp = "nMontoDisp"
    const val DteSaveInfo = "DteSaveInfo"
    const val cErrorWS = "cErrorWS"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$IdPrestamo INTEGER NOT NULL DEFAULT 0," +
                "$cFolio TEXT NOT NULL," +
                "$cCliente TEXT NOT NULL," +
                "$nMontoTotal REAL NOT NULL,," +
                "$nSaldoPendiente REAL NOT NULL," +
                "$nMontoDisp REAL NOT NULL," +
                "$DteSaveInfo INTEGER DEFAULT (strftime('%s', 'now'))," +
                "$cErrorWS TEXT NOT NULL);"

    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region OffLineCobranza
object OfflineCobranza {
    const val TABLE_NAME = "OffLineCobranza"

    const val Id = "Id"
    const val IdPrestamo = "IdPrestamo"
    const val cFolio = "cFolio"
    const val cCliente = "cCliente"
    const val nPendiente = "nPendiente"
    const val nAlDia = "nAlDia"
    const val nLiquidar = "nLiquidar"
    const val DteSaveInfo = "DteSaveInfo"
    const val IdCliente = "IdCliente"
    const val IdClienteMoral = "IdClienteMoral"
    const val IdGrupoSolidario = "IdGrupoSolidario"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$IdPrestamo INTEGER NOT NULL DEFAULT 0," +
                "$cFolio TEXT NOT NULL," +
                "$cCliente TEXT NOT NULL," +
                "$nPendiente REAL NOT NULL,," +
                "$nAlDia REAL NOT NULL," +
                "$nLiquidar REAL NOT NULL," +
                "$DteSaveInfo INTEGER DEFAULT (strftime('%s', 'now'))," +
                "$IdCliente INTEGER NOT NULL DEFAULT 0," +
                "$IdClienteMoral INTEGER NOT NULL DEFAULT 0," +
                "$IdGrupoSolidario INTEGER NOT NULL DEFAULT 0);"


    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region Pagos
object Pagos {
    const val TABLE_NAME = "Pagos"

    const val Id = "Id"
    const val IdPrestamo = "IdPrestamo"
    const val cFolio = "cFolio"
    const val cCliente = "cCliente"
    const val nPago = "nPago"
    const val IdMedioPago = "IdMedioPago"
    const val cNumeroCheque = "cNumeroCheque"
    const val lTipoEmisor = "lTipoEmisor"
    const val Emisor = "Emisor"
    const val nTipoAdelanto = "nTipoAdelanto"
    const val DteSaveInfo = "DteSaveInfo"
    const val cErrorWS = "cErrorWS"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$IdPrestamo INTEGER NOT NULL DEFAULT 0," +
                "$cFolio TEXT NOT NULL," +
                "$cCliente TEXT NOT NULL," +
                "$nPago REAL NOT NULL,," +
                "$IdMedioPago INTEGER NOT NULL," +
                "$cNumeroCheque TEXT NOT NULL," +
                "$lTipoEmisor INTEGER NOT NULL," +
                "$Emisor TEXT NOT NULL," +
                "$nTipoAdelanto INTEGER NOT NULL," +
                "$DteSaveInfo INTEGER DEFAULT (strftime('%s', 'now'))," +
                "$cErrorWS TEXT NOT NULL);"


    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region IntegrantesGrupoOffline
object IntegrantesGrupoOffline {
    const val TABLE_NAME = "IntegrantesGrupoOffline"

    const val Id = "Id"
    const val IdRef = "IdRef"
    const val IdTipoRef = "IdTipoRef"
    const val IdPrestamo = "IdPrestamo"
    const val IdGrupoSolidario = "IdGrupoSolidario"
    const val IdRelGrupoCliente = "IdRelGrupoCliente"
    const val IdCliente = "IdCliente"
    const val cNombre = "cNombre"
    const val nMonto = "nMonto"
    const val nGarantia = "nGarantia"
    const val IdRol = "IdRol"
    const val nMontoPago = "nMontoPago"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$IdRef INTEGER NOT NULL DEFAULT 0," +
                "$IdTipoRef INTEGER NOT NULL DEFAULT 0," +
                "$IdPrestamo INTEGER NOT NULL DEFAULT 0," +
                "$IdGrupoSolidario INTEGER NOT NULL DEFAULT 0," +
                "$IdRelGrupoCliente INTEGER NOT NULL DEFAULT 0," +
                "$IdCliente INTEGER NOT NULL DEFAULT 0," +
                "$cNombre TEXT NOT NULL," +
                "$nMonto REAL NOT NULL," +
                "$nGarantia REAL NOT NULL," +
                "$IdRol INT DEFAULT 0," +
                "$nMontoPago REAL NOT NULL);"


    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region OffLinePrestamoXCobrar
object OfflinePrestamoXCobrar {
    const val TABLE_NAME = "OffLinePrestamoXCobrar"

    const val Id = "Id"
    const val IdPrestamo = "IdPrestamo"
    const val cFolio = "cFolio"
    const val cCliente = "cCliente"
    const val nMontoTotal = "nMontoTotal"
    const val nSaldoPendiente = "nSaldoPendiente"
    const val DteSaveInfo = "DteSaveInfo"
    const val IdCliente = "IdCliente"
    const val IdClienteMoral = "IdClienteMoral"
    const val IdGrupoSolidario = "IdGrupoSolidario"
    const val cDireccion = "cDireccion"
    const val nEstadoRegistro = "nEstadoRegistro"
    const val nPos = "nPos"
    const val cGeoLocalizacion = "cGeoLocalizacion"
    const val cColor = "cColor"
    const val lManual = "lManual"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$IdPrestamo INTEGER NOT NULL," +
                "$cFolio TEXT NOT NULL," +
                "$cCliente TEXT NOT NULL," +
                "$nMontoTotal REAL NOT NULL," +
                "$nSaldoPendiente REAL NOT NULL," +
                "$DteSaveInfo INTEGER DEFAULT (strftime('%s', 'now'))," +
                "$IdCliente INTEGER NOT NULL DEFAULT 0," +
                "$IdClienteMoral INTEGER NOT NULL DEFAULT 0," +
                "$IdGrupoSolidario INTEGER NOT NULL DEFAULT 0," +
                "$cDireccion TEXT NOT NULL DEFAULT ''," +
                "$nEstadoRegistro INTEGER NOT NULL DEFAULT 0," +
                "$nPos INTEGER NOT NULL DEFAULT 1," +
                "$cGeoLocalizacion TEXT NOT NULL DEFAULT ''," +
                "$cColor TEXT NOT NULL DEFAULT ''," +
                "$lManual INTEGER NOT NULL DEFAULT 0);"


    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

//region OffLinePrestamoXOperador
object OfflinePrestamoXOperador {
    const val TABLE_NAME = "OffLinePrestamoXOperador"

    const val Id = "Id"
    const val IdPrestamo = "IdPrestamo"
    const val cFolio = "cFolio"
    const val cCliente = "cCliente"
    const val nMontoTotal = "nMontoTotal"
    const val nSaldoPendiente = "nSaldoPendiente"
    const val DteSaveInfo = "DteSaveInfo"
    const val IdCliente = "IdCliente"
    const val IdClienteMoral = "IdClienteMoral"
    const val IdGrupoSolidario = "IdGrupoSolidario"
    const val cDireccion = "cDireccion"
    const val nEstadoRegistro = "nEstadoRegistro"
    const val nPos = "nPos"
    const val cGeoLocalizacion = "cGeoLocalizacion"
    const val cColor = "cColor"

    const val Create =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$IdPrestamo INTEGER NOT NULL," +
                "$cFolio TEXT NOT NULL," +
                "$cCliente TEXT NOT NULL," +
                "$nMontoTotal REAL NOT NULL," +
                "$nSaldoPendiente REAL NOT NULL," +
                "$DteSaveInfo INTEGER DEFAULT (strftime('%s', 'now'))," +
                "$IdCliente INTEGER NOT NULL DEFAULT 0," +
                "$IdClienteMoral INTEGER NOT NULL DEFAULT 0," +
                "$IdGrupoSolidario INTEGER NOT NULL DEFAULT 0," +
                "$cDireccion TEXT NOT NULL DEFAULT ''," +
                "$nEstadoRegistro INTEGER NOT NULL DEFAULT 0," +
                "$nPos INTEGER NOT NULL DEFAULT 1," +
                "$cGeoLocalizacion TEXT NOT NULL DEFAULT ''," +
                "$cColor TEXT NOT NULL DEFAULT '');"

    const val Delete = "DROP TABLE IF EXISTS $TABLE_NAME;"
}
//endregion

const val SQL_CREATE_ENTRIES =
    Configuracion.Create +
    UbicacionGPS.Create +
    OfflineDisposicion.Create +
    Disposiciones.Create +
    OfflineCobranza.Create +
    Pagos.Create +
    IntegrantesGrupoOffline.Create +
    OfflinePrestamoXCobrar.Create +
    OfflinePrestamoXOperador.Create

const val SQL_DELETE_ENTRIES =
    Configuracion.Delete +
    UbicacionGPS.Delete +
    OfflineDisposicion.Delete +
    Disposiciones.Delete +
    OfflineCobranza.Delete +
    Pagos.Delete +
    IntegrantesGrupoOffline.Delete +
    OfflinePrestamoXCobrar.Delete +
    OfflinePrestamoXOperador.Delete