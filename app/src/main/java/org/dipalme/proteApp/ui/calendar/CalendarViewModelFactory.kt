@file:Suppress("UNCHECKED_CAST")

package org.dipalme.proteApp.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalendarViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarViewModel() as T
    }
}