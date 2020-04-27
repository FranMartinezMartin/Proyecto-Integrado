package com.example.pi_v1.model

import android.net.Uri
import java.io.File

data class Service (
    val volunteersList : List<Volunteer>?,
    val vehicleList: List<Vehicle>?,
    val name: String,
    val id: String,
    val servicePaper: File,
    val revisionPaper: File?,
    val map: Uri?,
    val contact: Contact
)