@file:Suppress("DEPRECATION")

package org.dipalme.proteApp.ui.home

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

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var loading: ViewStub
    private lateinit var recycler: RecyclerView
    private lateinit var thisView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisView = inflater.inflate(R.layout.fragment_home, container, false)
        loading = thisView.findViewById(R.id.vsLoading)
        recycler = thisView.findViewById(R.id.homeRecycler)
        loading.visibility = View.VISIBLE
        initViewModel()
        viewModel.loadRecycler()
        return thisView
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, HomeViewModelFactory()).get(HomeViewModel::class.java)

        viewModel.navigationEvent.observe(this, Observer {
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = HomeAdapter(it, thisView.context)
            loading.visibility = View.GONE
        })

        viewModel.errorEvent.observe(this, Observer {
            thisView.context.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })
    }
}
