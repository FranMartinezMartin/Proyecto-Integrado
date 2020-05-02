package com.example.pi_v1.ui.services.boss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pi_v1.R
import com.example.pi_v1.model.Service
import java.util.*

class ModifyFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_modify, container, false)
        recycler = root.findViewById(R.id.recView)

        var fecha = Calendar.getInstance().time
        var service = Service(null, null, "Carrera Popular", "Almeria", fecha, "001", null, null, null, null)
        var service2 = Service(null, null, "Recogida alimentos", "Granada", fecha, "002", null, null, null, null)
        var service3 = Service(null, null, "Media Maraton", "Almeria", fecha, "003", null, null, null, null)
        var service4 = Service(null, null, "Carretillas", "Olula del Río", fecha, "004", null, null, null, null)

        var lista: List<Service> = listOf(service, service2, service3, service4)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = ModifyAdapter(lista)

        return root
    }

}
