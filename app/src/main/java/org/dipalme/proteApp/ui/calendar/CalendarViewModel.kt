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
import org.dipalme.proteApp.data.Repository
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent
import java.time.LocalDate


class CalendarViewModel : ViewModel() {
    val calendarAvailability = MutableLiveData<CalendarAvailabilityState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val successEvent: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        calendarAvailability.value =
            CalendarAvailabilityState(null, null, null, null, null, null, null, null)
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

    fun saveAvailability() {
        BACKGROUND.submit {
            val volunteer = CalendarFragment().context?.let { Repository(it).getCurrentVolunteer() }
            val db = FirebaseFirestore.getInstance()
            val today: String =
                LocalDate.now().dayOfMonth.toString() + "-" + LocalDate.now().month.toString() + "-" + LocalDate.now().year.toString()

            db.collection("Disponibilidad").document(today).collection(volunteer?.name.toString())
                .add(
                    mapOf(
                        "00-06" to calendarAvailability.value?.available_00_06,
                        "06-12" to calendarAvailability.value?.available_06_12,
                        "12-18" to calendarAvailability.value?.available_12_18,
                        "18-24" to calendarAvailability.value?.available_18_24,
                        "00-12" to calendarAvailability.value?.available_00_12,
                        "06-18" to calendarAvailability.value?.available_06_18,
                        "12-24" to calendarAvailability.value?.available_12_24,
                        "00-24" to calendarAvailability.value?.available_00_24
                    )
                ).addOnSuccessListener { successEvent.postValue(R.string.success.toString())
                }.addOnFailureListener { errorEvent.postValue(R.string.ER_006.toString()) }
        }
    }

    fun validateAvailability(
        rb1: Boolean,
        rb2: Boolean,
        rb3: Boolean,
        rb4: Boolean,
        rb5: Boolean,
        rb6: Boolean,
        rb7: Boolean,
        rb8: Boolean
    ) {
        calendarAvailability.value =
            CalendarAvailabilityState(rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8)
    }

}