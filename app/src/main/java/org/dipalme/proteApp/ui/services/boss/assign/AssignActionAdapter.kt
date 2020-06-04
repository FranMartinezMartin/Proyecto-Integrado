package org.dipalme.proteApp.ui.services.boss.assign

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.model.Vehicle
import org.dipalme.proteApp.model.Volunteer
import java.text.SimpleDateFormat

class AssignActionAdapter(private val services: List<Service>, context: Context) :
    RecyclerView.Adapter<AssignActionAdapter.ServicesHolder>() {

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

        holder.tvServiceName.text = service.name
        holder.placeName.text = service.place

        val formatter = SimpleDateFormat("dd MMM yyyy")
        holder.date.text = formatter.format(service.date)

        /**
        val adapter = ListVolunteerAdapter(contextPassed, service.volunteersList)
        holder.volunteerList.adapter = adapter

        val adapter = ListVehicleAdapter(contextPassed, service.vehicleList)
        holder.vehiclesList.adapter = adapter
         **/

        holder.frame.setOnClickListener {
            val i = Intent(contextPassed, BossAssignActivity::class.java)
            i.putExtra("service id", service.id)
            ContextCompat.startActivity(contextPassed, i, null)
        }
    }

    class ServicesHolder(v: View, context: Context) : RecyclerView.ViewHolder(v) {
        val tvServiceName: TextView = itemView.findViewById(R.id.tvServiceName)
        val placeName: TextView = itemView.findViewById(R.id.tvPlaceName)
        val date: TextView = itemView.findViewById(R.id.date)
        val volunteerList: ListView = itemView.findViewById(R.id.volunteersList)
        val vehiclesList: ListView = itemView.findViewById(R.id.vehiclesList)
        val frame = itemView.findViewById<View>(R.id.frame)
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
}





