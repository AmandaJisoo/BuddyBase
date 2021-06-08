package com.example.buddybase.model
import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

// This should match Firebase: Users (collection)

// Email (string), FullName (string), ImageProfilePic (reference), Matched (array), Q_FriendType (array), Q_Music (array), Q_Personality (array), Q_Pet (string), Q_Show (string), Q_Taste (string)\

data class UserInfo(
    val Email: String?,
    val FullName: String?,
    var ImageProfilePic: String?,
    var Matched: ArrayList<String>?,
    var Q_FriendType: List<String>?,
    val Q_Music: List<String>?,
    var Q_Personality: List<String>?,
    val Q_Pet: String?,
    val Q_Show: String?,
    val Q_Taste: String?,
    val uid: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Email)
        parcel.writeString(FullName)
        parcel.writeString(ImageProfilePic)
        parcel.writeStringList(Matched)
        parcel.writeStringList(Q_FriendType)
        parcel.writeStringList(Q_Music)
        parcel.writeStringList(Q_Personality)
        parcel.writeString(Q_Pet)
        parcel.writeString(Q_Show)
        parcel.writeString(Q_Taste)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }
}

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