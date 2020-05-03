package com.example.pi_v1.ui.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pi_v1.ui.services.boss.BossAssignFragment
import com.example.pi_v1.ui.services.boss.BossCreateFragment
import com.example.pi_v1.ui.services.boss.BossModifyFragment

@Suppress("DEPRECATION")
class PageAdapter(fragmentManager: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BossAssignFragment()
            1 -> BossModifyFragment()
            else -> BossCreateFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}