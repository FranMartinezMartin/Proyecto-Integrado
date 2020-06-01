package org.dipalme.proteApp.ui.services.boss.assign

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.ServicesDataState
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AssignViewModel : ViewModel() {
    val servicesDataState = MutableLiveData<ServicesDataState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    init {
        servicesDataState.value = ServicesDataState()
    }

    fun getCurrentDate(tv: TextView) {
        BACKGROUND.submit {
            try {
                val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                val day = LocalDate.now().dayOfMonth
                val month = LocalDate.now().monthValue
                val year = LocalDate.now().year
                tv.text = LocalDate.parse("$day-$month-$year", formatter).toString()
                navigationEvent.postValue(NavigationEvent.NavigationAssignFragment)
            } catch (e: Exception) {
                errorEvent.postValue(R.string.ER_008)
            }
        }
    }

    fun setSelectedDate(cal: Calendar) {
        servicesDataState.value = servicesDataState.value?.copy(
            date = cal
        )
    }
}