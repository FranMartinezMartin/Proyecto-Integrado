package org.dipalme.ProteApp.ui.manuals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import org.dipalme.ProteApp.R
import org.dipalme.ProteApp.ui.home.HomeViewModel

class ManualFragment : Fragment() {

    private lateinit var manualsViewModel: ManualsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_manuals, container, false)
        return root
    }
}