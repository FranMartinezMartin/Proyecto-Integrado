package org.dipalme.proteApp.ui.services.boss.assign

import android.content.Context
import android.util.Log
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
import org.dipalme.proteApp.ui.contacts.ContactsAdapter
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
            var list: MutableList<Service> = mutableListOf()
            val db = FirebaseFirestore.getInstance()
            db.collection("Servicios").get().addOnSuccessListener { result ->
                if (result != null) {
                    val formatter = SimpleDateFormat("dd/MM/yyyy")
                    for (document in result) {
                        val serviceDate = formatter.format(document.getTimestamp("fecha")?.toDate())
                        if (serviceDate.equals(formatter.format(dateMilis))) {
                            val service: Service = Service(
                                null,
                                null,
                                document.getString("nombre"),
                                document.getString("lugar"),
                                document.getTimestamp("fecha")!!.toDate(),
                                document.id,
                                document.getDocumentReference("parteRev").toString(),
                                document.getDocumentReference("parteServ").toString(),
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
}