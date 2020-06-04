package org.dipalme.proteApp.ui.services.boss.assign

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_boss_assign.*
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showErrorDialog
import org.dipalme.proteApp.ui.customDialog.CustomAssignDialog
import java.util.*

class BossAssignActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var tvServiceName: TextView
    private lateinit var tvTime: TextView
    private lateinit var volunteerList: ListView
    private lateinit var vehicleList: ListView
    private lateinit var viewModel: AssignViewModel
    private lateinit var loading: ViewStub

    private lateinit var serviceDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss_assign)
        loadViews()
        loading.visibility = View.VISIBLE
        initView()
        val serviceID = intent.getStringExtra("service id")
        val service = viewModel.getService(serviceID)

        btnAccept.setOnClickListener {
            viewModel.saveLists()
        }
        btnBack.setOnClickListener { finish() }

        viewVolunteers.setOnClickListener {
            val fragmentManager = supportFragmentManager

            val newFragment =
                CustomAssignDialog(viewModel.volunteersList(service))
            newFragment.show(fragmentManager, "dialog")
        }
        viewVehicles.setOnClickListener {

        }
    }

    private fun initView() {
        viewModel =
            ViewModelProviders.of(this, AssignViewModelFactory()).get(AssignViewModel::class.java)

        viewModel.servicesDataState.observe(this, Observer {
            loading.visibility = View.GONE
        })

        viewModel.errorEvent.observe(this, Observer {
            this.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })

        viewModel.navigationEvent.observe(this, Observer {
            loading.visibility = View.GONE
        })

        viewModel.infoEvent.observe(this, Observer {
            this.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })
    }

    private fun loadViews() {
        toolbar = findViewById(R.id.assignToolbar)
        setSupportActionBar(toolbar)
        tvServiceName = findViewById(R.id.tvServiceName)
        tvTime = findViewById(R.id.tvTime)
        volunteerList = findViewById(R.id.vol_List)
        vehicleList = findViewById(R.id.vehicles_List)
        loading = findViewById(R.id.vsLoading)
        viewModel = AssignViewModel()
    }
}
