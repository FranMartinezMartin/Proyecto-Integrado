package org.dipalme.proteApp.ui.services.boss.assign

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_boss_assign.*
import kotlinx.android.synthetic.main.fragment_boss_services.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.channels.ticker
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.ServicesDataState
import org.dipalme.proteApp.extension.showErrorDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class BossAssignFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var thisContext: Context
    private var viewModel = AssignViewModel()
    private lateinit var vDate: View
    private lateinit var tvDate: TextView
    private var selectedDate by Delegates.notNull<Long>()
    private lateinit var loading: ViewStub

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_boss_assign, container, false)
        recycler = root.findViewById(R.id.servicesRecView)
        tvDate = root.findViewById(R.id.tvDate)
        vDate = root.findViewById(R.id.vDate)
        loading = root.findViewById(R.id.vsLoading)
        thisContext = root.context
        loadDate()
        initViewModel()

        vDate.setOnClickListener {
            showDatePicker(Calendar.getInstance())
        }
        return root
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, AssignViewModelFactory()).get(AssignViewModel::class.java)

        viewModel.servicesDataState.observe(viewLifecycleOwner, Observer {
            val cal = it.date
            if (cal != null) {
                changeDate(cal.time)
                selectedDate = cal.timeInMillis
            }
            loading.visibility = View.GONE
        })

        viewModel.errorEvent.observe(viewLifecycleOwner, Observer {
            thisContext.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })

        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer {
            loading.visibility = View.GONE
        })

        viewModel.infoEvent.observe(viewLifecycleOwner, Observer {
            thisContext.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })
    }

    private fun loadDate() {
        val sdf = SimpleDateFormat("dd MMM yyyy")
        tvDate.text = sdf.format(Calendar.getInstance().time)
        selectedDate = Calendar.getInstance().timeInMillis
        loading.visibility = View.VISIBLE
        Log.w("fun loadDate()", "########## loading view on ##########")
        viewModel.getServiceByDay(selectedDate, recycler, thisContext)
    }

    private fun changeDate(cal: Date) {
        val sdf = SimpleDateFormat("dd MMM yyyy")
        tvDate.text = sdf.format(cal.time)
        selectedDate = cal.time
        loading.visibility = View.VISIBLE
        Log.w("fun changeDate()", "########## loading view on ##########")
        viewModel.getServiceByDay(selectedDate, recycler, thisContext)
    }

    private fun showDatePicker(date: Calendar) {
        val dialog = DatePickerDialog(
            requireContext(),
            dateListener,
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate = System.currentTimeMillis() - 1000
        dialog.show()
    }

    private val dateListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            viewModel.setSelectedDate(cal)
        }
}
