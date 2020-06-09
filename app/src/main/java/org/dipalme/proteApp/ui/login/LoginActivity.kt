@file:Suppress("DEPRECATION")

package org.dipalme.proteApp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.dipalme.proteApp.R
import org.dipalme.proteApp.extension.showErrorDialog
import org.dipalme.proteApp.NavigationDrawer

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
        loadViews()
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

        btLogin.setOnClickListener {
            viewModel.userLog(this, etIndicative.text.toString(), etPassword.text.toString())
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
            this.showErrorDialog(getString(it))
            loading.visibility = View.GONE
        })

        viewModel.navigationEvent.observe(this, Observer {
            val i = Intent(this, NavigationDrawer::class.java)
            startActivity(i)
            finish()
            loading.visibility = View.GONE
        })

        viewModel.firstTimeEvent.observe(this, Observer {
            val i = Intent(this, ChangePassword::class.java)
            i.putExtra("indicative", etIndicative.text.toString())
            i.putExtra("passwd", etPassword.text.toString())
            startActivity(i)
            loading.visibility = View.GONE
        })
    }

    private fun loadViews(){
        etIndicative = findViewById(R.id.indicative)
        etPassword = findViewById(R.id.password)
        btLogin = findViewById(R.id.login_button)
        errorText = findViewById(R.id.tvError)
        viewModel = LoginViewModel()
        loading = findViewById(R.id.vsLoading)
        loading.visibility = View.VISIBLE
    }
}
