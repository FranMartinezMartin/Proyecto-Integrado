@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package org.dipalme.proteApp.ui.services.boss.assign

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.ServicesDataState
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AssignViewModel : ViewModel() {

    val servicesDataState = MutableLiveData<ServicesDataState>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val serviceEvent: SingleLiveEvent<Service> = SingleLiveEvent()
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
                                document.getDocumentReference("contacto"),
                                mutableListOf(),
                                mutableListOf()
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
                    val serviceData = Service(
                        result.getString("nombre"),
                        result.getString("lugar"),
                        result.getTimestamp("fecha")?.toDate(),
                        result.id,
                        result.getDocumentReference("contacto"),
                        mutableListOf(),
                        mutableListOf()
                    )
                    serviceEvent.postValue(serviceData)
                } else {
                    errorEvent.postValue(R.string.ER_010)
                }
            }
        }
    }

    fun volunteersList(service: Service) {
        val volunteerList: MutableList<String> = mutableListOf()
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

    fun vehiclesList() {
        BACKGROUND.submit {
            val vehiclesList: MutableList<String> = mutableListOf()
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
        service: Service,
        volunteers: MutableList<String>,
        vehicles: MutableList<String>
    ) {
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            val volunteersDocument =
                service.name?.let {
                    db.collection("ServiciosAsignados").document("Proximos").collection(it)
                        .document("Voluntarios")
                }
            val vehiclesDocument =
                service.name?.let {
                    db.collection("ServiciosAsignados").document("Proximos").collection(it)
                        .document("Vehiculos")
                }
            try {
                volunteersDocument?.set(hashMapOf("voluntarios" to volunteers))
                vehiclesDocument?.set(hashMapOf("vehiculos" to vehicles))
                saveListsInVolunteers(service, volunteers, vehicles)
            } catch (e: Exception) {
                errorEvent.postValue(R.string.ER_011)
            }
        }
    }

    private fun saveListsInVolunteers(
        service: Service,
        volunteers: MutableList<String>,
        vehicles: MutableList<String>
    ) {
        BACKGROUND.submit {
            val timeStmp = service.date?.let { Timestamp(it) }
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

            val dbVolunteer = FirebaseFirestore.getInstance()
            val db = FirebaseFirestore.getInstance()

            for (elem in volunteers) {
                dbVolunteer.collection("Usuarios").whereEqualTo("nombre", elem).get()
                    .addOnSuccessListener {
                        db.collection("Usuarios").document(it.documents[0].id)
                            .collection("ProximosServicios")
                            .document("${service.name} (${formatter.format(service.date)})").set(
                                hashMapOf(
                                    "fecha" to timeStmp,
                                    "id servicio" to service.id,
                                    "id vehiculo" to vehicles
                                )
                            ).addOnSuccessListener {
                                infoEvent.postValue(R.string.saveListAction)
                                navigationEvent.postValue(NavigationEvent.NavigationAssignAction)
                            }
                    }
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
