package com.logicsystems.appsofom.Adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.logicsystems.appsofom.R

open class GenericBasicAdapter<T>(private val _context: Context, private val list: ArrayList<T>, private val usarSubtitulo: Boolean) : ArrayAdapter<T>(_context, R.layout.basic_listitem, R.id.txtBasicTitle, list) where T : IBasicListElement {

    override fun setDropDownViewResource(resource: Int) {
        super.setDropDownViewResource(R.layout.basic_listitem)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var item: View? = convertView
        val holder: BasicViewHolder

        val inflater = LayoutInflater.from(_context)
        item = inflater.inflate(R.layout.basic_listitem, null)

        holder = BasicViewHolder()
        holder.titulo = item.findViewById(R.id.txtBasicTitle)
        holder.subtitulo = item.findViewById(R.id.txtBasicSubtitle)
        holder.subtitulo2 = item.findViewById(R.id.txtBasicSubtitle2)
        if (!usarSubtitulo){
            holder.subtitulo.visibility = View.GONE
        }
        item.tag = holder

        val titulo = list[position].getDescription()
        val subtitulo = list[position].getDetailDescription()
        val subtitulo2 = list[position].getDetailDescription2()

        holder.titulo.text = titulo
        if (usarSubtitulo){
            holder.subtitulo.text = subtitulo
        }
        if (!usarSubtitulo || subtitulo2.trim() == ""){
            holder.subtitulo2.visibility = View.GONE
            holder.subtitulo2.text = ""
        }
        else{
            holder.subtitulo2.text = subtitulo2
            holder.subtitulo2.visibility = View.VISIBLE
            holder.subtitulo2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        }
        return item
    }

    fun getItemPosition(element: T) : Int{
        for (i in 1 until list.count()){
            val e: T = list[i]
            if ((element.getId() > 0 && element.getId() == e.getId()) || element.getId() == 0 && element.getDescription() != "" && element.getDescription() == e.getDescription()){
                return i
            }
        }
        return -1
    }

    fun getItemAtPosition(position: Int): T{
        return list[position]
    }

    fun getItemAtDescripcion(descripcion: String): T{
        val element: T = list.filter { it.getDescription() == descripcion }[0]
        element.init(-1, "", "", "")
        return element
    }
}
class BasicViewHolder
{
    lateinit var titulo: TextView
    lateinit var subtitulo: TextView
    lateinit var subtitulo2: TextView

}