package com.logicsystems.appsofom

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.logicsystems.appsofom.datos.ClsGenerica
import com.logicsystems.appsofom.datos.Utils


class MainActivity : ClsGenerica() , NavigationView.OnNavigationItemSelectedListener{
    private  lateinit var drawer: DrawerLayout
    private  lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toogle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

//        val navigationView: NavigationView = findViewById(R.id.nav_view)

//        setSupportActionBar(findViewById(R.id.toolbar1))
//        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
//        setSupportActionBar(findViewById(R.id.toolbar1))
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
//        setSupportActionBar(findViewById(R.id.toolbar1))
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)

//        drawerLayout.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()
//        navigationView.setNavigationItemSelectedListener(this)

        val headerLayout: View = navigationView.getHeaderView(0)
//        val nav_menu: TextView = headerLayout.findViewById(R.id.navheader_username)
//        nav_menu.text = UserApp.StrNickName

//        txtEstadoConexion = headerLayout.findViewById(R.id.navheader_email)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_one -> Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two -> Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three -> Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show()
        }

        drawer.closeDrawer(GravityCompat.START)
        return  true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return  true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun VerificarConexion() {
//        txtEstadoConexion.text = EstatusConexion()
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