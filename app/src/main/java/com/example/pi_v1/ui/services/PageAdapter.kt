package com.example.pi_v1.ui.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pi_v1.ui.services.boss.AssignFragment
import com.example.pi_v1.ui.services.boss.CreateFragment
import com.example.pi_v1.ui.services.boss.ModifyFragment

@Suppress("DEPRECATION")
class PageAdapter(fragmentManager: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AssignFragment()
            1 -> ModifyFragment()
            else -> CreateFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}