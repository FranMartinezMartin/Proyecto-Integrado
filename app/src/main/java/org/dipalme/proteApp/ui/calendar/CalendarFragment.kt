package org.dipalme.proteApp.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import org.dipalme.proteApp.R


class CalendarFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var calendar: CalendarView
    private lateinit var rbDisp1: RadioButton
    private lateinit var rbDisp2: RadioButton
    private lateinit var rbDisp3: RadioButton
    private lateinit var rbDisp4: RadioButton
    private lateinit var rbDisp5: RadioButton
    private lateinit var rbDisp6: RadioButton
    private lateinit var rbDisp7: RadioButton
    private lateinit var rbDisp8: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        calendarViewModel = CalendarViewModel()
        loadViews(root)
        return root
    }


    private fun loadViews(root: View) {
        calendar = root.findViewById(R.id.calendarView)
        rbDisp1 = root.findViewById(R.id.rbDisp1)
        rbDisp2 = root.findViewById(R.id.rbDisp2)
        rbDisp3 = root.findViewById(R.id.rbDisp3)
        rbDisp4 = root.findViewById(R.id.rbDisp4)
        rbDisp5 = root.findViewById(R.id.rbDisp5)
        rbDisp6 = root.findViewById(R.id.rbDisp6)
        rbDisp7 = root.findViewById(R.id.rbDisp7)
        rbDisp8 = root.findViewById(R.id.rbDisp8)
    }
}
