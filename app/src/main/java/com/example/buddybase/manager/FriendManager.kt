package com.example.buddybase.manager

import android.util.Log
import com.example.buddybase.model.UserInfo

class FriendManager {
    private lateinit var newLikedFriend: UserInfo
    var likedFriends = mutableListOf<UserInfo>()
        private set

    fun onLikeClick(friend: UserInfo) {
        newLikedFriend = friend
        likedFriends.add(newLikedFriend)
        Log.i("likedFriends", "added")
    }
}