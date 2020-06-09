@file:Suppress("DEPRECATION")

package org.dipalme.proteApp.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_change_password.*
import org.dipalme.proteApp.R
import org.jetbrains.anko.alert

class ChangePassword : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var actualPass: TextInputEditText
    private lateinit var newPass: TextInputEditText
    private lateinit var newPassRepeated: TextInputEditText
    private lateinit var loading: ViewStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        loadViews()
        initViewModel()

        actualPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (actualPass.text.toString() != intent.getStringExtra("passwd")) {
                    actualPass.error = getString(R.string.invalid_password)
                } else {
                    actualPass.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, st: Int, co: Int, af: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, bef: Int, co: Int) {}
        })

        newPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validatePasswordChange(s.toString())
                if (viewModel.loginDataState.value?.updatePass == false) {
                    newPass.error = getString(R.string.passChangeInvalid)
                } else {
                    newPass.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, st: Int, co: Int, af: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, bef: Int, co: Int) {}
        })

        newPassRepeated.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validatePasswordChangeRepeat(s.toString())
                if (viewModel.loginDataState.value?.updatePassRepeat == false) {
                    newPassRepeated.error = getString(R.string.passChangeInvalid)
                } else {
                    newPassRepeated.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, st: Int, co: Int, af: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, bef: Int, co: Int) {}
        })

        btnUpdate.setOnClickListener {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            viewModel.updatePassword(intent.getStringExtra("indicative"), newPass.text.toString(), newPassRepeated.text.toString())
        }
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, LoginViewModelFactory()).get(LoginViewModel::class.java)

        viewModel.loginDataState.observe(this, Observer {
            btnUpdate.isEnabled = it.updatePass == true && it.updatePassRepeat == true
            loading.visibility = View.GONE
        })

        viewModel.updateEvent.observe(this, Observer {
            loading.visibility = View.GONE
            finish()
        })
        viewModel.errorEvent.observe(this, Observer {
            loading.visibility = View.GONE
            alert {
                message(getString(it))
                positiveButton(R.string.accept) {
                    finish()
                }
            }.show()
        })
    }

    private fun loadViews() {
        actualPass = findViewById(R.id.etOldPass)
        newPass = findViewById(R.id.etNewPass)
        newPassRepeated = findViewById(R.id.etNewPassRepeat)
        viewModel = LoginViewModel()
        loading = findViewById(R.id.vsLoading)
        loading.visibility = View.VISIBLE
    }
}
