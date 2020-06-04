package org.dipalme.proteApp

import org.dipalme.proteApp.model.Service

sealed class NavigationEvent {
    object NavigationMain : NavigationEvent()
    object NavigationCalendar: NavigationEvent()
    object NavigationContact: NavigationEvent()
    object NavigationAssignFragment: NavigationEvent()
    object NavigationAssignAction: NavigationEvent()
}