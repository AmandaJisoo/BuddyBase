package com.example.buddybase.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddybase.R
import com.example.buddybase.UserApplication
import com.example.buddybase.adapter.LikedFriendsAdapter
import com.example.buddybase.adapter.NotificationsAdapter
import com.example.buddybase.databinding.FragmentNotificationsBinding
import com.example.buddybase.manager.NotificationManager
import com.example.buddybase.manager.UserManager


class NotificationsFragment : Fragment() {
    lateinit var userApp: UserApplication
    private lateinit var userManager: UserManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNotificationsBinding.inflate(inflater)
        activity?.title = "Notifications"

        with(binding) {
            userApp = activity?.applicationContext as UserApplication
            val notificationManager = userApp.notificationManager
            userManager = userApp.userManager


            val adapter = notificationManager.uid?.let { NotificationsAdapter(notificationManager.notificationList, it, userManager.firebaseStorageReference) }
            rvNotifications.adapter = adapter
        }
        return binding.root
    }

}