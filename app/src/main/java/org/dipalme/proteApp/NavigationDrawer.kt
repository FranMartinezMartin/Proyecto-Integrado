@file:Suppress("DEPRECATION")

package org.dipalme.proteApp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import org.dipalme.proteApp.data.Repository

class NavigationDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private var viewModel = DrawerViewModel()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initViewModel()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val hView = navView.getHeaderView(0)
        val tvVolName: TextView = hView.findViewById(R.id.tvVolunteerName)
        val tvVolInd: TextView = hView.findViewById(R.id.tvVolunteerIndicative)
        val volunteer = Repository(this).getCurrentVolunteer()
        tvVolName.text = volunteer?.name.toString()
        tvVolInd.text = volunteer?.indicative.toString()

        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, DrawerViewModelFactory()).get(DrawerViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                Repository(this).logoutVolunteer()
                true
            }
            R.id.profile -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_profile)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
            R.id.nav_calendar -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_calendar)
            R.id.nav_contacts -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_contacts)
            R.id.nav_boss_services -> {
                if (viewModel.loadServiceFragment(this) == true) {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.nav_boss_services)
                } else {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.nav_vol_services)
                }
            }
            else -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

