package com.logicsystems.appsofom.datos

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.logicsystems.appsofom.ClsCapaNegocios
import java.util.*


class ClsGeoLocalizacionRegs{
    var oRegistros = arrayListOf<ClsGeoLocalizacion>()
}

open class ClsGeoLocalizacion : ClsGenerica(), LocationListener {
    val OCapa = ClsCapaNegocios()

    var locationManager: LocationManager = Context.LOCATION_SERVICE as LocationManager
    var locationProvider: String = ""
    lateinit var location: Location

    private val TAG: String = "location"

    //region Variables
    private lateinit var context: Context
    val contexto: Context = this.context

    val Problema: String = this.StrProblema

    private lateinit var strGeoLocalizacion: String
    val GeoLocalizacion: String = strGeoLocalizacion

    private var nID: Int = 0
    val ID: Int = nID

    private lateinit var DteUbicacion: Date
    val FechaUbicacion: Date = DteUbicacion
    //endregion

    private lateinit var oReg: ClsGeoLocalizacionRegs

    init{
        this.Limpiar()
        this.nID = 0
        this.strGeoLocalizacion = ""
        this.DteUbicacion = Date(Long.MIN_VALUE)
        oReg = ClsGeoLocalizacionRegs()
        oReg.oRegistros.clear()
    }

    fun getLocation(): Boolean{
        val blnReturn = false

        locationProvider = LocationManager.GPS_PROVIDER

        Log.d("tag", "provider..$locationProvider.")

        if(Looper.myLooper() == null){
            Looper.prepare()
        }

//        locationManager.requestLocationUpdates(locationProvider, 0, 0, this)

        if (locationManager.isProviderEnabled(locationProvider)){
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return true
            }
            location = locationManager.getLastKnownLocation(locationProvider)!!
        }
        return blnReturn
    }

    //region NO SE UTILIZA, PERO ES UNA INTERFACE DE LA CLASE
    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }
    //endregion

//    fun ConsumirWebService(sGeoLocalizacion: String, DteFecha: Date){
//        service.Url = AppSofomConfigs().URLWSFull()
//
////        val StrIMEI: String = AppSofomConfigs().getIMEI(context)
//        if (!OCapa.getXMLSetUbicacionesGPS(sGeoLocalizacion, DteFecha)){
//            this.StrProblema = "Error en el XML: " + OCapa.StrProblema
//        }
//        else{
//            this.Guardar()
//
//            val strXML: String = OCapa.StrXMLReturn.replace("AppUbicacionesGPS", "Ubicaciones")
//
//            service.MultiWebMethodsAppAsync(null,
//                AppSofomConfigs().cNameEmpresa, "AppSofom", "SETUBICACIONESGPS", strXML, UserApp.StrUser, UserApp.StrPass, "")
//        }
//    }

//    fun ConsumirWebService(aGeoLocalizacion: List<UbicacionGPS>){
//        servicePendiente.Url = AppSofomConfigs().URLWSFull()
//
////        val StrIMEI: String = AppSofomConfigs().getIMEI(context)
//
//        if (!OCapa.getXMLSetUbicacionesGPS(aGeoLocalizacion)){
//            this.StrProblema = "Error en el XML: " + OCapa.StrProblema
//        }
//        else{
//            val strXML: String = OCapa.StrXMLReturn.replace("AppUbicacionesGPS", "Ubicaciones")
//
//            servicePendiente.MultiWebMethodsAppAsync(null,
//                AppSofomConfigs().cNameEmpresa, "AppSofom", "SETUBICACIONESGPS", strXML, UserApp.StrUser, UserApp.StrPass, "")
//        }
//    }
}