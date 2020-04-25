package com.example.pi_v1.ui.services.boss

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fragmentManager: FragmentManager, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private var fragments: List<Fragment> = mutableListOf()
    private var fragmentesTitles: List<Fragment> = mutableListOf()

    fun PageAdapter(fragmentManager: FragmentManager) {

    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentesTitles.get(position).toString()
    }

    fun addFragment(fragment: Fragment, title: String){

    }

}