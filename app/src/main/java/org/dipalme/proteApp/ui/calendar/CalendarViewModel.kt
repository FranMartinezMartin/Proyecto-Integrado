package org.dipalme.proteApp.ui.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.CalendarAvailabilityState
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent

class CalendarViewModel: ViewModel() {
    val calendarAvailability = MutableLiveData<CalendarAvailabilityState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        calendarAvailability.value = CalendarAvailabilityState()
    }

    fun LoadCalendar() {
        BACKGROUND.submit{

        }
    }
}