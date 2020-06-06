package org.dipalme.proteApp.ui.services.boss.assign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.model.Vehicle
import org.dipalme.proteApp.model.Volunteer
import java.text.SimpleDateFormat
import java.util.*

class BossAssignAdapter(private val services: List<Service>, context: Context) :
    RecyclerView.Adapter<BossAssignAdapter.ServicesHolder>() {

    private val contextPassed = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_services, parent, false),
            contextPassed
        )
    }

    override fun getItemCount(): Int = services.size

    override fun onBindViewHolder(holder: ServicesHolder, position: Int) {
        val service = services[position]

        holder.tvServiceName.text = service.name.toString()
        holder.placeName.text = service.place.toString()

        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        holder.date.text = formatter.format(service.date!!)

        holder.frame.setOnClickListener {
            val i = Intent(contextPassed, BossAssignActivity::class.java)
            i.putExtra("serviceID", service.id.toString())
            startActivity(contextPassed, i, null)
        }
    }

    class ServicesHolder(v: View, context: Context) : RecyclerView.ViewHolder(v) {
        val tvServiceName: TextView = itemView.findViewById(R.id.tvServiceName)
        val placeName: TextView = itemView.findViewById(R.id.tvPlaceName)
        val date: TextView = itemView.findViewById(R.id.date)
        val frame = itemView.findViewById<View>(R.id.frame)
    }
}