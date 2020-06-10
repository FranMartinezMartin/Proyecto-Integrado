package org.dipalme.proteApp.ui.services.volunteer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R

class NextSubAdapter(private val list: MutableList<String>) :
    RecyclerView.Adapter<NextSubAdapter.ServicesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_view, parent, false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ServicesHolder, position: Int) {
        val data = list[position]
            holder.data.text = data
    }

    class ServicesHolder(v: View) : RecyclerView.ViewHolder(v) {
        val data: TextView = itemView.findViewById(R.id.tvListView)
    }
}