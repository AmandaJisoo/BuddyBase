package com.example.buddybase.model

// Email (string), FullName (string), ImageProfilePic (reference), Matched (array), Q_FriendType (array), Q_Music (array), Q_Personality (array), Q_Pet (string), Q_Show (string), Q_Taste (string)
data class NotificationInfo (
//    val User: UserInfo,
//    val Action: String,
    val Description: String,
    var ImageProfilePic: String?
//    val Time: ...time ago, not sure if we want it
)