package org.dipalme.proteApp.ui.services.boss

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.dipalme.proteApp.ui.services.boss.assign.BossAssignFragment
import org.dipalme.proteApp.ui.services.boss.create.BossCreateFragment
import org.dipalme.proteApp.ui.services.boss.modify.BossModifyFragment

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