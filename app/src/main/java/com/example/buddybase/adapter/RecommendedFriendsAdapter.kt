package com.example.buddybase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.R
import com.example.buddybase.databinding.ItemRecommendedFriendBinding
import com.example.buddybase.model.UserInfo
import com.google.gson.Gson
import org.json.JSONObject

class RecommendedFriendsAdapter(private val listOfFriends: Map<String, Any>): RecyclerView.Adapter<RecommendedFriendsAdapter.FriendsViewHolder>() {

    private var matchedFriends: MutableList<UserInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemRecommendedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        for ((userName, value) in listOfFriends) {
            val gson = Gson()
            val userInfo = gson.fromJson(JSONObject(value as Map<String, Any>).toString(), UserInfo::class.java)
            matchedFriends.add(userInfo)
            Log.i("userInfo", userName)
        }

        val friend = matchedFriends[position]
        with(holder.binding) {
            tvFriendName.text = friend.FullName
            val friendType = friend.Q_FriendType.joinToString { it }
            tvFriendPersonality.text = root.context.getString(
                R.string.recommended_friends_friend_type_format,
                friend.FullName,
                friendType
            )
            // TODO: Parse ImageProfilePic as readable URI string

            btnLike.setOnClickListener{

            }
        }
    }

    override fun getItemCount(): Int = listOfFriends.size

    class FriendsViewHolder(val binding: ItemRecommendedFriendBinding): RecyclerView.ViewHolder(binding.root)

}