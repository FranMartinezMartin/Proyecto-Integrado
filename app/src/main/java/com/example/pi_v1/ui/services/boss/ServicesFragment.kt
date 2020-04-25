package com.example.pi_v1.ui.services.boss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pi_v1.R

class ServicesFragment : Fragment() {

    private lateinit var slideshowViewModel: ServicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(ServicesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_services, container, false)
        return root
    }
}
