package org.dipalme.ProteApp.ui.services.boss

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.ProteApp.R
import org.dipalme.ProteApp.model.Service

class BossAssignAdapter(private val services: List<Service>) :
    RecyclerView.Adapter<BossAssignAdapter.ServicesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BossAssignAdapter.ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_services, parent, false
            )
        )
    }

    override fun getItemCount(): Int = services.size

    override fun onBindViewHolder(holder: BossAssignAdapter.ServicesHolder, position: Int) {
        val serv: Service = services[position]

        holder.tvServiceName.text = serv.name
        holder.placeName.text = serv.place
    }

    class ServicesHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var service: Service? = null

        init {
            v.setOnClickListener(this)
        }

        val tvServiceName: TextView = itemView.findViewById(R.id.tvServiceName)
        val placeName: TextView = itemView.findViewById(R.id.tvPlaceName)
        val date: TextView = itemView.findViewById(R.id.date)
        val volunteerList: ListView = itemView.findViewById(R.id.volunteersList)
        val vehiclesList: ListView = itemView.findViewById(R.id.vehiclesList)

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "Clic")
        }
    }
}

