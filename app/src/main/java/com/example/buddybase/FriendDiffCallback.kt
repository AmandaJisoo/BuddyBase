package com.example.buddybase

import androidx.recyclerview.widget.DiffUtil
import com.example.buddybase.model.UserInfo

class FriendDiffCallback(private val newFriendList: List<UserInfo>, private val oldFriendList: List<UserInfo>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFriendList.size

    override fun getNewListSize(): Int = newFriendList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newFriend = newFriendList[newItemPosition]
        val oldFriend = oldFriendList[oldItemPosition]
        return newFriend.FullName == oldFriend.FullName && newFriend.Email == oldFriend.Email
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newFriend = newFriendList[newItemPosition]
        val oldFriend = oldFriendList[oldItemPosition]
        return newFriend.Q_FriendType == oldFriend.Q_FriendType && newFriend.Q_Personality == oldFriend.Q_Personality
    }
}