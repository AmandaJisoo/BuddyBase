package com.example.buddybase.manager

import android.util.Log
import com.example.buddybase.model.NotificationInfo
import com.example.buddybase.model.UserInfo

class NotificationManager {
    var user: UserInfo? = null
        private set
    var action: String? = null
        private set
    var description: String? = null
        private set

    private lateinit var newNotification: NotificationInfo

    fun setNewNotification(user: UserInfo, action: String, description: String) {
        this.user = user
        this.action = action
        this.description = description
    }




    var notificationList = mutableListOf<NotificationInfo>()
        private set

    fun addNewNotification(notification: NotificationInfo) {
        newNotification = notification
        notificationList.add(newNotification)
        Log.i("notificationList", "new notification added")
    }
}