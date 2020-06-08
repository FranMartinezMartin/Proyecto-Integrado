package org.dipalme.proteApp.ui.services.volunteer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service

class DoneServicesAdapter(
    private val services: List<Service>,
    context: Context
) :
    RecyclerView.Adapter<DoneServicesAdapter.ServicesHolder>() {

    private val contextPassed = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_services_compact, parent, false
            )
        )
    }

    override fun getItemCount(): Int = services.size

    override fun onBindViewHolder(holder: ServicesHolder, position: Int) {
        val service = services[position]

        holder.tvServiceName.text = service.name.toString()
        holder.placeName.text = service.place.toString()
    }

    class ServicesHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvServiceName: TextView = itemView.findViewById(R.id.tvServiceName)
        val placeName: TextView = itemView.findViewById(R.id.tvPlaceName)

    }
}