package org.dipalme.proteApp.model

import java.util.*

data class Volunteer (
    val active: Boolean,
    val driver: Boolean?,
    val password: String,
    val mail: String,
    val incorporation: Date,
    val boss: Boolean,
    val birthday: Date?,
    val name: String,
    val phone: String
)