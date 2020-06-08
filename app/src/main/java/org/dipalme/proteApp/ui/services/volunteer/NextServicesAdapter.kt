package org.dipalme.proteApp.ui.services.volunteer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.dipalme.proteApp.R
import org.dipalme.proteApp.data.BACKGROUND
import org.dipalme.proteApp.model.Service
import java.text.SimpleDateFormat
import java.util.*

class NextServicesAdapter(private val services: MutableList<Service>, contextPassed: Context) :
    RecyclerView.Adapter<NextServicesAdapter.ServicesHolder>() {

    private val context = contextPassed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesHolder {
        return ServicesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_services, parent, false
            )
        )
    }

    override fun getItemCount(): Int = services.size

    override fun onBindViewHolder(holder: ServicesHolder, position: Int) {
        val service = services[position]

        holder.tvServiceName.text = service.name.toString()
        holder.placeName.text = service.place.toString()

        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        holder.date.text = formatter.format(service.date!!)

        /**
         * Obtengo de ServiciosAsignados la lista con los voluntarios y vehiculos que añado al servicio despues
         */
        val db = FirebaseFirestore.getInstance()
            .collection("ServiciosAsignados").document("Proximos")
        if (service.volunteers != null) {
            BACKGROUND.submit {
                db.collection(service.name.toString()).document("Voluntarios")
                    .get().addOnSuccessListener { r ->
                        val data: List<String> = r.get("voluntarios") as List<String>
                        for (d in data) {
                            service.volunteers.add(d)
                        }
                        holder.volunteerRec.layoutManager = LinearLayoutManager(context)
                        holder.volunteerRec.adapter = NextSubAdapter(service.volunteers)
                    }
            }
        }
        if (service.vehicles != null) {
            BACKGROUND.submit {
                db.collection(service.name.toString()).document("Vehiculos")
                    .get().addOnSuccessListener { r ->
                        val data: List<String> = r.get("vehiculos") as List<String>
                        for (d in data) {
                            service.vehicles.add(d)
                        }
                        holder.vehicleRec.layoutManager = LinearLayoutManager(context)
                        holder.vehicleRec.adapter = NextSubAdapter(service.vehicles)
                    }
            }
        }

    }

    class ServicesHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvServiceName: TextView = itemView.findViewById(R.id.tvServiceName)
        val placeName: TextView = itemView.findViewById(R.id.tvPlaceName)
        val date: TextView = itemView.findViewById(R.id.date)
        val frame = itemView.findViewById<View>(R.id.frame)
        val volunteerRec: RecyclerView = itemView.findViewById(R.id.volunteersList)
        val vehicleRec: RecyclerView = itemView.findViewById(R.id.vehiclesList)

    }
}