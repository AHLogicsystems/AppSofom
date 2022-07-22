CREATE TABLE IF NOT EXISTS Configuracion (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT,
    cEntorno        TEXT NOT NULL,
    cEmpresa        TEXT NOT NULL,
    nMinUpdateGPS   INTEGER DEFAULT 0,
    nMinUpdateInfo  INTEGER DEFAULT 0,
    cLoginUser      TEXT NOT NULL,
    cLoginPass      TEXT NOT NULL,
    cOperador       TEXT NOT NULL,
    cInfoTicket     TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS UbicacionGPS (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT,
    cUbicacion      TEXT NOT NULL,
    DteSave         INTEGER DEFAULT (strftime('%s', 'now'))
);
CREATE TABLE IF NOT EXISTS OffLineDisposicion (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPrestamo      INTEGER NOT NULL,
    cFolio          TEXT NOT NULL,  
    cCliente        TEXT NOT NULL,  
    nMontoTotal     REAL NOT NULL,
    nSaldoPendiente REAL NOT NULL,
    DteSaveInfo     INTEGER DEFAULT (strftime('%s', 'now'))
);
CREATE TABLE IF NOT EXISTS Disposiciones (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPrestamo      REAL NOT NULL,
    cFolio          TEXT NOT NULL,
    cCliente        TEXT NOT NULL,
    nMontoTotal     REAL NOT NULL,
    nSaldoPendiente REAL NOT NULL,
    nMontoDisp      REAL NOT NULL,
    DteSaveInfo     INTEGER DEFAULT (strftime('%s', 'now')),
    cErrorWS        TEXT NOT NULL                        
);
CREATE TABLE IF NOT EXISTS OffLineCobranza (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPrestamo      INTEGER NOT NULL,
    cFolio          TEXT NOT NULL,  
    cCliente        TEXT NOT NULL,  
    nPendiente      REAL NOT NULL,
    nAlDia          REAL NOT NULL,
    nLiquidar       REAL NOT NULL,
    DteSaveInfo     INTEGER DEFAULT (strftime('%s', 'now'))
);
CREATE TABLE IF NOT EXISTS Pagos (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPrestamo      INTEGER NOT NULL,
    cFolio          TEXT NOT NULL,  
    cCliente        TEXT NOT NULL,  
    nPago           REAL NOT NULL,
    IdMedioPago     INTEGER NOT NULL,
    cNumeroCheque   TEXT NOT NULL,  
    lTipoEmisor     INTEGER NOT NULL,
    Emisor          TEXT NOT NULL,
    nTipoAdelanto   INTEGER NOT NULL,
    DteSaveInfo     INTEGER DEFAULT (strftime('%s', 'now')),
    cErrorWS        TEXT NOT NULL
);