package com.example.buddybase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddybase.R
import com.example.buddybase.UserApplication
import com.example.buddybase.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {
    lateinit var userApp: UserApplication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNotificationsBinding.inflate(inflater)
        activity?.title = "Notifications"
        with(binding) {
            userApp = activity?.applicationContext as UserApplication

            // TODO: notifications need to be put in RV
//            val adapter = Notifications
//            rvNotifications.adapter = adapter
        }
        return binding.root
    }
}