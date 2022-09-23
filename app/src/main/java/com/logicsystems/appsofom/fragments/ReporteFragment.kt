package com.logicsystems.appsofom.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import com.logicsystems.appsofom.ConfigActivity
import com.logicsystems.appsofom.R
import com.logicsystems.appsofom.testahActivity
import kotlinx.android.synthetic.main.activity_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReporteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReporteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_reporte, container, false)

        val view :View = inflater.inflate(R.layout.fragment_reporte, container, false)
        //val listDetallado = view.findViewById<GridView>(R.id.ContenlistView)
        val btnIr = view.findViewById<Button>(R.id.btnIr)

        btnIr.setOnClickListener {
            IrOtro()

        }

        return view
    }

  fun IrOtro(){
      //val intent = Intent(this,  ConfigActivity::class.java)
      val intent = Intent(this.activity, testahActivity::class.java)
      this.startActivity(intent)
  }
}