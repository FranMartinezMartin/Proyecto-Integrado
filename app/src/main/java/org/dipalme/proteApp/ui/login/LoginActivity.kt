@file:Suppress("DEPRECATION")

package org.dipalme.proteApp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showErrorDialog
import org.dipalme.proteApp.navigation_drawer

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var etIndicative: EditText
    private lateinit var etPassword: EditText
    private lateinit var btLogin: Button
    private lateinit var loading: ViewStub
    private lateinit var errorText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etIndicative = findViewById(R.id.indicative)
        etPassword = findViewById(R.id.password)
        btLogin = findViewById(R.id.login_button)
        loading = findViewById(R.id.vsLoading)
        errorText = findViewById(R.id.tvError)
        viewModel = LoginViewModel()
        initViewModel()

        etIndicative.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validateIndicative(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, st: Int, co: Int, af: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, bef: Int, co: Int) {}
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.validatePassword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, st: Int, co: Int, af: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, bef: Int, co: Int) {}
        })

        /**
         * Sing-in button functionality
         */
        btLogin.setOnClickListener {
            viewModel.UserLog(this, etIndicative.text.toString(), etPassword.text.toString())
            loading.visibility = View.VISIBLE
        }
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, LoginViewModelFactory()).get(LoginViewModel::class.java)

        viewModel.checkLoggedUser(this)

        viewModel.loginDataState.observe(this, Observer {
            btLogin.isEnabled = it.validIndicative == true && it.validPassword == true
            when (it.validIndicative) {
                null -> errorText.visibility = View.INVISIBLE
                false -> {
                    errorText.visibility = View.VISIBLE
                    errorText.setText(R.string.ER_003)
                }
            }
            if (it.validPassword == false) {
                errorText.visibility = View.VISIBLE
                errorText.setText(R.string.ER_003)
            } else {
                errorText.visibility = View.INVISIBLE
            }
            loading.visibility = View.GONE
        })

        viewModel.errorEvent.observe(this, Observer {
            this.showErrorDialog(it)
        })

        viewModel.navigationEvent.observe(this, Observer {
            val i = Intent(this, navigation_drawer::class.java)
            startActivity(i)
            finish()
            loading.visibility = View.GONE
        })
    }
}
