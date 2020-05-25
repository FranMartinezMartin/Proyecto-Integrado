package org.dipalme.proteApp.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.RadioButton
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
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


@Suppress("DEPRECATION")
class CalendarFragment : Fragment() {
    private lateinit var calendar: CompactCalendarView
    private lateinit var rbDisp1: RadioButton
    private lateinit var rbDisp2: RadioButton
    private lateinit var rbDisp3: RadioButton
    private lateinit var rbDisp4: RadioButton
    private lateinit var rbDisp5: RadioButton
    private lateinit var rbDisp6: RadioButton
    private lateinit var rbDisp7: RadioButton
    private lateinit var rbDisp8: RadioButton
    private lateinit var tvmonth: TextView
    private lateinit var viewModel: CalendarViewModel
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
        loading.visibility = View.VISIBLE
        initViewModel()
        return thisView
    }

    fun calendarlistener() {
        calendar.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = calendar.getEvents(dateClicked)
                thisView.context.showCalendarDialog(events)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d("TAG", "Month was scrolled to: $firstDayOfNewMonth")
                if (firstDayOfNewMonth.month < 1){
                    firstDayOfNewMonth.month = 12
                }
                if (firstDayOfNewMonth.month > 12){
                    firstDayOfNewMonth.month = 1
                }
                tvmonth.text = resources.getStringArray(R.array.months)[firstDayOfNewMonth.month]
            }
        })
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
            btAvailability.isEnabled =
                it.available_00_06 != null && it.available_00_12 != null && it.available_00_24 != null && it.available_06_12 != null && it.available_06_18 != null && it.available_12_18 != null && it.available_12_24 != null && it.available_18_24 != null
            when (it.available_00_06) {
                true -> {
                    rbDisp2.isChecked = false
                    rbDisp5.isChecked = false
                    rbDisp8.isChecked = false
                }
                null -> rbDisp1.isChecked = false
            }
            when (it.available_06_12) {
                true -> {
                    rbDisp1.isChecked = false
                    rbDisp5.isChecked = false
                    rbDisp8.isChecked = false
                }
                null -> rbDisp2.isChecked = false
            }
            when (it.available_12_18) {
                true -> {
                    rbDisp4.isChecked = false
                    rbDisp6.isChecked = false
                    rbDisp7.isChecked = false
                    rbDisp8.isChecked = false
                }
                null -> rbDisp3.isChecked = false
            }
            when (it.available_18_24) {
                true -> {
                    rbDisp3.isChecked = false
                    rbDisp6.isChecked = false
                    rbDisp7.isChecked = false
                    rbDisp8.isChecked = false
                }
                null -> rbDisp4.isChecked = false
            }
            when (it.available_00_12) {
                true -> {
                    rbDisp1.isChecked = false
                    rbDisp2.isChecked = false
                    rbDisp6.isChecked = false
                    rbDisp7.isChecked = false
                    rbDisp8.isChecked = false
                }
                null -> rbDisp5.isChecked = false
            }
            when (it.available_06_18) {
                true -> {
                    rbDisp1.isChecked = false
                    rbDisp2.isChecked = false
                    rbDisp3.isChecked = false
                    rbDisp5.isChecked = false
                    rbDisp7.isChecked = false
                    rbDisp8.isChecked = false
                }
                null -> rbDisp6.isChecked = false
            }
            when (it.available_12_24) {
                true -> {
                    rbDisp3.isChecked = false
                    rbDisp4.isChecked = false
                    rbDisp5.isChecked = false
                    rbDisp6.isChecked = false
                    rbDisp8.isChecked = false
                }
                null -> rbDisp7.isChecked = false
            }
            when (it.available_00_24) {
                true -> {
                    rbDisp1.isChecked = false
                    rbDisp2.isChecked = false
                    rbDisp3.isChecked = false
                    rbDisp4.isChecked = false
                    rbDisp5.isChecked = false
                    rbDisp6.isChecked = false
                    rbDisp7.isChecked = false
                }
                null -> rbDisp8.isChecked = false
            }
        })
    }

    private fun loadViews(root: View) {
        calendar = root.findViewById(R.id.compactcalendar_view)
        rbDisp1 = root.findViewById(R.id.rbDisp1)
        rbDisp2 = root.findViewById(R.id.rbDisp2)
        rbDisp3 = root.findViewById(R.id.rbDisp3)
        rbDisp4 = root.findViewById(R.id.rbDisp4)
        rbDisp5 = root.findViewById(R.id.rbDisp5)
        rbDisp6 = root.findViewById(R.id.rbDisp6)
        rbDisp7 = root.findViewById(R.id.rbDisp7)
        rbDisp8 = root.findViewById(R.id.rbDisp8)
        loading = root.findViewById(R.id.vsLoading)
        tvmonth = root.findViewById(R.id.tvMonth)
        val c1 = Calendar.getInstance()
        tvmonth.text = resources.getStringArray(R.array.months)[c1.get(Calendar.MONTH)]

    }
}
