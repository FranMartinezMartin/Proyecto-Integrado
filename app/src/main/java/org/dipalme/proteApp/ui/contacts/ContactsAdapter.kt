package org.dipalme.proteApp.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Contact

class ContactsAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsHolder {
        return ContactsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsHolder, position: Int) {
        val contact: Contact = contacts[position]
        holder.tvPosition.text = contact.occupation
        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.movil
    }

    class ContactsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
    }
}