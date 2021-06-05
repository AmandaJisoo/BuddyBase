package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.ActivityRecommendedFriendsBinding
import com.example.buddybase.manager.UserManager
import com.example.buddybase.model.UserInfo
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import org.json.JSONObject

class RecommendedFriendsActivity : AppCompatActivity() {
    // IMPORTANT:
    // This is not useful. Its logic has been moved to RecommendedFriendsFragment.
    // This is just for reference.











//    lateinit var db: FirebaseFirestore
//    lateinit var manager: UserManager
//    lateinit var userApp: UserApplication
//    lateinit var docRef: DocumentReference
//    lateinit var binding: ActivityRecommendedFriendsBinding
//    private var matchedFriends: MutableList<UserInfo> = mutableListOf()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityRecommendedFriendsBinding.inflate(layoutInflater).apply { setContentView(root) }
//        userApp = this.applicationContext as UserApplication
//        manager = this.userApp.userManager
//
//        db = FirebaseFirestore.getInstance()
//
//        //TODO: for now document("aBhsZO73GMe1a09xrMUemyZJB2q1") is hardcoded because this activity isn't linked to the signup/login flow
//        docRef = db.collection("Users").document("aBhsZO73GMe1a09xrMUemyZJB2q1")
//        docRef.get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        var matchedWith = document.data!!["Matched"] as List<String>
//                        val mapOfMatches = getMapOfMatchedUsers(matchedWith, binding)
//                        Log.i("forLeo", "expecting a map that's not empty below")
//                        Log.i("forLeo", "$mapOfMatches") //logs "{}"
//                    } else {
//                        Log.i("forLeo", "error doc doesn't exist")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.i("forLeo", "something wrong:", exception)
//                }
//    }
//
//    private fun getMapOfMatchedUsers(matched: List<String>, binding: ActivityRecommendedFriendsBinding): MutableMap<Any?, Any> {
//        val docRef = db.collection("Users")
//        var size = matched.size
//        val mapOfMatches = mutableMapOf<Any?, Any>()
//        matched.forEach {
//            docRef.document(it).get()
//                    .addOnSuccessListener { document ->
//                        if (document != null) {
//                            size--
//                            mapOfMatches[document.data!!["FullName"]] = document.data!!
//                            if (size == 0) {
//                                Log.i("forLeo", "$mapOfMatches")
//
//                                val matches = mapOfMatches as Map<String, Any>
//                                for ((userName, value) in matches) {
//                                    val gson = Gson()
//                                    val friendInfo = value as Map<String, Any>
//                                    val userInfo = gson.fromJson(JSONObject(friendInfo).toString(), UserInfo::class.java)
//                                    val profilePicRef = (friendInfo["ImageProfilePic"] as com.google.firebase.firestore.DocumentReference).path
//                                    userInfo.ImageProfilePic = profilePicRef
//                                    matchedFriends.add(userInfo)
//                                }
//                                val adapter = RecommendedFriendsAdapter(matchedFriends)
//                                binding.rvRecommendedFriends.adapter = adapter
//                            }
//                        } else {
//                            Log.i("forLeo", "could not find user doc")
//                        }
//                    }
//                    .addOnFailureListener { exception ->
//                        Log.i("forLeo", "setMatched failed with ", exception)
//                    }
//        }
//        return mapOfMatches
//    }
}