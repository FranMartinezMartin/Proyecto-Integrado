package org.dipalme.proteApp

sealed class NavigationEvent {
    object NavigationMain : NavigationEvent()
    object NavigationCalendar: NavigationEvent()
}