package com.example.pi_v1.model

import android.net.Uri
import java.io.File
import java.util.*

data class Service (
    val volunteersList : List<Volunteer>?,
    val vehicleList: List<Vehicle>?,
    val name: String,
    val place: String,
    val date: Date,
    val id: String,
    val servicePaper: File?,
    val revisionPaper: File?,
    val map: Uri?,
    val contact: Contact?
)