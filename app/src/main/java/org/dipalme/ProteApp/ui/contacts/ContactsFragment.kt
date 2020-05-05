package org.dipalme.ProteApp.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import org.dipalme.ProteApp.R
import org.dipalme.ProteApp.ui.home.HomeViewModel

class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactsViewModel =
            ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

}
