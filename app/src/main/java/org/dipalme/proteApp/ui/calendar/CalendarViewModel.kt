package org.dipalme.proteApp.ui.calendar

import android.graphics.Color
import android.util.EventLog
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.CalendarAvailabilityState
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent


class CalendarViewModel : ViewModel() {
    val calendarAvailability = MutableLiveData<CalendarAvailabilityState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        calendarAvailability.value = CalendarAvailabilityState()
    }

    fun loadCalendar(compactCalendar: CompactCalendarView) {
        // Crear lista de eventos para llenar calendario
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            db.collection("Servicios").get().addOnSuccessListener { result ->
                if (result != null) {
                    for (document in result) {
                        var event = document.getTimestamp("fecha")?.toDate()?.time?.let {
                            Event(Color.BLUE, it, document.getString("nombre"))
                        }
                        compactCalendar.addEvent(event)
                        navigationEvent.postValue(NavigationEvent.NavigationCalendar)
                    }
                } else {
                    errorEvent.postValue(R.string.ER_005.toString())
                }
            }
        }
    }


}