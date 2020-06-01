package org.dipalme.proteApp.ui.services.boss

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import org.dipalme.proteApp.R
import com.google.android.material.tabs.TabLayout

@Suppress("DEPRECATION")
class ServicesBossFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager: ViewPager
    private lateinit var viewModel: ServicesViewModel
    private lateinit var thisContext: Context
    private lateinit var loading: ViewStub


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_boss_services, container, false)
        thisContext = root.context
        loadBossFragment(root)
        initViewModel()
        return root
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ServicesViewModelFactory())
            .get(ServicesViewModel::class.java)

        viewModel.servicesDataState.observe(viewLifecycleOwner, Observer {
        })
    }

    private fun loadBossFragment(root: View) {
        tabLayout = root.findViewById(R.id.tabLayout)
        pager = root.findViewById(R.id.pager)
        val pageAdapter = PageAdapter(
            childFragmentManager,
            tabLayout.tabCount
        )
        pager.adapter = pageAdapter

        pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        )
        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}
