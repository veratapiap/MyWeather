package com.patricioveratapia.myweather.weather.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.patricioveratapia.myweather.R


class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(HomeFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu items for use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.action_select_city, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        return when (item.itemId) {
            R.id.action_change_location -> {

                val fragment = supportFragmentManager.findFragmentById(R.id.activity_base_fragment_container)

                (fragment as HomeFragment).showCitySelectionDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
