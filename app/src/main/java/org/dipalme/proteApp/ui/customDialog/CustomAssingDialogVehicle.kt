package org.dipalme.proteApp.ui.customDialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_assign_dialog.view.*
import org.dipalme.proteApp.R
import org.dipalme.proteApp.ui.services.boss.assign.AssignViewModel

class CustomAssingDialogVehicle(list: MutableList<String>, viewModel: AssignViewModel) : DialogFragment() {
    private lateinit var recycler: RecyclerView
    private val listOfElements: MutableList<String> = list
    private val vm = viewModel
    private lateinit var adapter: CustomAssignDialogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.custom_assign_dialog, container, false)

        recycler = root.findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        adapter = CustomAssignDialogAdapter(listOfElements)
        recycler.adapter = adapter

        root.btAccept.setOnClickListener {
            Log.w("Lista de voluntarios: ", adapter.getList().toString())
            vm.saveVehicleLists(adapter.getList())
            root.visibility = View.INVISIBLE
            dismiss()
        }
        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}