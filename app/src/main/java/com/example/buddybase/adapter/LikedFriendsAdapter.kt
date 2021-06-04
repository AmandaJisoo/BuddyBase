package com.example.buddybase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.databinding.ItemLikedFriendBinding
import com.example.buddybase.model.UserInfo

class LikedFriendsAdapter(private val likedFriends: List<UserInfo>): RecyclerView.Adapter<LikedFriendsAdapter.LikedFriendViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedFriendViewHolder {
        val binding = ItemLikedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return LikedFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedFriendViewHolder, position: Int) {
        val friend = likedFriends[position]

        with(holder.binding) {
            tvFriendName.text = friend.FullName
        }
    }

    override fun getItemCount(): Int = likedFriends.size

    class LikedFriendViewHolder(val binding:ItemLikedFriendBinding): RecyclerView.ViewHolder(binding.root)
}