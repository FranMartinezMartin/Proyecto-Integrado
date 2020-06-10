package org.dipalme.proteApp.data

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import org.dipalme.proteApp.model.Volunteer
import org.dipalme.proteApp.ui.login.LoginActivity

class Repository(contextReceived: Context) {
    private val PERSON_KEY = "PERSON_KEY"
    private val context = contextReceived
    private val PREFS_FILENAME = "org.dipalme.proteApp.prefs"

    fun setCurrentVolunteer(volunteer: Volunteer) {
        val edit: SharedPreferences.Editor = context.getSharedPreferences(PREFS_FILENAME, 0).edit()
        val json = Gson().toJson(volunteer)
        edit.putString(PERSON_KEY, json).apply()
    }

    fun getCurrentVolunteer(): Volunteer? {
        val json: String? =
            context.getSharedPreferences(PREFS_FILENAME, 0).getString(PERSON_KEY, null)
        return Gson().fromJson(json, Volunteer::class.java) ?: return null
    }

    fun logoutVolunteer() {
        BACKGROUND.submit {
            val edit: SharedPreferences.Editor =
                context.getSharedPreferences(PREFS_FILENAME, 0).edit()
            edit.remove(PERSON_KEY).apply()
            val i = Intent(context, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            ContextCompat.startActivity(context, i, null)
        }
    }
}