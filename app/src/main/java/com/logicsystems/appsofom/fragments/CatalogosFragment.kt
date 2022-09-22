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
import com.logicsystems.appsofom.*
import com.logicsystems.appsofom.datos.AppSofomConfigs
import com.logicsystems.appsofom.datos.Catalogos
import com.logicsystems.appsofom.datos.REQUEST_CODES
import com.logicsystems.appsofom.datos.Service
import kotlinx.android.synthetic.main.item_subprestamo.view.*


class CatalogosFragment : Fragment() {
    val service = Service()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalogos, container, false)
        val listDetallado = view.findViewById<GridView>(R.id.ContenlistCatView)

        val datos = arrayListOf<SubMenuClientes>()
        datos.add(SubMenuClientes("Clientes", R.drawable.img_clientes ))
        datos.add(SubMenuClientes("Grupos Solidarios", R.drawable.img_grupo ))
        datos.add(SubMenuClientes("Referencias", R.drawable.img_referencias2 ))
        datos.add(SubMenuClientes("Domicilios", R.drawable.img_domicilios ))
        datos.add(SubMenuClientes("Negocios", R.drawable.img_negocios ))
        datos.add(SubMenuClientes("PLD", R.drawable.img_pld ))
        listDetallado.adapter = AdaptadorSubMenuCliente(this.requireActivity(), datos)

        listDetallado.setOnItemClickListener{ parent, view, position, id ->
            if (AppSofomConfigs.isOnLine(this.requireActivity())){
                val intent = when(position){
                    Catalogos.DOMICILIOS.ordinal -> {
                        Intent(this.requireActivity(), DireccionesActivity::class.java)
                            .putExtra("action", REQUEST_CODES.LIST_DOMICILIO.valor)
                    }
                    Catalogos.NEGOCIOS.ordinal -> {
                        Intent(this.requireActivity(), NegocioActivity::class.java)
                            .putExtra("TypeSearch", REQUEST_CODES.SEARCH_NEGOCIO_LIST.valor)
                    }
                    Catalogos.GRUPOSSOLIDARIOS.ordinal -> {
                        Intent(this.requireActivity(), GrupoSolidarioActivity::class.java)
                            .putExtra("TypeSearch", REQUEST_CODES.SEARCH_GRUPO_SOLIDARIO_LIST.valor)
                    }
                    else -> {
                        Intent(this.requireActivity(), ClientesActivity::class.java)
                            .putExtra("TypeSearch", position)
                    }
                }
                this.startActivity(intent)
            }
            else{
                service.alertasError(this.requireActivity(), "Esta opción solo se encuentra disponible en la modalidad en línea")
            }
        }

        return view
    }
}

class SubMenuClientes(var StrTitulo: String, var IntImagen: Int)

open class AdaptadorSubMenuCliente(private val cContext: Activity, private val datos: ArrayList<SubMenuClientes>) :
    ArrayAdapter<SubMenuClientes>(cContext, 0, datos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(cContext).inflate(R.layout.item_subprestamo, parent, false)

        val subMenuPrestamo = datos[position]
        layout.tituloLista.text = subMenuPrestamo.StrTitulo
        layout.imgPrestamo.setImageResource(subMenuPrestamo.IntImagen)

        return layout
    }
}