package org.dipalme.proteApp.ui.calendar

<<<<<<< Updated upstream
import android.content.Context
=======
import android.graphics.Color
>>>>>>> Stashed changes
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.RadioButton
import androidx.fragment.app.Fragment
<<<<<<< Updated upstream
import androidx.lifecycle.ViewModelProviders
import org.dipalme.proteApp.R
import java.util.*
=======
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.dipalme.proteApp.R
import java.text.SimpleDateFormat
import java.util.*

>>>>>>> Stashed changes

@Suppress("DEPRECATION")
class CalendarFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var calendar: CompactCalendarView
    private lateinit var rbDisp1: RadioButton
    private lateinit var rbDisp2: RadioButton
    private lateinit var rbDisp3: RadioButton
    private lateinit var rbDisp4: RadioButton
    private lateinit var rbDisp5: RadioButton
    private lateinit var rbDisp6: RadioButton
    private lateinit var rbDisp7: RadioButton
    private lateinit var rbDisp8: RadioButton
    private lateinit var viewModel: CalendarViewModel
    private lateinit var loading: ViewStub

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        loadViews(root)
<<<<<<< Updated upstream

        calendar.

=======
        calendarlistener()
        loading.visibility = View.VISIBLE
        initViewModel()
>>>>>>> Stashed changes
        return root
    }

    fun calendarlistener() {
        calendar.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = calendar.getEvents(dateClicked)
                /**
                 * Mostrar dialogo con el dia seleccionado y los eventos que haya, solo hora y nombre del evento (nombre = lugar + actividad)
                 */
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d("TAG", "Month was scrolled to: $firstDayOfNewMonth")
                /**
                 * Cambiar mes en tvMonth
                 */
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

        viewModel.calendarAvailability.observe(this, androidx.lifecycle.Observer {
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


<<<<<<< Updated upstream




























    private fun loadViews( root: View){
        calendar = root.findViewById(R.id.calendarView)
=======
    private fun loadViews(root: View) {
        calendar = root.findViewById(R.id.compactcalendar_view)
>>>>>>> Stashed changes
        rbDisp1 = root.findViewById(R.id.rbDisp1)
        rbDisp2 = root.findViewById(R.id.rbDisp2)
        rbDisp3 = root.findViewById(R.id.rbDisp3)
        rbDisp4 = root.findViewById(R.id.rbDisp4)
        rbDisp5 = root.findViewById(R.id.rbDisp5)
        rbDisp6 = root.findViewById(R.id.rbDisp6)
        rbDisp7 = root.findViewById(R.id.rbDisp7)
        rbDisp8 = root.findViewById(R.id.rbDisp8)
        loading = root.findViewById(R.id.vsLoading)
    }
}
