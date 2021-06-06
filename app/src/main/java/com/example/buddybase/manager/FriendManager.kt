package com.example.buddybase.manager

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.buddybase.FriendDiffCallback
import com.example.buddybase.model.UserInfo
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


class FriendManager {
    private lateinit var newLikedFriend: UserInfo
    var recommendedFriends = mutableListOf<UserInfo>()
        private set
//    private lateinit var likedFriends: List<UserInfo>

//    var likedFriends = mutableListOf<UserInfo>()
    var likedFriends = mutableListOf<UserInfo>()
        private set

    fun onLikeClick(friend: UserInfo) {
        newLikedFriend = friend
        likedFriends.add(newLikedFriend)
//        delay(5, TimeUnit.MILLISECONDS)
        this.recommendedFriends.remove(friend)
//        Log.i("likedFriends", "added")
    }


    fun loadRecommendedFriends(data: List<UserInfo>) {
        this.recommendedFriends = data as MutableList<UserInfo>
    }

    fun onRecommendRemoveClick(friend: UserInfo) {
        this.recommendedFriends.remove(friend)
//        val callback = FriendDiffCallback(oldFriendList, recommendedFriends)
//        val result = DiffUtil.calculateDiff(callback)
//        result.dispatchUpdatesTo()
//        val oldFriendList = this.recommendedFriends
    }

    fun onLikedRemoveClick(friend: UserInfo) {
        this.likedFriends.remove(friend)
    }
}