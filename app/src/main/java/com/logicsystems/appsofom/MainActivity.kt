package com.logicsystems.appsofom

import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast





class MainActivity : AppCompatActivity() {
    lateinit var mPreviosItem: MenuItem

    var timer: SystemClock? = null
    lateinit var timer2: SystemClock
    var timerEstatus: SystemClock? = null
    var txtEstadoConexion = TextView(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navigationView: NavigationView = findViewById(R.id.nav_view)

        setSupportActionBar(findViewById(R.id.toolbar1))
//        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        setSupportActionBar(findViewById(R.id.toolbar1))
        val SAB = supportActionBar
        if (SAB != null) {
            SAB.title = "Bienvenido al CIB"
            SAB.setDisplayHomeAsUpEnabled(true)
            SAB.setDisplayShowHomeEnabled(true)
        }
//        val drawerToggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            findViewById(R.id.toolbar1),
//            R.string.ApplicationName,
//            R.string.drawer_close
//        )
        setSupportActionBar(findViewById(R.id.toolbar1))
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)

//        drawerLayout.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()
//        navigationView.setNavigationItemSelectedListener(this)

        val headerLayout: View = navigationView.getHeaderView(0)
        val nav_menu: TextView = headerLayout.findViewById(R.id.navheader_username)
        nav_menu.text = UserApp.StrNickName

        txtEstadoConexion = headerLayout.findViewById(R.id.navheader_email)
    }

    private fun VerificarConexion() {
        txtEstadoConexion.text = EstatusConexion()
    }

    fun EstatusConexion(): String {
        var sEst = ""
        if (Utils.isConnected(this)) {
            sEst = "Estado de Conexión: Online"
        } else {
            sEst = "Estado de Conexión: Offline"
        }
        return sEst
    }

    private fun enviarubicacionespendientes() {
//        configuramos el tiempo para hacer los llamados cada tiempo configurado (tiempo mins * 60000 = minutos en milisegs).
//        val nmilisegundos: Int = AppSofomConfigs.nMinUpdateinfo * 60000
//        timer2.elapsedRealtime = nmilisegundos
//        timer2.elapsed += ontimedeventpendientes
//        timer2.enabled = true
    }

    private fun ObtenerGPSAsincrono() {
        //EJECUTAMOS LA PRIMERA VEZ!
        Toast.makeText(this, "Geolocalizacion Iniciada...", Toast.LENGTH_LONG).show()
        EjecutarGeoLocalizacion()

        //CONFIGURAMOS EL TIEMPO PARA HACER LOS LLAMADOS CADA TIEMPO CONFIGURADO (TIEMPO MINS * 60000 = MINUTOS EN MILISEGS).
//        val nMilisegundos: Int = AppSofomConfigs.MinUpdateGPS * 60000
//        timer.Interval = nMilisegundos
//        timer.Elapsed += OnTimedEvent
//        timer.Enabled = true
    }

    private fun EjecutarGeoLocalizacion() {
//        SaveUbicacion = UbicacionTask(this)
//        val oConf = ClsGeoLocalizacion()
//        oConf.Contexto = this
//        SaveUbicacion.Execute(oConf)
    }
}