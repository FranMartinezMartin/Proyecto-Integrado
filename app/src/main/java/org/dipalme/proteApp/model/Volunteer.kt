package org.dipalme.proteApp.model

import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName

data class Volunteer(
    @SerializedName("activo")
    val active: Boolean?,
    @SerializedName("carnet")
    val driver: Boolean?,
    @SerializedName("contraseña")
    val password: String?,
    @SerializedName("correo")
    val mail: String?,
    @SerializedName("indicativo")
    val indicative: String?,
    @SerializedName("ingreso")
    val incorporation: Timestamp?,
    @SerializedName("jefe")
    val boss: Boolean?,
    @SerializedName("nacimiento")
    val birthday: Timestamp?,
    @SerializedName("nombre")
    val name: String?,
    @SerializedName("telefono")
    var phone: String?
)