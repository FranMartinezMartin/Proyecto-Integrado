package org.dipalme.proteApp.ui.calendar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.sundeepk.compactcalendarview.CompactCalendarView
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
                        var serviceDate: Timestamp = document.data?.get("fecha") as Timestamp
                        if (serviceDate != null) {
                            Log.w("Fecha servicio", "${document.id}"+serviceDate.toDate().time)
                            /*
                                Forma de crear eventos y añadirlos al calendario
                                var myCalendar = Calendar.getInstance()

                                myCalendar.set(Calendar.YEAR, 2020)
                                myCalendar.set(Calendar.MONTH, 5)
                                myCalendar.set(Calendar.DAY_OF_MONTH, 23)

                                var event = Event (Color.BLUE, myCalendar.getTimeInMillis(), "Evento de prueba en el dia 23/05/2020")
                                calendar.addEvent(event)
                            */
                        } else {
                            errorEvent.postValue(R.string.ER_004.toString())
                        }
                    }
                } else {
                    errorEvent.postValue(R.string.ER_005.toString())
                }
            }
        }
    }
}