package org.dipalme.proteApp.ui.services.volunteer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.ui.services.boss.assign.BossAssignAdapter

class VolunteerServicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_volunteer_services, container, false)

        var recNext = root.findViewById<RecyclerView>(R.id.recNextServices)
        var listaNext: List<Service> = listOf()
        recNext.layoutManager = LinearLayoutManager(root.context)
        recNext.adapter =
            BossAssignAdapter(listaNext, root.context)

        var recDone = root.findViewById<RecyclerView>(R.id.recDoneServices)
        var listaDone: List<Service> = listOf()
        recDone.layoutManager = LinearLayoutManager(root.context)
        recDone.adapter =
            BossAssignAdapter(listaDone, root.context)

        return root
    }

}
