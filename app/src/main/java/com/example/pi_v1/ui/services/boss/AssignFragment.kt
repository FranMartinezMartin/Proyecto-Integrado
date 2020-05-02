package com.example.pi_v1.ui.services.boss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pi_v1.R
import com.example.pi_v1.model.Service
import java.util.*

class AssignFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_assign, container, false)
        recycler = root.findViewById(R.id.servicesRecView)
        var fecha = Calendar.getInstance().time

        var service = Service(null, null, "Carrera Popular", "Almeria", fecha, "001", null, null, null, null)
        var service2 = Service(null, null, "Recogida alimentos", "Granada", fecha, "002", null, null, null, null)

        var lista: List<Service> = listOf(service, service2)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = MyAdapter(lista)
        return root
    }

}
