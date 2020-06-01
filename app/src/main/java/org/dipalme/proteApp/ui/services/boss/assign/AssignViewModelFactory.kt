package org.dipalme.proteApp.ui.services.boss.assign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AssignViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AssignViewModel() as T
    }
}