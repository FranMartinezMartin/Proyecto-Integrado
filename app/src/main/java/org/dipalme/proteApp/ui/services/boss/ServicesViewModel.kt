package org.dipalme.proteApp.ui.services.boss

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.dipalme.proteApp.data.ServicesDataState
import java.util.*

class ServicesViewModel: ViewModel() {
    val servicesDataState = MutableLiveData<ServicesDataState>()
    //val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    //val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    init {
        servicesDataState.value = ServicesDataState(Calendar.getInstance())
    }

}