package org.dipalme.proteApp.data

data class LoginDataState(
    val validIndicative: Boolean? = null,
    val validPassword: Boolean? = null,
    val updatePass: Boolean? = null,
    val updatePassRepeat: Boolean? = null
)