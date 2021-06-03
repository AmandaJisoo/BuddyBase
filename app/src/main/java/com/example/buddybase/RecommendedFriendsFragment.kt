package com.example.buddybase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.ActivityRecommendedFriendsBinding
import com.example.buddybase.databinding.FragmentRecommendedFriendsBinding
import com.example.buddybase.manager.UserManager
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class RecommendedFriendsFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    lateinit var manager: UserManager
    lateinit var userApp: UserApplication
    lateinit var docRef: DocumentReference

    private val navController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecommendedFriendsBinding.inflate(inflater)

//        userApp = this.applicationContext as UserApplication
        userApp = activity?.applicationContext as UserApplication
        manager = this.userApp.userManager

        db = FirebaseFirestore.getInstance()
//        var uid = manager.uid
//        docRef = uid?.let { db.collection("Users").document(it) }!!

        //TODO: for now document("aBhsZO73GMe1a09xrMUemyZJB2q1") is hardcoded because this activity isn't linked to the signup/login flow
        docRef = db.collection("Users").document("aBhsZO73GMe1a09xrMUemyZJB2q1")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var matchedWith = document.data!!["Matched"] as List<String>
                    val mapOfMatches = getMapOfMatchedUsers(matchedWith, binding)
                    Log.i("forLeo", "expecting a map that's not empty below")
                    Log.i("forLeo", "$mapOfMatches") //logs "{}"
                } else {
                    Log.i("forLeo", "error doc doesn't exist")
                }
            }
            .addOnFailureListener { exception ->
                Log.i("forLeo", "something wrong:", exception)
            }

        return binding.root
    }

    private fun getMapOfMatchedUsers(matched: List<String>, binding: FragmentRecommendedFriendsBinding): MutableMap<Any?, Any> {
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
                            Log.i("forLeo", "$mapOfMatches")
                            val matches = mapOfMatches as Map<String, Any>
                            val adapter = RecommendedFriendsAdapter(matches)
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
        return mapOfMatches
    }
}

