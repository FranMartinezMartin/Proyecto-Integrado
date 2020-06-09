@file:Suppress("DEPRECATION")

package org.dipalme.proteApp.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showErrorDialog

class ContactsFragment : Fragment() {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var recycler: RecyclerView
    private lateinit var loading: ViewStub
    private lateinit var thisview: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_contacts, container, false)
        recycler = root.findViewById(R.id.recycler)
        loading = root.findViewById(R.id.vsLoading)
        loading.visibility = View.VISIBLE
        thisview = root
        initViewModel()
        viewModel.loadRecicler(recycler, root.context)
        return root
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ContactViewModelFactory())
            .get(ContactsViewModel::class.java)

        viewModel.navigationEvent.observe(this, Observer {
            loading.visibility = View.GONE
        })

        viewModel.errorEvent.observe(this, Observer {
            thisview.context.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })
    }
}
