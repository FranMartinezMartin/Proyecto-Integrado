package org.dipalme.proteApp

sealed class NavigationEvent {
    object NavigationMain : NavigationEvent()
    object NavigationCalendar : NavigationEvent()
    object NavigationContact : NavigationEvent()
    object NavigationAssignFragment : NavigationEvent()
    object NavigationAssignAction : NavigationEvent()

    class AssignActionVolunteer(list: MutableList<String>) {
        val data = list

        fun getList(): MutableList<String> {
            return data
        }

        fun getSize(): Int {
            return data.size
        }
    }

    class AssignActionVehicle(list: MutableList<String>) {
        val data = list

        fun getList(): MutableList<String> {
            return data
        }

        fun getSize(): Int {
            return data.size
        }
    }
}