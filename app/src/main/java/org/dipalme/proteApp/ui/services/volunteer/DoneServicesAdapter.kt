package org.dipalme.proteApp.ui.services.volunteer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service

class DoneServicesAdapter(private val services: List<Service>) :
    RecyclerView.Adapter<DoneServicesAdapter.ServicesHolder>() {

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

    }

    class ServicesHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var service: Service? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "Clic")
        }
    }

}