package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.findNavController
import com.example.buddybase.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater).apply { setContentView(root) }
        with(binding) {
            // Hide ActionBar
            supportActionBar?.hide()

            // disable auto color tint
            navBarView.itemIconTintList = null

            navBarView.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.nav_bar_home -> navController.navigate(NavGraphHomeDirections.actionGlobalRecommendedFriendsFragment())
                    R.id.nav_bar_liked -> navController.navigate(NavGraphHomeDirections.actionGlobalLikedFriendsFragment())
                    // TODO: add account page
                    R.id.nav_bar_account -> Toast.makeText(
                        this@HomeActivity,
                        "Not yet implemented!",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.nav_bar_notification -> navController.navigate(NavGraphHomeDirections.actionGlobalNotificationsFragment())
                }
                true
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.home_menu_items, menu)
//
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

        }

        return super.onOptionsItemSelected(item)
    }
}