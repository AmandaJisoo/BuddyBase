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

    var likedFriends = mutableListOf<UserInfo>()
        private set

    fun onLikeClick(friend: UserInfo) {
        newLikedFriend = friend
        likedFriends.add(newLikedFriend)
        this.recommendedFriends.remove(friend)
    }


    fun loadRecommendedFriends(data: MutableList<UserInfo>, userManager: UserManager) {
        this.recommendedFriends = data
        // remove current user from list
        val itr = recommendedFriends.toList().iterator()
        while (itr.hasNext()) {
            val matchedFriend = itr.next()
            if(matchedFriend.uid == userManager.uid) {
                matchedFriend.FullName?.let { userManager.setFullName(it) }
                recommendedFriends.remove(matchedFriend)
            }
        }
    }

    fun onRecommendRemoveClick(friend: UserInfo) {
        this.recommendedFriends.remove(friend)
    }

    fun onLikedRemoveClick(friend: UserInfo) {
        this.recommendedFriends.remove(friend)
        this.likedFriends.remove(friend)
    }
}