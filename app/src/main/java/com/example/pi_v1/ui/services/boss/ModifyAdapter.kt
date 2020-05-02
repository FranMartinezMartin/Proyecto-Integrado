package com.example.pi_v1.ui.services.boss

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pi_v1.R
import com.example.pi_v1.model.Service


class ModifyAdapter(private val services: List<Service>) :
    RecyclerView.Adapter<ModifyAdapter.ServicesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_services_modify, parent, false
            )
        )
    }

    override fun getItemCount(): Int = services.size

    override fun onBindViewHolder(holder: ServicesHolder, position: Int) {
        val serv: Service = services[position]

        holder.ServiceName.text = serv.name
        holder.placeName.text = serv.place
    }

    class ServicesHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var service: Service? = null

        init {
            v.setOnClickListener(this)
        }

        val ServiceName: TextView = itemView.findViewById(R.id.tvServiceName)
        val placeName: TextView = itemView.findViewById(R.id.tvPlaceName)


        override fun onClick(v: View?) {
            Log.d("RecyclerView", "Clic")
        }
    }

}
