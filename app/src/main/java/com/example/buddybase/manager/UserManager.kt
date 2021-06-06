package com.example.buddybase.manager

import com.google.firebase.storage.StorageReference

class UserManager {
    var email: String? = null
        private set
    var fullName: String? = null
        private set
    var uid: String? = null
        private set
    var matchedUids: List<String>? = null
        private set
    var matchedUsers: Map<String, Any>? = null
        private set

    var firebaseStorageReference: StorageReference ?= null
        private set

    fun setStorageRef(firebaseStorageReference: StorageReference) {
        this.firebaseStorageReference = firebaseStorageReference
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setFullName(fullName: String) {
        this.fullName = fullName
    }

    fun setUid(uid: String) {
        this.uid = uid
    }

    fun setMatchedUids(matched: List<String>) {
        this.matchedUids = matched
    }

    fun setMatchedUsers(matched: Map<String, Any>) {
        this.matchedUsers = matched
    }
}