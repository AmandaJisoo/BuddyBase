package com.example.buddybase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.databinding.ItemLikedFriendBinding
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.model.UserInfo

private lateinit var friendManager: FriendManager
private var matchedFriends: MutableList<UserInfo> = mutableListOf()

class LikedFriendsAdapter(private val likedFriends: List<UserInfo>): RecyclerView.Adapter<LikedFriendsAdapter.LikedFriendViewHolder>() {


    var onFriendClickListener: (person: UserInfo) -> Unit = {_ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedFriendViewHolder {
        val binding = ItemLikedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return LikedFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedFriendViewHolder, position: Int) {
        val friend = likedFriends[position]

        with(holder.binding) {
            tvFriendName.text = friend.FullName

            itemLikedRoot.setOnClickListener {
                onFriendClickListener(friend)
            }
        }
    }



    override fun getItemCount(): Int = likedFriends.size

    class LikedFriendViewHolder(val binding:ItemLikedFriendBinding): RecyclerView.ViewHolder(binding.root)
}