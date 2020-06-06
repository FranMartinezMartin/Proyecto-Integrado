package org.dipalme.proteApp.ui.customDialog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_custom_assign_dialog.view.*
import org.dipalme.proteApp.R

class CustomAssignDialogAdapter(private val elements: List<String>) :
    RecyclerView.Adapter<CustomAssignDialogAdapter.ElementsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElementsHolder {
        return ElementsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_custom_assign_dialog, parent, false)
        )
    }
    private var volSaved: MutableList<String> = mutableListOf()

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: ElementsHolder, position: Int) {
        holder.textView.text = elements[position]

        for (element in volSaved) {
            if (element == elements[position])
                holder.checkBox.isChecked = true
        }
        holder.checkBox.setOnClickListener {
            if (it.checkBox.isChecked) {
                Log.w(" ##### TAG", elements[position])
                volSaved.add(elements[position])
            } else {
                for (element in volSaved) {
                    if (element == elements[position])
                        volSaved.remove(element)
                }
            }
            Log.w(" ##### TAGLIST", "$volSaved")
        }
    }

    fun getList(): MutableList<String>{
        return volSaved
    }

    class ElementsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val textView: TextView = itemView.findViewById(R.id.tvVolName)
    }
}