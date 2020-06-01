package org.dipalme.proteApp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.dipalme.proteApp.data.DrawerDataState
import org.dipalme.proteApp.data.Repository
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent

class DrawerViewModel: ViewModel() {
    private val drawerDataState = MutableLiveData<DrawerDataState>()
    //val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    //val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    init {
        drawerDataState.value = DrawerDataState()
    }

    fun loadServiceFragment(context: Context): Boolean? {
        val vol = Repository(context).getCurrentVolunteer()
        drawerDataState.value = drawerDataState.value?.copy(
            isBoss = vol?.boss
        )
        //return drawerDataState.value?.isBoss
        return true
    }
}