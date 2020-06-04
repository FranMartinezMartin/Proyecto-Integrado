package org.dipalme.proteApp.ui.services.boss.assign

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.ServicesDataState
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class AssignViewModel : ViewModel() {
    val servicesDataState = MutableLiveData<ServicesDataState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val infoEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    init {
        servicesDataState.value = ServicesDataState()
    }

    fun getServiceByDay(dateMilis: Long, recycler: RecyclerView, context: Context) {
        BACKGROUND.submit {
            val list: MutableList<Service> = mutableListOf()
            val db = FirebaseFirestore.getInstance()
            db.collection("Servicios").get().addOnSuccessListener { result ->
                if (result != null) {
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    for (document in result) {
                        val serviceDate =
                            formatter.format(document.getTimestamp("fecha")!!.toDate())
                        if (serviceDate == formatter.format(dateMilis)) {
                            val service = Service(
                                document.getString("nombre"),
                                document.getString("lugar"),
                                document.getTimestamp("fecha")!!.toDate(),
                                document.id,
                                document.getDocumentReference("contacto").toString()
                            )
                            list.add(service)
                        }
                    }
                    if (list.isEmpty()) {
                        infoEvent.postValue(R.string.infoMessage)
                    } else {
                        navigationEvent.postValue(NavigationEvent.NavigationAssignFragment)
                    }
                    recycler.layoutManager = LinearLayoutManager(context)
                    recycler.adapter = BossAssignAdapter(list, context)
                } else {
                    errorEvent.postValue(R.string.ER_005)
                }
            }
        }
    }

    fun setSelectedDate(cal: Calendar) {
        servicesDataState.value = servicesDataState.value?.copy(
            date = cal
        )
    }

    fun getService(serviceID: String): Service {
        var serv = Service(null, null, null, null, null)
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            db.collection("Servicios").document(serviceID).get().addOnSuccessListener { result ->
                if (result != null) {
                    serv = Service(
                        result.getString("nombre"),
                        result.getString("lugar"),
                        result.getTimestamp("fecha")!!.toDate(),
                        result.id,
                        result.getDocumentReference("contacto").toString()
                    )
                    navigationEvent.postValue(NavigationEvent.NavigationAssignAction)
                } else {
                    errorEvent.postValue(R.string.ER_010)
                }
            }
        }
        return serv
    }

    fun volunteersList(service: Service): MutableList<String> {
        val volunteerList: MutableList<String> = mutableListOf()
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            db.collection("Disponibilidad").document(formatter.format(service.date)).collection("voluntarios").get()
                .addOnSuccessListener { result ->
                    if (result != null) {
                        for (document in result) {
                            volunteerList.add(document.id)
                            navigationEvent.postValue(NavigationEvent.NavigationAssignAction)
                        }
                    } else {
                        errorEvent.postValue(R.string.nullAvailability)
                    }
                }
        }
        return if (volunteerList.isEmpty()) {
            volunteerList.add("")
            volunteerList
        } else {
            volunteerList
        }
    }

    fun saveLists() {
        BACKGROUND.submit {

        }
    }
}