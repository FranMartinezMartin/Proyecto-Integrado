package org.dipalme.proteApp.model

import com.google.firebase.firestore.DocumentReference
import java.util.*

data class Service(
    val name: String?,
    val place: String?,
    val date: Date?,
    val id: String?,
    val contact: DocumentReference?,
    val volunteers: MutableList<String>?,
    val vehicles: MutableList<String>?
)