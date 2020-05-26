package org.dipalme.proteApp.data

class CalendarAvailabilityState {
    var available_00_06: Boolean? = null
    var available_06_12: Boolean? = null
    var available_12_18: Boolean? = null
    var available_18_24: Boolean? = null
    var available_00_12: Boolean? = null
    var available_06_18: Boolean? = null
    var available_12_24: Boolean? = null
    var available_00_24: Boolean? = null

    constructor(
        available_00_06: Boolean?,
        available_06_12: Boolean?,
        available_12_18: Boolean?,
        available_18_24: Boolean?,
        available_00_12: Boolean?,
        available_06_18: Boolean?,
        available_12_24: Boolean?,
        available_00_24: Boolean?
    ) {
        this.available_00_06 = available_00_06
        this.available_06_12 = available_06_12
        this.available_12_18 = available_12_18
        this.available_18_24 = available_18_24
        this.available_00_12 = available_00_12
        this.available_06_18 = available_06_18
        this.available_12_24 = available_12_24
        this.available_00_24 = available_00_24
    }
}