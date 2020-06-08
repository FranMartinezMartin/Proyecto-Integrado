package org.dipalme.proteApp.model

import com.google.gson.annotations.SerializedName

data class Contact (
    @SerializedName("nombre")
    val name: String?,
    @SerializedName("telefono")
    val movil: String?,
    @SerializedName("cargo")
    val occupation: String?
)