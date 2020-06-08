package org.dipalme.proteApp.ui.home

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.model.News
import org.dipalme.proteApp.ui.liveEvents.SingleLiveEvent

class HomeViewModel : ViewModel() {
    val navigationEvent: SingleLiveEvent<MutableList<News>> = SingleLiveEvent()
    val errorEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    fun loadRecycler() {
        BACKGROUND.submit {
            val db = FirebaseFirestore.getInstance()
            db.collection("Noticias").get().addOnSuccessListener { result ->
                var data = mutableListOf<News>()
                if (result != null) {
                    for (elem in result) {
                        val news = News(
                            elem.get("imagen").toString(),
                            elem.get("url").toString(),
                            elem.id
                        )
                        data.add(news)
                    }
                    navigationEvent.postValue(data)
                } else {
                    errorEvent.postValue(R.string.ER_015)
                }
            }.addOnFailureListener {

            }
        }
    }

}