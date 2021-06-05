package com.example.buddybase.adapter

import android.app.Notification
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.databinding.ItemLikedFriendBinding
import com.example.buddybase.databinding.ItemNotificationBinding
import com.example.buddybase.model.NotificationInfo

class NotificationsAdapter(private val notifications: List<NotificationInfo>): RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context))
        return NotificationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val notification = notifications[position]

        with(holder.binding) {
            //TODO: Set profile pic image to imageview
//            ivOtherProfilePic.setImageResource(notification.User.ImageProfilePic)
            tvNotificationAction.text = notification.User.FullName + " " + notification.Action //puts the user's name and their action together
            tvNotificationDescription.text = notification.Description
        }
    }

    override fun getItemCount(): Int = notifications.size

    class NotificationsViewHolder(val binding:ItemNotificationBinding): RecyclerView.ViewHolder(binding.root)
}