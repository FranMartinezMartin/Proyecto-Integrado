package org.dipalme.proteApp.ui.contacts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.dipalme.proteApp.NavigationEvent
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.model.Contact
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent

class ContactsViewModel : ViewModel() {
    val navigationEvent: SingleLiveEvent<NavigationEvent> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    fun loadRecicler(recycler: RecyclerView, context: Context) {
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            var list: MutableList<Contact> = mutableListOf()
            db.collection("Contactos").get().addOnSuccessListener { result ->
                if (result != null) {
                    for (document in result) {
                        val contact: Contact = Contact(
                            document.getString("nombre"),
                            document.getString("telefono"),
                            document.id
                        )
                        list.add(contact)
                    }
                    recycler.layoutManager = LinearLayoutManager(context)
                    recycler.adapter = ContactsAdapter(list)
                    navigationEvent.postValue(NavigationEvent.NavigationContact)
                } else {
                    errorEvent.postValue(R.string.ER_007)
                }
            }.addOnFailureListener { errorEvent.postValue(R.string.ER_1001) }
        }
    }
}