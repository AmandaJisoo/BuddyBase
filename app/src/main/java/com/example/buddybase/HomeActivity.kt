package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.findNavController
import com.example.buddybase.databinding.ActivityHomeBinding
import com.example.buddybase.manager.UserManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val navController by lazy { findNavController(R.id.navHost) }
    private val userApp: UserApplication by lazy { application as UserApplication }
    private lateinit var manager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater).apply { setContentView(root) }
        this.manager = userApp.userManager

        with(binding) {
            // Hide ActionBar
            supportActionBar?.hide()

            // disable auto color tint
            navBarView.itemIconTintList = null

            navBarView.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.nav_bar_home -> navController.navigate(NavGraphHomeDirections.actionGlobalRecommendedFriendsFragment())
                    R.id.nav_bar_liked -> navController.navigate(NavGraphHomeDirections.actionGlobalLikedFriendsFragment())
                    R.id.nav_bar_account -> navController.navigate(NavGraphHomeDirections.actionGlobalProfileFragment())
                    R.id.nav_bar_notification -> navController.navigate(NavGraphHomeDirections.actionGlobalNotificationsFragment())
                }
                true
            }
        }

        Log.i("godsuya", "${manager.matchedUids}")
        for (uid in manager.matchedUids!!) {
            Log.i("godsuya", "$uid")
            Firebase.messaging.unsubscribeFromTopic("/topics/$uid")
        }


        Firebase.messaging.subscribeToTopic("/topics/${manager.uid}")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.i("godsuya", msg)
                Log.i("godsuya", "${manager.uid}")
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