@file:Suppress("DEPRECATION")

package org.dipalme.proteApp.ui.services.boss.assign

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_boss_assign.*
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showErrorDialog
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.ui.customDialog.CustomAssignDialogVolunteer
import org.dipalme.proteApp.ui.customDialog.CustomAssingDialogVehicle

class BossAssignActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var tvServiceName: TextView
    private lateinit var tvTime: TextView
    private lateinit var volunteerList: ListView
    private lateinit var vehicleList: ListView
    private lateinit var viewModel: AssignViewModel
    private lateinit var loading: ViewStub

    private lateinit var service: Service
    private var volSaved: MutableList<String> = mutableListOf()
    private var vehiSaved: MutableList<String> = mutableListOf()


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss_assign)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        loadViews()
        loading.visibility = View.VISIBLE
        initViewModel()
        val id = intent.getStringExtra("serviceID")
        if (id != null) viewModel.getService(id)

        viewVolunteers.setOnClickListener {
            loading.visibility = View.VISIBLE
            viewModel.volunteersList(service)
        }

        viewVehicles.setOnClickListener {
            loading.visibility = View.VISIBLE
            viewModel.vehiclesList()
        }

        btnAccept.setOnClickListener {
            if (volSaved.isNotEmpty() || vehiSaved.isNotEmpty()) {
                viewModel.saveLists(service, volSaved, vehiSaved)
            }

        }
        btnBack.setOnClickListener { finish() }
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, AssignViewModelFactory()).get(AssignViewModel::class.java)

        viewModel.servicesDataState.observe(this, Observer {
            loading.visibility = View.GONE
        })

        viewModel.serviceEvent.observe(this, Observer {
            service = it
        })

        viewModel.volunteerListEvent.observe(this, Observer {
            val fragmentManager = supportFragmentManager
            val newFragment = CustomAssignDialogVolunteer(it, viewModel)
            newFragment.show(fragmentManager, "dialog")
            loading.visibility = View.GONE
        })
        viewModel.vehicleListEvent.observe(this, Observer {
            val fragmentManager = supportFragmentManager
            val newFragment = CustomAssingDialogVehicle(it, viewModel)
            newFragment.show(fragmentManager, "dialog")
            loading.visibility = View.GONE
        })

        viewModel.volunteerListViewEvent.observe(this, Observer {
            volSaved = it.getList()
            volunteerList.adapter = MyAdapter(this, it.getList())
        })
        viewModel.vehicleListViewEvent.observe(this, Observer {
            vehiSaved = it.getList()
            vehicleList.adapter = MyAdapter(this, it.getList())
        })

        viewModel.errorEvent.observe(this, Observer {
            this.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })

        viewModel.infoEvent.observe(this, Observer {
            this.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })

        viewModel.navigationEvent.observe(this, Observer {
            finish()
        })
    }

    private fun loadViews() {
        toolbar = findViewById(R.id.toolbar4)
        setSupportActionBar(toolbar)
        tvServiceName = findViewById(R.id.tvServiceName)
        tvTime = findViewById(R.id.tvTime)
        volunteerList = findViewById(R.id.vol_List)
        vehicleList = findViewById(R.id.vehicles_List)
        loading = findViewById(R.id.vsLoading)
        viewModel = AssignViewModel()
    }

    private class MyAdapter(context: Context, list: MutableList<String>) : BaseAdapter() {
        private val c: Context = context
        private val data = list
        val inflater: LayoutInflater =
            c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.item_list_view, parent, false)
            val textView = rowView.findViewById<TextView>(R.id.tvListView)
            textView.text = data[position]
            return rowView
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return data.size
        }

    }
}
