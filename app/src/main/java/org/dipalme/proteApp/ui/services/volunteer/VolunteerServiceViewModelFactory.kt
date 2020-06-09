@file:Suppress("UNCHECKED_CAST")

package org.dipalme.proteApp.ui.services.volunteer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VolunteerServiceViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VolunteerServiceViewModel() as T
    }
}