package org.dipalme.ProteApp.ui.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.dipalme.ProteApp.ui.services.boss.BossAssignFragment
import org.dipalme.ProteApp.ui.services.boss.BossCreateFragment
import org.dipalme.ProteApp.ui.services.boss.BossModifyFragment

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