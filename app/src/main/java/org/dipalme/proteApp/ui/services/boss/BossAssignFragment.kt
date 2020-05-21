package org.dipalme.proteApp.ui.services.boss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service
import java.util.*

class BossAssignFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_boss_assign, container, false)
        recycler = root.findViewById(R.id.servicesRecView)
        var fecha = Calendar.getInstance().time

        var service = Service(null, null, "Carrera Popular", "Almeria", fecha, "001", null, null, null, null)
        var service2 = Service(null, null, "Recogida alimentos", "Granada", fecha, "002", null, null, null, null)

        var lista: List<Service> = listOf(service, service2)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = BossAssignAdapter(lista)
        return root
    }

}