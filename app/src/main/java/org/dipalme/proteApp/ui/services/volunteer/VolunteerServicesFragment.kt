@file:Suppress("DEPRECATION")

package org.dipalme.proteApp.ui.services.volunteer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showErrorDialog
import org.dipalme.proteApp.model.Service

class VolunteerServicesFragment : Fragment() {

    private lateinit var nextRecycler: RecyclerView
    private lateinit var doneRecycler: RecyclerView
    private lateinit var loading: ViewStub
    private lateinit var viewModel: VolunteerServiceViewModel
    private lateinit var thisView: View
    private var nextList = mutableListOf<Service>()
    private var doneList = mutableListOf<Service>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisView = inflater.inflate(R.layout.fragment_volunteer_services, container, false)
        loading = thisView.findViewById(R.id.vsLoading)
        nextRecycler = thisView.findViewById(R.id.recNextServices)
        doneRecycler = thisView.findViewById(R.id.recDoneServices)
        loading.visibility = View.VISIBLE
        viewModel = VolunteerServiceViewModel()
        initViewModel()
        viewModel.fillNextRecycler(thisView.context)
        viewModel.fillDoneRecycler(thisView.context)
        return thisView
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, VolunteerServiceViewModelFactory())
            .get(VolunteerServiceViewModel::class.java)

        viewModel.navigationEvent.observe(this, Observer {
            loading.visibility = View.GONE
        })

        viewModel.errorEvent.observe(this, Observer {
            this.context?.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })

        viewModel.nextRecyclerViewEvent.observe(this, Observer {
            nextList.add(it)
            nextRecycler.layoutManager = LinearLayoutManager(thisView.context)
            nextRecycler.adapter = NextServicesAdapter(nextList, thisView.context)
        })

        viewModel.doneRecyclerViewEvent.observe(this, Observer {
            if (it.name != null) {
                doneList.add(it)
                doneRecycler.layoutManager = LinearLayoutManager(thisView.context)
                doneRecycler.adapter = DoneServicesAdapter(doneList, thisView.context)
            }
            loading.visibility = View.GONE
        })
    }
}
