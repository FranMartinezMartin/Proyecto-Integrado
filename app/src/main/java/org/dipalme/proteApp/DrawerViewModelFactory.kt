@file:Suppress("UNCHECKED_CAST")

package org.dipalme.proteApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DrawerViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrawerViewModel() as T
    }
}