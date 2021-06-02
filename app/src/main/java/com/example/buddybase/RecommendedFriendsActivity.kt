package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.ActivityRecommendedFriendsBinding
import com.example.buddybase.manager.UserManager
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class RecommendedFriendsActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var manager: UserManager
    lateinit var userApp: UserApplication
    lateinit var docRef: DocumentReference
//    private lateinit var rvRecommendedFriends: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecommendedFriendsBinding.inflate(layoutInflater).apply { setContentView(root) }

        val friends = listOf("Eric", "Chee", "Justin", "Bieber", "Lady", "GAGA", "Captain", "America", "League", "Of", "Legends")

//        with(binding) {
//            val adapter = RecommendedFriendsAdapter(friends)
//            rvRecommendedFriends.adapter = adapter
//        }


        userApp = this.applicationContext as UserApplication
        manager = this.userApp.userManager

        db = FirebaseFirestore.getInstance()
//        var uid = manager.uid
//        docRef = uid?.let { db.collection("Users").document(it) }!!
        docRef = db.collection("Users").document("aBhsZO73GMe1a09xrMUemyZJB2q1")
        docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
//                        var mapOfMatches: MutableMap<Any?, Any>
                        var matchedWith = document.data!!["Matched"] as List<String>
                        var size = matchedWith.size
                        val mapOfMatches: MutableMap<Any?, Any> = HashMap()
                        val docRef2 = db.collection("Users")
                        matchedWith.forEach {
                            docRef2.document(it).get()
                                    .addOnSuccessListener { document ->
                                        if (document != null) {
                                            size--
                                            mapOfMatches[document.data!!["FullName"]] = document.data!!
                                            if (size == 0) {
                                                Log.i("forLeo", "$mapOfMatches")

                                                val keys = mapOfMatches.keys.toMutableList() as MutableList<String>

                                                val adapter = RecommendedFriendsAdapter(keys)
                                                binding.rvRecommendedFriends.adapter = adapter
                                            }
                                        } else {
                                            Log.i("forLeo", "could not find user doc")
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.i("forLeo", "setMatched failed with ", exception)
                                    }
                        }
                        Log.i("forLeo", "expecting a map that's not empty below")
                        Log.i("forLeo", "$mapOfMatches") //logs "{}"
                    } else {
                        Log.i("forLeo", "error doc doesn't exist")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.i("forLeo", "something wrong:", exception)
                }
    }

    private fun getMapOfMatchedUsers(matched: List<String>): MutableMap<Any?, Any> {
        val docRef = db.collection("Users")
        var size = matched.size
        val mapOfMatches = mutableMapOf<Any?, Any>()
        matched.forEach {
            docRef.document(it).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            size--
                            mapOfMatches[document.data!!["FullName"]] = document.data!!
                            if (size == 0) {

//                            this.matched = matchedUsers
                            }
                        } else {
                            Log.i("forLeo", "could not find user doc")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.i("forLeo", "setMatched failed with ", exception)
                    }
        }
        return mapOfMatches
    }
}