package com.example.buddybase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.databinding.ItemRecommendedFriendBinding

class RecommendedFriendsAdapter(private val listOfFriends: List<String>): RecyclerView.Adapter<RecommendedFriendsAdapter.FriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemRecommendedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val friend = listOfFriends[position]
        with(holder.binding) {
            tvFriendName.text = friend
        }
    }

    override fun getItemCount(): Int = listOfFriends.size

    class FriendsViewHolder(val binding: ItemRecommendedFriendBinding): RecyclerView.ViewHolder(binding.root)

}