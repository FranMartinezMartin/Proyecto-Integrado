package org.dipalme.proteApp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.nav_header_navigation_drawer.*
import org.dipalme.proteApp.data.Repository

class navigation_drawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        var hView = navView.getHeaderView(0)
        var tvVolName: TextView = hView.findViewById(R.id.tvVolunteerName)
        var tvVolInd: TextView = hView.findViewById(R.id.tvVolunteerIndicative)
        var volunteer = Repository(this).getCurrentVolunteer()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                Repository(this).logoutVolunteer()
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
            R.id.nav_maps -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_maps)
            R.id.nav_manuals -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_manuals)
            R.id.nav_services -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_services)
            else -> findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

