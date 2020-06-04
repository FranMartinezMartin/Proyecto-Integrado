package org.dipalme.proteApp.ui.customDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R

class CustomAssignDialogAdapter(private val elements: List<String>) :
    RecyclerView.Adapter<CustomAssignDialogAdapter.ElementsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElementsHolder {
        return ElementsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_custom_assign_dialog, parent, false)
        )
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: ElementsHolder, position: Int) {
        val element: String = elements[position]

        holder.checkBox.text = element
    }

    class ElementsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}