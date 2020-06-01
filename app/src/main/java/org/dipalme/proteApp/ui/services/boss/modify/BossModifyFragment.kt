package org.dipalme.proteApp.ui.services.boss.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.ui.services.boss.modify.BossModifyAdapter
import java.util.*

class BossModifyFragment : Fragment() {

    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_boss_modify, container, false)
        recycler = root.findViewById(R.id.recView)

        return root
    }

}
