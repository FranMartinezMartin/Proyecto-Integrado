package org.dipalme.proteApp.ui.services.boss.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.ui.services.boss.modify.BossModifyAdapter
import java.util.*

class BossModifyFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_boss_modify, container, false)
        recycler = root.findViewById(R.id.recView)

        var fecha = Calendar.getInstance().time
        var service = Service(null, null, "Carrera Popular", "Almeria", fecha, "001", null, null, null, null)
        var service2 = Service(null, null, "Recogida alimentos", "Granada", fecha, "002", null, null, null, null)
        var service3 = Service(null, null, "Media Maraton", "Almeria", fecha, "003", null, null, null, null)
        var service4 = Service(null, null, "Carretillas", "Olula del Río", fecha, "004", null, null, null, null)

        var lista: List<Service> = listOf(service, service2, service3, service4)
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter =
            BossModifyAdapter(lista)

        return root
    }

}
