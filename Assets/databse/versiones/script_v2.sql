--Tabla que guarda la configuración.
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

--Tabla que guarda las ubicaciones GPS cuando no exista conexión a Internet
CREATE TABLE IF NOT EXISTS UbicacionGPS (
    Id              INTEGER PRIMARY KEY AUTOINCREMENT,
    cUbicacion      TEXT NOT NULL,
    DteSave         INTEGER DEFAULT (strftime('%s', 'now'))
);

--Tabla que guarda información relacionada con las disposiciones para poder utilizarlas cuando no exista conexión a Internet
CREATE TABLE IF NOT EXISTS OffLineDisposicion (
    Id					INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPrestamo			INTEGER NOT NULL,
    cFolio				TEXT NOT NULL,  
    cCliente			TEXT NOT NULL,  
    nMontoTotal			REAL NOT NULL,
    nSaldoPendiente		REAL NOT NULL,
    DteSaveInfo			INTEGER DEFAULT (strftime('%s', 'now')),
	IdCliente			INTEGER NOT NULL DEFAULT 0,
	IdClienteMoral		INTEGER NOT NULL DEFAULT 0,
	IdGrupoSolidario	INTEGER NOT NULL DEFAULT 0
);

--Tabla que guarda información de las disposiciones realizadas cuando no exista conexión a Internet
CREATE TABLE IF NOT EXISTS Disposiciones (
    Id					INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPrestamo			REAL NOT NULL,
    cFolio				TEXT NOT NULL,
    cCliente			TEXT NOT NULL,
    nMontoTotal			REAL NOT NULL,
    nSaldoPendiente		REAL NOT NULL,
    nMontoDisp			REAL NOT NULL,
    DteSaveInfo			INTEGER DEFAULT (strftime('%s', 'now')),
    cErrorWS			TEXT NOT NULL
);

--Tabla que guarda información relacionada con la cobranza para poder utilizarlas cuando no exista conexión a Internet
CREATE TABLE IF NOT EXISTS OffLineCobranza (
    Id					INTEGER PRIMARY KEY AUTOINCREMENT,
    IdPrestamo			INTEGER NOT NULL,
    cFolio				TEXT NOT NULL,  
    cCliente			TEXT NOT NULL,  
    nPendiente			REAL NOT NULL,
    nAlDia				REAL NOT NULL,
    nLiquidar			REAL NOT NULL,
    DteSaveInfo			INTEGER DEFAULT (strftime('%s', 'now')),
	IdCliente			INTEGER NOT NULL DEFAULT 0,
	IdClienteMoral		INTEGER NOT NULL DEFAULT 0,
	IdGrupoSolidario	INTEGER NOT NULL DEFAULT 0
);

--Tabla que guarda información de los cobros realizados cuando no exista conexión a Internet
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
CREATE TABLE IF NOT EXISTS IntegrantesGrupoOffline (
    Id						INTEGER PRIMARY KEY AUTOINCREMENT,
	IdRef					INTEGER NOT NULL DEFAULT 0,
	IdTipoRef				INTEGER NOT NULL DEFAULT 0,--1 Disposiciones, 2 - Cobranza, 3 - Pagos Prestamo
	IdPrestamo				INTEGER NOT NULL DEFAULT 0,
	IdGrupoSolidario		INTEGER NOT NULL DEFAULT 0,
	IdRelGrupoCliente		INTEGER NOT NULL DEFAULT 0,
    IdCliente				INTEGER NOT NULL DEFAULT 0,
	cNombre					TEXT NOT NULL DEFAULT '',
	nMonto					REAL NOT NULL DEFAULT 0,
	nGarantia				REAL NOT NULL DEFAULT 0,
	IdRol					INT DEFAULT 0,
	nMontoPago				REAL NOT NULL DEFAULT 0
);