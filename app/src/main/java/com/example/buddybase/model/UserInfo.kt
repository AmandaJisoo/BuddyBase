package com.example.buddybase.model

// This should match Firebase: Users (collection)

// Email (string), FullName (string), ImageProfilePic (reference), Matched (array), Q_FriendType (array), Q_Music (array), Q_Personality (array), Q_Pet (string), Q_Show (string), Q_Taste (string)
data class UserInfo (
    val Email: String,
    val FullName: String,
    val ImageProfilePic: String,
    val Matched: List<String>,
    val Q_FriendType: List<String>,
    val Q_Music: List<String>,
    val Q_Personality: List<String>,
    val Q_Pet: String,
    val Q_Show: String,
    val Q_Taste: String
)

//Raiki Lim={
//    Q_Pet=Dogs,
//    Q_Show=Comedy,
//    Matched=[Bill Gates, Ken Jeong],
//    Email=raiki.lim@gmail.com,
//    Q_Personality=[Organized, Quiet],
//    Q_FriendType=[Quiet, Outgoing],
//    FullName=Raiki Lim,
//    Q_Taste=Savory,
//    ImageProfilePic=com.google.firebase.firestore.DocumentReference@69c5e655,
//    Q_Music=[Classical]
//}