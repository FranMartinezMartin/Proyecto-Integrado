package org.dipalme.proteApp.ui.services.boss.assign

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.ServicesDataState
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.model.ServiceData
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AssignViewModel : ViewModel() {

    val servicesDataState = MutableLiveData<ServicesDataState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val serviceEvent: SingleLiveEvent<ServiceData> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val infoEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val volunteerListEvent: SingleLiveEvent<MutableList<String>> = SingleLiveEvent()
    val vehicleListEvent: SingleLiveEvent<MutableList<String>> = SingleLiveEvent()
    val volunteerListViewEvent: SingleLiveEvent<NavigationEvent.AssignActionVolunteer> =
        SingleLiveEvent()
    val vehicleListViewEvent: SingleLiveEvent<NavigationEvent.AssignActionVehicle> =
        SingleLiveEvent()

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

    fun getService(serviceID: String) {
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            db.collection("Servicios").document(serviceID).get().addOnSuccessListener { result ->
                if (result != null) {
                    val serv = ServiceData(
                        result.get("nombre").toString(), result.get("lugar").toString(),
                        result.getTimestamp("fecha")!!.toDate(),
                        result.id,
                        result.getDocumentReference("contacto").toString()
                    )
                    serviceEvent.postValue(serv)
                } else {
                    errorEvent.postValue(R.string.ER_010)
                }
            }
        }
    }

    fun volunteersList(service: ServiceData) {
        var volunteerList: MutableList<String> = mutableListOf()
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            db.collection("Disponibilidad").document(formatter.format(service.date))
                .collection("voluntarios").get()
                .addOnSuccessListener { result ->
                    if (result != null) {
                        for (document in result) {
                            volunteerList.add(document.id)
                        }
                        volunteerListEvent.postValue(volunteerList)
                    } else {
                        errorEvent.postValue(R.string.nullAvailability)
                    }
                }
        }
    }

    fun vehiclesList(service: ServiceData) {
        BACKGROUND.submit {
            var vehiclesList: MutableList<String> = mutableListOf()
            BACKGROUND.submit {
                val db = FirebaseFirestore.getInstance()
                db.collection("Vehiculos").get().addOnSuccessListener { result ->
                    if (result != null) {
                        for (document in result) {
                            vehiclesList.add(document.id)
                        }
                        vehicleListEvent.postValue(vehiclesList)
                    } else {
                        errorEvent.postValue(R.string.nullAvailability)
                    }
                }
            }
        }
    }

    fun saveLists(
        service: ServiceData,
        volunteers: MutableList<String>,
        vehicles: MutableList<String>
    ) {
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()

            val volunteersDocument =
                db.collection("ServiciosAsignados").document("Proximos").collection(service.name).document("Voluntarios")
            val vehiclesDocument =
                db.collection("ServiciosAsignados").document("Proximos").collection(service.name).document("Vehiculos")

            try {
                volunteersDocument.set(hashMapOf("voluntarios" to volunteers))
                vehiclesDocument.set(hashMapOf("vehiculos" to vehicles))
                infoEvent.postValue(R.string.saveListAction)
                navigationEvent.postValue(NavigationEvent.NavigationAssignAction)
            } catch (e: Exception) {
                errorEvent.postValue(R.string.ER_011)
            }
        }

    }

    fun saveVolunteerLists(list: MutableList<String>) {
        BACKGROUND.submit {
            volunteerListViewEvent.postValue(NavigationEvent.AssignActionVolunteer(list))
        }
    }

    fun saveVehicleLists(list: MutableList<String>) {
        BACKGROUND.submit {
            vehicleListViewEvent.postValue(NavigationEvent.AssignActionVehicle(list))
        }
    }
}
