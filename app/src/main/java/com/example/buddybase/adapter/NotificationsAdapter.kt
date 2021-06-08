package com.example.buddybase.adapter

import android.app.Notification
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.buddybase.R
import com.example.buddybase.databinding.ItemLikedFriendBinding
import com.example.buddybase.databinding.ItemNotificationBinding
import com.example.buddybase.model.NotificationInfo
import com.google.firebase.storage.StorageReference
import io.github.rosariopfernandes.firecoil.load

class NotificationsAdapter(private val notifications: List<NotificationInfo>,
                            private val storageRef: StorageReference?):
        RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {

    private lateinit var ivProfilePic: ImageView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context))
        return NotificationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val notification = notifications[position]

        with(holder.binding) {
            ivProfilePic = ivOtherProfilePic
            val storageReference = storageRef
            if (storageReference != null) {
                val imgProfilePic = notification.ImageProfilePic
                if (imgProfilePic != null) {
                    val imageRef = storageReference.child(imgProfilePic)
                    ivOtherProfilePic.load(imageRef) {
                        crossfade(true)
                        placeholder(R.mipmap.ic_avatar_placeholder_round)
                        transformations(CircleCropTransformation())
                    }
                } else {
                    ivOtherProfilePic.load(R.mipmap.ic_avatar_placeholder_round) {
                        transformations(CircleCropTransformation())
                    }
                }
            }

//            ivOtherProfilePic.setImageResource(notification.User.ImageProfilePic)
            tvNotificationAction.text = notification.User.FullName + " " + notification.Action //puts the user's name and their action together
//            tvNotificationDescription.text = notification.Description
        }
    }

    override fun getItemCount(): Int = notifications.size

    class NotificationsViewHolder(val binding:ItemNotificationBinding): RecyclerView.ViewHolder(binding.root)
}