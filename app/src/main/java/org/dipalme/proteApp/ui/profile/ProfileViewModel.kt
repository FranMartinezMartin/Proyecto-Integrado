package org.dipalme.proteApp.ui.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_update_user_data.view.*
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.LoginDataState
import org.dipalme.proteApp.data.Repository
import org.dipalme.proteApp.model.Volunteer
import org.dipalme.proteApp.ui.customDialog.DatePickerFragment
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class ProfileViewModel : ViewModel() {
    val loginDataState = MutableLiveData<LoginDataState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val loadDataEvent: SingleLiveEvent<Volunteer> = SingleLiveEvent()

    init {
        loginDataState.value = LoginDataState()
    }

    fun getUserData(mContext: Context) {
        val volunteer = Repository(mContext).getCurrentVolunteer()
        loadDataEvent.postValue(volunteer)
    }

    private lateinit var date: Timestamp

    fun showDatePickerDialog(v: View, fragmentManager: FragmentManager) {
        BACKGROUND.submit {
            val newFragment =
                DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val c = Calendar.getInstance()
                    c.set(year, month, day)
                    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    date = Timestamp(c.time)
                    v.tvBirthday.setText(formatter.format(c.time))
                })

            newFragment.show(fragmentManager, "datePicker")
        }
    }

    fun saveData(view: View) {
        BACKGROUND.submit {
            val volunteer = Repository(view.context).getCurrentVolunteer()
            Log.w("Datos voluntario guardado", volunteer.toString())
            val ind = "V-" + volunteer?.indicative

            val dl = view.cbDriverLicense.isChecked

            Log.w("TAG fecha", date.toString())
            if (volunteer?.indicative != null) {
                Log.w("TAG inicio if", "se empieza a buscar $ind")
                FirebaseFirestore.getInstance().collection("Usuarios")
                    .document(ind).update(
                        hashMapOf(
                            "nombre" to view.tvName.text.toString(),
                            "correo" to view.tvEmail.text.toString(),
                            "telefono" to view.tvPhone.text.toString(),
                            "carnet" to dl,
                            "nacimiento" to date
                        ) as Map<String, Any>
                    ).addOnSuccessListener {
                        Log.w("TAG", "update bien")
                        navigationEvent.postValue(NavigationEvent.NavigationUpdateProfile)
                    }.addOnFailureListener {
                        errorEvent.postValue(R.string.impossibleSaveData)
                        Log.w("TAG", it)
                    }
            } else {
                errorEvent.postValue(R.string.impossibleSaveData)
                Log.w("TAG", "indicativo nulo")
            }

        }
    }
}