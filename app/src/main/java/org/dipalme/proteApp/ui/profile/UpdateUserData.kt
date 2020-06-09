@file:Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package org.dipalme.proteApp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showErrorDialog
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class UpdateUserData : Fragment() {
    private lateinit var thisView: View
    private lateinit var tvName: EditText
    private lateinit var tvPhone: EditText
    private lateinit var tvEmail: EditText
    private lateinit var tvBirthday: EditText
    private lateinit var cbDriver: CheckBox
    private lateinit var loading: ViewStub
    private lateinit var button: Button
    private lateinit var clic: View
    private var viewModel = ProfileViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisView = inflater.inflate(R.layout.fragment_update_user_data, container, false)
        loadViews()
        initViewModel()
        viewModel.getUserData(thisView.context)

        clic.setOnClickListener {
            activity?.supportFragmentManager?.let { it1 ->
                viewModel.showDatePickerDialog(
                    thisView,
                    it1
                )
            }
        }

        button.setOnClickListener {
            loading.visibility = View.VISIBLE
            viewModel.saveData(thisView)
        }
        return thisView
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, ProfileViewModelFactory()).get(ProfileViewModel::class.java)

        viewModel.loadDataEvent.observe(this, Observer { vol ->
            tvName.setText(vol.name)
            tvPhone.setText(vol.phone)
            tvEmail.setText(vol.mail)
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            tvBirthday.setText(formatter.format(vol.birthday?.toDate()))
            if (vol.driver == true)
                cbDriver.isChecked = true

            loading.visibility = View.GONE
        })
        viewModel.navigationEvent.observe(this, Observer {
            thisView.context.showErrorDialog(getString(R.string.succesfulUpdate))
            loading.visibility = View.GONE
        })
        viewModel.errorEvent.observe(this, Observer {
            thisView.context.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })
    }

    private fun loadViews() {
        tvName = thisView.findViewById(R.id.tvName)
        tvPhone = thisView.findViewById(R.id.tvPhone)
        tvEmail = thisView.findViewById(R.id.tvEmail)
        tvBirthday = thisView.findViewById(R.id.tvBirthday)
        cbDriver = thisView.findViewById(R.id.cbDriverLicense)
        button = thisView.findViewById(R.id.btnUpdateData)
        loading = thisView.findViewById(R.id.vsLoading)
        clic = thisView.findViewById(R.id.view2)
        loading.visibility = View.VISIBLE
    }
}