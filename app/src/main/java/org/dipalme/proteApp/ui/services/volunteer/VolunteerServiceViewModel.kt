package org.dipalme.proteApp.ui.services.volunteer

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.data.Repository
import org.dipalme.proteApp.model.Service
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent

class VolunteerServiceViewModel : ViewModel() {
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    val nextRecyclerViewEvent: SingleLiveEvent<Service> = SingleLiveEvent()
    val doneRecyclerViewEvent: SingleLiveEvent<Service> = SingleLiveEvent()

    fun fillNextRecycler(context: Context) {
        BACKGROUND.submit {
            val user = Repository(context).getCurrentVolunteer()
            var service: Service
            if (user != null) {
                /**
                 * Obtengo una lista con los documentos de ProximosServicios del usuario en cuestion
                 */
                FirebaseFirestore.getInstance().collection("Usuarios")
                    .document("V-${user.indicative}").collection("ProximosServicios").get()
                    .addOnSuccessListener { result ->
                        for (element in result) {
                            /**
                             * Por cada documento encontrado en Proximos, obtengo la informacion de ese servicio
                             */
                            val serviceID = element.get("id servicio").toString()
                            FirebaseFirestore.getInstance().collection("Servicios")
                                .document(serviceID).get().addOnSuccessListener { res ->
                                    service = Service(
                                        res.getString("nombre"),
                                        res.getString("lugar"),
                                        res.getTimestamp("fecha")?.toDate(),
                                        res.id,
                                        res.getDocumentReference("contacto"),
                                        mutableListOf(),
                                        mutableListOf()
                                    )
                                    /**
                                     * Añado el servicio con las listas de voluntarios y vehiculos a la lista que se pasará en el evento
                                     */
                                    nextRecyclerViewEvent.postValue(service)
                                }.addOnFailureListener { errorEvent.postValue(R.string.ER_012) }
                        }
                    }
            }
        }
    }

    fun fillDoneRecycler(context: Context) {
        BACKGROUND.submit {
            val user = Repository(context).getCurrentVolunteer()
            var service: Service
            if (user != null) {
                /**
                 * Obtengo una lista con los documentos de ProximosServicios del usuario en cuestion
                 */
                FirebaseFirestore.getInstance().collection("Usuarios")
                    .document("V-${user.indicative}").collection("ServiciosRealizados").get()
                    .addOnSuccessListener { result ->
                        for (element in result) {
                            /**
                             * Por cada documento encontrado en Proximos, obtengo la informacion de ese servicio
                             */
                            val serviceID = element.get("id servicio").toString()
                            FirebaseFirestore.getInstance().collection("Servicios")
                                .document(serviceID).get().addOnSuccessListener { res ->
                                    service = Service(
                                        res.getString("nombre"),
                                        res.getString("lugar"),
                                        res.getTimestamp("fecha")?.toDate(),
                                        res.id,
                                        res.getDocumentReference("contacto"),
                                        mutableListOf(),
                                        mutableListOf()
                                    )
                                    doneRecyclerViewEvent.postValue(service)
                                }.addOnFailureListener { errorEvent.postValue(R.string.ER_012) }
                        }
                    }
            }
        }
    }
}