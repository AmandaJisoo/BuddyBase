package com.example.buddybase.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.buddybase.HomeActivity
import com.example.buddybase.ProfileActivity
import com.example.buddybase.UserApplication
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.FragmentRecommendedFriendsBinding
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.manager.UserManager
import com.example.buddybase.model.UserInfo
import com.example.buddybase.startProfileActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import org.json.JSONObject

class RecommendedFriendsFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    lateinit var manager: UserManager
    lateinit var userApp: UserApplication
//    lateinit var matchedWith: List<String>

    private lateinit var friendManager: FriendManager
    private lateinit var matchedFriends: MutableList<UserInfo>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecommendedFriendsBinding.inflate(inflater)
        activity?.title = "Recommended Friends"

        userApp = activity?.applicationContext as UserApplication
        this.friendManager = userApp.friendManager

        manager = this.userApp.userManager

        loadRecommendedFriends(binding)
        with(binding) {

            // swipe to refresh
            srlRefreshFriendList.setOnRefreshListener {

//                getMapOfMatchedUsers(matchedWith, binding)
                loadRecommendedFriends(binding)
                srlRefreshFriendList.isRefreshing = false
            }
        }

        return binding.root
    }

    private fun loadRecommendedFriends(binding: FragmentRecommendedFriendsBinding) {
        val mapOfMatches = manager.matchedUsers
        val matches = mapOfMatches as Map<String, Any>
        matchedFriends = mutableListOf()
        for ((userName, value) in matches) {
            val gson = Gson()
            var friendInfo = value as MutableMap<String, Any>
//            FirebaseStorage
            if (friendInfo["ImageProfilePic"] != null) {
                friendInfo["ImageProfilePic"] =  (friendInfo["ImageProfilePic"] as DocumentReference).path
                Log.i("currentUser", friendInfo["ImageProfilePic"].toString())
            }

//            Log.i("fbsr", "${manager.firebaseStorageReference}")

//            if (imgProfilePic != null) {
//                friendInfo["ImageProfilePic"] =  imgProfilePic as DocumentReference
//            }
//            friendInfo["ImageProfilePic"] =
            val userInfo = gson.fromJson(JSONObject(friendInfo as Map<String, Any>).toString(), UserInfo::class.java)
//            val imgProfilePic = friendInfo["ImageProfilePic"]

//            friendInfo["ImageProfilePic"] as DocumentReference
            Log.i("userInfosss", "${userInfo}")
            matchedFriends.add(userInfo)
        }
        friendManager.loadRecommendedFriends(matchedFriends)
        val adapter = RecommendedFriendsAdapter(friendManager.recommendedFriends, manager.firebaseStorageReference)
        adapter.onLikeClickListener = { friend ->
            friendManager.onLikeClick(friend)
        }
        adapter.onRemoveClickListener = { friend ->
            friendManager.onRecommendRemoveClick(friend)
        }

        //val intentProfileActivity = Intent(activity, ProfileActivity::class.java)

        adapter.onFriendClickListener = { friend ->
            if (this.isAdded) {
                startProfileActivity(activity as Context, friend)
            } else {
                Toast.makeText(activity as Context, "Please try again", Toast.LENGTH_SHORT).show()
            }
        }
//        adapter.
//        manager.firebaseStorageReference
        binding.rvRecommendedFriends.adapter = adapter
    }

//    private fun getMapOfMatchedUsers(matched: List<String>, binding: FragmentRecommendedFriendsBinding): MutableMap<Any?, Any> {
//        val docRef = db.collection("Users")
//        var size = matched.size
//        val mapOfMatches = mutableMapOf<Any?, Any>()
//        matched.forEach {
//            docRef.document(it).get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        size--
//                        mapOfMatches[document.data!!["FullName"]] = document.data!!
//                        if (size == 0) {
//                            Log.i("forLeo", "$mapOfMatches")
////                            Log.i("matchesReference", "${(mapOfMatches["Ken Jeong"] as Map<String, Any>)["ImageProfilePic"]}")
//                            val matches = mapOfMatches as Map<String, Any>
//
//
//                            for ((userName, value) in matches) {
//                                val gson = Gson()
//                                val friendInfo = value as Map<String, Any>
//                                val userInfo = gson.fromJson(JSONObject(friendInfo).toString(), UserInfo::class.java)
//                                val profilePicRef = (friendInfo["ImageProfilePic"] as com.google.firebase.firestore.DocumentReference).path
//                                userInfo.ImageProfilePic = profilePicRef
//                                matchedFriends.add(userInfo)
//                            }
//
//
////                            Log.i("matchesReference", "${(matches["Ken Jeong"] as Map<String, Any>)["ImageProfilePic"]}")
////                            val adapter = RecommendedFriendsAdapter(matches)
//                            friendManager.loadRecommendedFriends(matchedFriends)
//                            val adapter = RecommendedFriendsAdapter(friendManager.recommendedFriends)
//                            adapter.onLikeClickListener = { friend ->
////                                btnLike.isClickable = false
//                                friendManager.onLikeClick(friend)
//                            }
//                            adapter.onRemoveClickListener = { friend ->
//                                friendManager.onRecommendRemoveClick(friend)
////                                adapter.updateFriends(friendManager.recommendedFriends)
////                                friendManager.onRecommendRemoveClick(friend)
////                                adapter.notifyDataSetChanged()
//
//                            }
//
//                            binding.rvRecommendedFriends.adapter = adapter
//                        }
//                    } else {
//                        Log.i("forLeo", "could not find user doc")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.i("forLeo", "setMatched failed with ", exception)
//                }
//        }
//        return mapOfMatches
//    }
}

