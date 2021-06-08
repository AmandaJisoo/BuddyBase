package com.example.buddybase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.buddybase.R
import com.example.buddybase.databinding.ItemNotificationBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import io.github.rosariopfernandes.firecoil.load


class NotificationsAdapter(private val notifications: MutableList<String>, private val uid: String,
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
                val firestore = FirebaseFirestore.getInstance()
                val docRef = firestore.collection("Users").document(uid)
                docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                val imgProfilePic = document.data!!["url"]
                                Log.i("weird", "${document.data!!["url"]}")
                                if (imgProfilePic != null) {
                                    val imageRef = storageReference.child(imgProfilePic as String)
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
                                tvNotificationAction.text = notification //puts the user's name and their action together


                            } else {
                                Log.d("rawr", "no such doc")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d("rawr", "get failed with ", exception)
                        }


            }

//            ivOtherProfilePic.setImageResource(notification.User.ImageProfilePic)

        }
    }

    override fun getItemCount(): Int = notifications.size

    class NotificationsViewHolder(val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root)
}