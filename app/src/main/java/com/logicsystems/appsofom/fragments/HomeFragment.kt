package com.logicsystems.appsofom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.logicsystems.appsofom.R
import com.logicsystems.appsofom.datos.ClsGenericaFragments

class HomeFragment : ClsGenericaFragments() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        config.LoadConfig(this.requireActivity())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clientes, container, false)
    }
}