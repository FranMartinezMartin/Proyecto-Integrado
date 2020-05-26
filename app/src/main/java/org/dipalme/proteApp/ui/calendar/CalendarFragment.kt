package org.dipalme.proteApp.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showCalendarDialog
import org.dipalme.proteApp.extension.showErrorDialog
import java.util.*


@Suppress("DEPRECATION")
class CalendarFragment : Fragment() {
    private lateinit var calendar: CompactCalendarView
    private lateinit var cbDisp1: CheckBox
    private lateinit var cbDisp2: CheckBox
    private lateinit var cbDisp3: CheckBox
    private lateinit var cbDisp4: CheckBox
    private lateinit var cbDisp5: CheckBox
    private lateinit var cbDisp6: CheckBox
    private lateinit var cbDisp7: CheckBox
    private lateinit var cbDisp8: CheckBox
    private lateinit var tvmonth: TextView
    private lateinit var viewModel: CalendarViewModel
    private lateinit var btAvailability: Button
    private lateinit var loading: ViewStub
    private lateinit var thisView: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisView = inflater.inflate(R.layout.fragment_calendar, container, false)
        loadViews(thisView)
        calendarlistener()
        radioButtonsListener()
        loading.visibility = View.VISIBLE
        initViewModel()
        return thisView
    }

    private fun calendarlistener() {
        calendar.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = calendar.getEvents(dateClicked)
                thisView.context.showCalendarDialog(events)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                if (firstDayOfNewMonth.month < 1) {
                    firstDayOfNewMonth.month = 12
                }
                if (firstDayOfNewMonth.month > 12) {
                    firstDayOfNewMonth.month = 1
                }
                tvmonth.text = resources.getStringArray(R.array.months)[firstDayOfNewMonth.month]
            }
        })
    }

    private fun radioButtonsListener() {
        cbDisp1.setOnClickListener {
            if (cbDisp1.isChecked) {
                cbDisp2.isChecked = false
                cbDisp5.isChecked = false
                cbDisp8.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
        cbDisp2.setOnClickListener {
            if (cbDisp2.isChecked) {
                cbDisp1.isChecked = false
                cbDisp5.isChecked = false
                cbDisp8.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
        cbDisp3.setOnClickListener {
            if (cbDisp3.isChecked) {
                cbDisp4.isChecked = false
                cbDisp6.isChecked = false
                cbDisp7.isChecked = false
                cbDisp8.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
        cbDisp4.setOnClickListener {
            if (cbDisp4.isChecked) {
                cbDisp3.isChecked = false
                cbDisp6.isChecked = false
                cbDisp7.isChecked = false
                cbDisp8.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
        cbDisp5.setOnClickListener {
            if (cbDisp5.isChecked) {
                cbDisp1.isChecked = false
                cbDisp2.isChecked = false
                cbDisp6.isChecked = false
                cbDisp7.isChecked = false
                cbDisp8.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
        cbDisp6.setOnClickListener {
            if (cbDisp6.isChecked) {
                cbDisp1.isChecked = false
                cbDisp2.isChecked = false
                cbDisp3.isChecked = false
                cbDisp5.isChecked = false
                cbDisp7.isChecked = false
                cbDisp8.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
        cbDisp7.setOnClickListener {
            if (cbDisp7.isChecked) {
                cbDisp3.isChecked = false
                cbDisp4.isChecked = false
                cbDisp5.isChecked = false
                cbDisp6.isChecked = false
                cbDisp8.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
        cbDisp8.setOnClickListener {
            if (cbDisp8.isChecked) {
                cbDisp1.isChecked = false
                cbDisp2.isChecked = false
                cbDisp3.isChecked = false
                cbDisp4.isChecked = false
                cbDisp5.isChecked = false
                cbDisp6.isChecked = false
                cbDisp7.isChecked = false
                calendarAvailabilityStateCheck()
            }
        }
    }

    private fun calendarAvailabilityStateCheck() {
        viewModel.validateAvailability(
            cbDisp1.isChecked,
            cbDisp2.isChecked,
            cbDisp3.isChecked,
            cbDisp4.isChecked,
            cbDisp5.isChecked,
            cbDisp6.isChecked,
            cbDisp7.isChecked,
            cbDisp8.isChecked
        )
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, CalendarViewModelFactory())
            .get(CalendarViewModel::class.java)

        viewModel.loadCalendar(calendar)

        viewModel.navigationEvent.observe(this, androidx.lifecycle.Observer {
            loading.visibility = View.GONE
        })

        viewModel.errorEvent.observe(this, androidx.lifecycle.Observer {
            thisView.context.showErrorDialog(it)
        })

        viewModel.calendarAvailability.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            btAvailability.isEnabled = it.available_00_06 == true
                    || it.available_00_12 == true
                    || it.available_00_24 == true
                    || it.available_06_12 == true
                    || it.available_06_18 == true
                    || it.available_12_18 == true
                    || it.available_12_24 == true
                    || it.available_18_24 == true
        })

        viewModel.successEvent.observe(this, androidx.lifecycle.Observer {
            thisView.context.showErrorDialog(it)
        })
    }

    private fun loadViews(root: View) {
        calendar = root.findViewById(R.id.compactcalendar_view)
        cbDisp1 = root.findViewById(R.id.cbDisp1)
        cbDisp2 = root.findViewById(R.id.cbDisp2)
        cbDisp3 = root.findViewById(R.id.cbDisp3)
        cbDisp4 = root.findViewById(R.id.cbDisp4)
        cbDisp5 = root.findViewById(R.id.cbDisp5)
        cbDisp6 = root.findViewById(R.id.cbDisp6)
        cbDisp7 = root.findViewById(R.id.cbDisp7)
        cbDisp8 = root.findViewById(R.id.cbDisp8)
        loading = root.findViewById(R.id.vsLoading)
        tvmonth = root.findViewById(R.id.tvMonth)
        btAvailability = root.findViewById(R.id.btAvailability)
        val c1 = Calendar.getInstance()
        tvmonth.text = resources.getStringArray(R.array.months)[c1.get(Calendar.MONTH)]
        btAvailability.setOnClickListener {
            viewModel.saveAvailability()
        }
    }
}
