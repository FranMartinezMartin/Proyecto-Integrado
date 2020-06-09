package org.dipalme.proteApp.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.LoginDataState
import org.dipalme.proteApp.data.Repository
import org.dipalme.proteApp.extension.isIndicativeValid
import org.dipalme.proteApp.extension.isPasswordValid
import org.dipalme.proteApp.model.Volunteer
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent


class LoginViewModel : ViewModel() {
    val loginDataState = MutableLiveData<LoginDataState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val firstTimeEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val updateEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()

    init {
        loginDataState.value = LoginDataState()
    }

    fun validateIndicative(indicative: String) {
        loginDataState.value = loginDataState.value?.copy(
            validIndicative = isIndicativeValid(indicative)
        )
    }

    fun validatePassword(password: String) {
        loginDataState.value =
            loginDataState.value?.copy(validPassword = password.isPasswordValid())
    }

    fun userLog(context: Context, indicative: String, password: String) {
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            db.collection("Usuarios").document(indicative).get().addOnSuccessListener { r ->
                if (r.exists()) {
                    if (r.data?.get("contraseña").toString() == password) {
                        val volunteer = Volunteer(
                            r.getBoolean("activo"),
                            r.getBoolean("carnet"),
                            r.getString("contraseña"),
                            r.getString("correo"),
                            r.getString("indicativo"),
                            r.getTimestamp("ingreso"),
                            r.getBoolean("jefe"),
                            r.getTimestamp("nacimiento"),
                            r.getString("nombre"),
                            r.getString("telefono")
                        )
                        if (r.getString("contraseña") == "voluntario") {
                            firstTimeEvent.postValue(volunteer.indicative)
                        } else {
                            if (volunteer.active != null) {
                                Repository(context).setCurrentVolunteer(volunteer)
                            }
                            navigationEvent.postValue(NavigationEvent.NavigationMain)
                        }
                    } else {
                        errorEvent.postValue(R.string.ER_002)
                    }
                } else {
                    errorEvent.postValue(R.string.ER_001)
                }
            }.addOnFailureListener {
                errorEvent.postValue(R.string.ER_001)
            }
        }
    }

    fun checkLoggedUser(context: Context) {
        BACKGROUND.submit {
            val volunteer = Repository(context).getCurrentVolunteer()
            if (volunteer != null) {
                navigationEvent.postValue(NavigationEvent.NavigationMain)
            }
        }
    }

    fun validatePasswordChange(password1: String) {
        loginDataState.value =
            loginDataState.value?.copy(updatePass = password1.isPasswordValid())
    }

    fun validatePasswordChangeRepeat(password2: String) {
        loginDataState.value =
            loginDataState.value?.copy(updatePassRepeat = password2.isPasswordValid())
    }

    fun updatePassword(indicative: String, password1: String, password2: String) {
        BACKGROUND.submit {
            if (password1 == password2) {
                if (password1 != "voluntario") {
                    val db = FirebaseFirestore.getInstance()
                    db.collection("Usuarios").document(indicative)
                        .update("contraseña", password2)
                        .addOnSuccessListener {
                            updateEvent.postValue(NavigationEvent.NavigationUpdatePassword)
                        }.addOnFailureListener {
                            errorEvent.postValue(R.string.impossibleToChangePass)
                        }
                } else {
                    errorEvent.postValue(R.string.difPass)
                }
            } else {
                errorEvent.postValue(R.string.difPass)
            }
        }
    }
}