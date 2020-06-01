package org.dipalme.proteApp.ui.calendar

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.firestore.FirebaseFirestore
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.CalendarAvailabilityState
import org.dipalme.proteApp.data.Repository
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent


class CalendarViewModel : ViewModel() {
    val calendarAvailability = MutableLiveData<CalendarAvailabilityState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val successEvent: SingleLiveEvent<Int> = SingleLiveEvent()

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
                        val event = document.getTimestamp("fecha")?.toDate()?.time?.let {
                            Event(Color.BLUE, it, document.getString("nombre"))
                        }
                        compactCalendar.addEvent(event)
                        navigationEvent.postValue(NavigationEvent.NavigationCalendar)
                    }
                } else {
                    errorEvent.postValue(R.string.ER_005)
                }
            }
        }
    }

    fun saveAvailability(day: String, context: Context) {
        BACKGROUND.submit {
            val volunteer = Repository(context).getCurrentVolunteer()
            val db = FirebaseFirestore.getInstance()

            db.collection("Disponibilidad").document(day).collection(volunteer?.name.toString())
                .document("V-" + volunteer?.indicative.toString())
                .set(
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
                ).addOnSuccessListener {
                    successEvent.postValue(R.string.success)
                }.addOnFailureListener { errorEvent.postValue(R.string.ER_006) }
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