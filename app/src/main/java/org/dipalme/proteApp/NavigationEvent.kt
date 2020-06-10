package org.dipalme.proteApp

sealed class NavigationEvent {
    object NavigationMain : NavigationEvent()
    object NavigationCalendar : NavigationEvent()
    object NavigationContact : NavigationEvent()
    object NavigationAssignFragment : NavigationEvent()
    object NavigationAssignAction : NavigationEvent()
    object NavigationUpdatePassword : NavigationEvent()
    object NavigationUpdateProfile : NavigationEvent()

    class AssignActionVolunteer(list: MutableList<String>) {
        val data = list

        fun getList(): MutableList<String> {
            return data
        }
    }

    class AssignActionVehicle(list: MutableList<String>) {
        val data = list

        fun getList(): MutableList<String> {
            return data
        }
    }
}