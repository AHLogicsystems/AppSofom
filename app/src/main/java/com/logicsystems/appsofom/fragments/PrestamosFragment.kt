package com.logicsystems.appsofom.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.logicsystems.appsofom.ListaCreditosXCobrarActivity
import com.logicsystems.appsofom.PrestamosActivity
import com.logicsystems.appsofom.R
import com.logicsystems.appsofom.datos.AppSofomConfigs
import com.logicsystems.appsofom.datos.Service
import com.logicsystems.appsofom.datos.SolicitudCredito
import kotlinx.android.synthetic.main.item_subprestamo.view.*

class PrestamosFragment : Fragment() {
    val service = Service()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_prestamos, container, false)
        val listDetallado = view.findViewById<GridView>(R.id.ContenlistView)

        val datos = arrayListOf<SubMenuPrestamos>()
        datos.add(SubMenuPrestamos("Solicitud Crédito", R.drawable.img_solicitud ))
        datos.add(SubMenuPrestamos("Renovar Crédito", R.drawable.img_renovar ))
        datos.add(SubMenuPrestamos("Reestructurar Crédito", R.drawable.img_reestructura ))
        datos.add(SubMenuPrestamos("Entregar Crédito", R.drawable.img_entregar ))
        datos.add(SubMenuPrestamos("Cobrar Crédito", R.drawable.img_cobrar ))
        datos.add(SubMenuPrestamos("Cobro de Accesorios Crédito", R.drawable.img_creditos_x_cobrar ))


        if (AppSofomConfigs.NameEntorno.uppercase() == "CHANGE2131"){
            datos.add(SubMenuPrestamos("Lista de Créditos por cobrar", R.drawable.img_creditos_x_cobrar ))
        }

        listDetallado.adapter = AdaptadorSubMenuPrestamo(this.requireActivity(), datos)

        listDetallado.setOnItemClickListener { parent, view, position, id ->
            if (!AppSofomConfigs.isOnLine(this.requireActivity()) &&
                (position == SolicitudCredito.RENOVAR.ordinal ||
                 position == SolicitudCredito.REESTRUCTURAR.ordinal ||
                 position == SolicitudCredito.SOLICITUD.ordinal ||
                 position == SolicitudCredito.ENTREGAR.ordinal)){
                service.alertasError(this.requireActivity(), "Esta opción solo se encuentra disponible en la modalidad en línea")
            }
            else{
                var intent = Intent(this.activity, PrestamosActivity::class.java)
                if (position == SolicitudCredito.LISTACREDITOXCOBRAR.ordinal){
                    intent = Intent(this.activity, ListaCreditosXCobrarActivity::class.java)
                }
                intent.putExtra("TypeSearch", position)
                startActivity(intent)
            }
        }

        return view
    }
}

open class SubMenuPrestamos( var StrTitulo: String, var IntImagen: Int )

open class AdaptadorSubMenuPrestamo(private val cContext: Activity, private val datos: ArrayList<SubMenuPrestamos>) :
    ArrayAdapter<SubMenuPrestamos>(cContext, 0, datos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(cContext).inflate(R.layout.item_subprestamo, parent, false)

        val subMenuPrestamo = datos[position]
        layout.tituloLista.text = subMenuPrestamo.StrTitulo
        layout.imgPrestamo.setImageResource(subMenuPrestamo.IntImagen)

        return layout
    }
}