package org.dipalme.proteApp.ui.services.boss.assign

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.model.Vehicle
import org.dipalme.proteApp.model.Volunteer
import java.text.SimpleDateFormat

class BossAssignAdapter(private val services: List<Service>, context: Context) :
    RecyclerView.Adapter<BossAssignAdapter.ServicesHolder>() {

    private val contextPassed = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_services, parent, false
            )
        )
    }

    override fun getItemCount(): Int = services.size

    override fun onBindViewHolder(holder: ServicesHolder, position: Int) {
        val serv: Service = services[position]

        holder.tvServiceName.text = serv.name
        holder.placeName.text = serv.place

        val formatter = SimpleDateFormat("dd MMM yyyy")
        holder.date.text = formatter.format(serv.date)

        if (serv.volunteersList != null) {
            val adapter = ListVolunteerAdapter(contextPassed, serv.volunteersList)
            holder.volunteerList.adapter = adapter
        }
        if (serv.vehicleList != null) {
            val adapter = ListVehicleAdapter(contextPassed, serv.vehicleList)
            holder.vehiclesList.adapter = adapter
        }
        holder.vehiclesList.setOnClickListener { onClicAction() }
        holder.volunteerList.setOnClickListener { onClicAction() }
        holder.innerCardView.setOnClickListener { onClicAction() }
    }

    fun onClicAction() {
        Log.d("RecyclerView", "Clic -- Inner")

    }

    class ServicesHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        init {
            v.setOnClickListener(this)
        }

        val tvServiceName: TextView = itemView.findViewById(R.id.tvServiceName)
        val placeName: TextView = itemView.findViewById(R.id.tvPlaceName)
        val date: TextView = itemView.findViewById(R.id.date)
        val volunteerList: ListView = itemView.findViewById(R.id.volunteersList)
        val vehiclesList: ListView = itemView.findViewById(R.id.vehiclesList)
        val innerCardView = itemView.findViewById<CardView>(R.id.innerCardView)

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "Clic -- Outer")
        }
    }
}

class ListVolunteerAdapter(
    private val context: Context,
    private val dataSource: List<Volunteer>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return rowView
    }

    override fun getItem(position: Int): String? {
        return dataSource[position].name
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}

class ListVehicleAdapter(
    private val context: Context,
    private val dataSource: List<Vehicle>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return rowView
    }

    override fun getItem(position: Int): String? {
        return dataSource[position].id
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}

