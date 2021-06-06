package com.example.buddybase

import android.app.Application
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.manager.NotificationManager
import com.example.buddybase.manager.UserManager
import com.example.buddybase.model.UserInfo
//import com.google.firebase.firestore.auth.UserInfo

class UserApplication : Application() {
    lateinit var userManager: UserManager
//    lateinit var recommendedFriends: List<UserInfo>
//    lateinit var likedFriends: List<UserInfo>
    lateinit var friendManager: FriendManager
    lateinit var notificationManager: NotificationManager

    // TODO: list of Notification


    override fun onCreate() {
        super.onCreate()
        this.friendManager = FriendManager()

        this.userManager = UserManager()

        this.notificationManager = NotificationManager()

        // TODO: init list of recommendedFriends
//        this.recommendedFriends =

    }
}