package com.logicsystems.appsofom

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_subprestamo.view.*


class PrestamosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_prestamos, container, false)
        val listDetallado = view.findViewById<ListView>(R.id.ContenlistView)

        val datos = arrayListOf<SubMenuPrestamos>()
        datos.add(SubMenuPrestamos().apply { IntIdMenu = 1; StrTitulo = "Solicitud Crédito"; R.drawable.img_solicitud })
        datos.add(SubMenuPrestamos().apply { IntIdMenu = 2; StrTitulo = "Renovar Crédito"; R.drawable.img_renovar })
        datos.add(SubMenuPrestamos().apply { IntIdMenu = 3; StrTitulo = "Reestructurar Crédito"; R.drawable.img_reestructura })
        datos.add(SubMenuPrestamos().apply { IntIdMenu = 4; StrTitulo = "Entregar Crédito"; R.drawable.img_entregar })
        datos.add(SubMenuPrestamos().apply { IntIdMenu = 5; StrTitulo = "Cobrar Crédito"; R.drawable.img_cobrar })
        datos.add(SubMenuPrestamos().apply { IntIdMenu = 6; StrTitulo = "Cobro de Accesorios Crédito"; R.drawable.img_creditos_x_cobrar })

        val adapter = AdaptadorSubMenuPrestamo(this.activity, datos)

        listDetallado.adapter = adapter

        return view
    }
}

open class SubMenuPrestamos{
    var IntIdMenu = 0
    var StrTitulo = ""
    var IntImagen = 0

    @SuppressLint("NotConstructor")
    fun SubMenuPrestamos (IdMenu: Int, Titulo: String, Imagen: Int ){
        this.IntIdMenu = IdMenu
        this.StrTitulo = Titulo
        this.IntImagen = Imagen
    }
}

open class AdaptadorSubMenuPrestamo(private val cContext: Activity?, private val datos: ArrayList<SubMenuPrestamos>) :
    ArrayAdapter<SubMenuPrestamos>(cContext!!, 0, datos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(cContext).inflate(R.layout.item_subprestamo, parent, false)

//        var item : View? = convertView
//        if (item == null){
//            val inflater: LayoutInflater = cContext.layoutInflater
//            item = inflater.inflate(R.layout.item_subprestamo, null)
//
//            item.txtListPrestamo.text = datos[position].StrTitulo
//            item.imgPrestamo.setImageResource(datos[position].IntImagen)
//        }

        val subMenuPrestamo = datos[position]
        layout.txtListPrestamo.text = subMenuPrestamo.StrTitulo
        layout.imgPrestamo.setImageResource(subMenuPrestamo.IntImagen)

        return layout
    }

    fun GetItemAtPosition(position: Int) : SubMenuPrestamos{
        return datos[position]
    }
}

class ViewHolderSubMenuPrestamo : Any(){
    lateinit var Titulo : TextView
    lateinit var Imagen : ImageView
}