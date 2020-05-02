package com.example.pi_v1.ui.services.boss

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pi_v1.R
import com.example.pi_v1.model.Service
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class MyAdapter(private val services: List<Service>) :
    RecyclerView.Adapter<MyAdapter.ServicesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_services, parent, false
            )
        )
    }

    override fun getItemCount(): Int = services.size

    override fun onBindViewHolder(holder: MyAdapter.ServicesHolder, position: Int) {
        val serv: Service = services[position]

        holder.tvServiceName.text = serv.name
        holder.placeName.text = serv.name
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

