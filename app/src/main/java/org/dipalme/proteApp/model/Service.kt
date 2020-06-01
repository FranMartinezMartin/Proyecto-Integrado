package org.dipalme.proteApp.model

import java.util.*

data class Service(
    val volunteersList: List<Volunteer>?,
    val vehicleList: List<Vehicle>?,
    val name: String?,
    val place: String?,
    val date: Date?,
    val id: String?,
    val servicePaper: String?,
    val revisionPaper: String?,
    //val map: Uri?,
    val contact: String?
)