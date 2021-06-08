package com.example.buddybase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.buddybase.R
import com.example.buddybase.UserApplication
import com.example.buddybase.databinding.ItemLikedFriendBinding
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.model.UserInfo
import com.google.firebase.storage.StorageReference
import io.github.rosariopfernandes.firecoil.load

private lateinit var friendManager: FriendManager
private var matchedFriends: MutableList<UserInfo> = mutableListOf()

class LikedFriendsAdapter(private val likedFriends: List<UserInfo>,
                          private val storageRef: StorageReference?,
                          private val application: UserApplication):
        RecyclerView.Adapter<LikedFriendsAdapter.LikedFriendViewHolder>() {

    private lateinit var ivProfilePic: ImageView

    var onRemoveClickListener: (person: UserInfo) -> Unit = {_ ->}


    var onFriendClickListener: (person: UserInfo) -> Unit = {_ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedFriendViewHolder {
        val binding = ItemLikedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return LikedFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedFriendViewHolder, position: Int) {
        val friend = likedFriends[position]

        with(holder.binding) {
            tvFriendName.text = friend.FullName

            // load image
            ivProfilePic = ivFriendProfilePic
            val storageReference = storageRef
            if (storageReference != null) {
                val imgProfilePic = friend.ImageProfilePic
                if (imgProfilePic != null) {
                    val imageRef = storageReference.child(imgProfilePic)
                    ivFriendProfilePic.load(imageRef) {
                        crossfade(true)
                        placeholder(R.mipmap.ic_avatar_placeholder_round)
                        transformations(CircleCropTransformation())
                    }
                } else {
                    ivFriendProfilePic.load(R.mipmap.ic_avatar_placeholder_round) {
                        transformations(CircleCropTransformation())
                    }
                }
            }

            // remove button logic
            friendManager = application.friendManager
            btnRemove.setOnClickListener {
                onRemoveClickListener(friendManager.likedFriends[position])
            }
            itemLikedRoot.setOnClickListener {
                onFriendClickListener(friend)
            }
            btnViewProfile.setOnClickListener {
                onFriendClickListener(friend)
            }
        }
    }



    override fun getItemCount(): Int = likedFriends.size

    class LikedFriendViewHolder(val binding:ItemLikedFriendBinding): RecyclerView.ViewHolder(binding.root)
}