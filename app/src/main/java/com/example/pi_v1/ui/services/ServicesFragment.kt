package com.example.pi_v1.ui.services

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.pi_v1.R
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import java.util.zip.Inflater

class ServicesFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_services, container, false)

        tabLayout = root.findViewById(R.id.tabLayout)

        pager = root.findViewById(R.id.pager)
        val pageAdapter = PageAdapter(childFragmentManager, tabLayout.tabCount)
        pager.adapter = pageAdapter

        pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        )
        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        return root
    }
}