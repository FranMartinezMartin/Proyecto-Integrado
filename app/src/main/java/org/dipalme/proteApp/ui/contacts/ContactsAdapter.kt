package org.dipalme.proteApp.ui.contacts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.Extension
import org.dipalme.proteApp.extension.showContactInfo
import org.dipalme.proteApp.model.Contact
import org.jetbrains.anko.find

class ContactsAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsAdapter.ContactsHolder {
        return ContactsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsAdapter.ContactsHolder, position: Int) {
        val contact: Contact = contacts[position]

        holder.tvPosition.text = contact.occupation
        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.movil
    }

    class ContactsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var contact: Contact? = null

        init {
            v.setOnClickListener(this)
        }

        val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "Clic")
            v?.context?.showContactInfo(contact?.occupation!!, contact?.name!!, contact?.movil!!)
        }
    }
}