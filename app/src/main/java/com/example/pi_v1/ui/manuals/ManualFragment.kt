package com.example.pi_v1.ui.manuals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.pi_v1.R
import com.example.pi_v1.ui.home.HomeViewModel

class ManualFragment : Fragment() {

    private lateinit var manualsViewModel: ManualsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manualsViewModel =
            ViewModelProviders.of(this).get(manualsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_manuals, container, false)
        return root
    }
}