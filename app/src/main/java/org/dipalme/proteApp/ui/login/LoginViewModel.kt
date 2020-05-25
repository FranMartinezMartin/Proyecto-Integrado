package org.dipalme.proteApp.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
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
    val errorEvent: SingleLiveEvent<String> = SingleLiveEvent()

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

    fun UserLog(context: Context, indicative: String, password: String) {
        BACKGROUND.submit {
            // Access a Cloud Firestore instance from your Activity
            val db = FirebaseFirestore.getInstance()

            db.collection("Usuarios").document(indicative).get().addOnSuccessListener { r ->
                if (r.exists()) {
                    if (r.data?.get("contraseña").toString() == password) {
                        navigationEvent.postValue(NavigationEvent.NavigationMain)




                        /*val volunteer = r.toObject(Volunteer::class.java)
                        Log.w("Volunteer ID: LoginViewModel()", volunteer.toString())
                        if (volunteer != null) {
                            Repository(context).setCurrentVolunteer(volunteer)
                        }*/
                    } else {
                        errorEvent.postValue(R.string.ER_002.toString())
                    }
                } else {
                    errorEvent.postValue(R.string.ER_001.toString())
                }
            }.addOnFailureListener {
                errorEvent.postValue(R.string.ER_001.toString())
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
}