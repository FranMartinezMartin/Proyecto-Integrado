package org.dipalme.proteApp.ui.manuals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.dipalme.proteApp.R

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