package com.example.buddybase.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddybase.UserApplication
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.FragmentRecommendedFriendsBinding
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.manager.UserManager
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class RecommendedFriendsFragment : Fragment() {
    private lateinit var db: FirebaseFirestore
    lateinit var manager: UserManager
    lateinit var userApp: UserApplication
    lateinit var docRef: DocumentReference

    private lateinit var friendManager: FriendManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecommendedFriendsBinding.inflate(inflater)
        activity?.title = "Recommended Friends"

        userApp = activity?.applicationContext as UserApplication
        this.friendManager = userApp.friendManager

        manager = this.userApp.userManager

        db = FirebaseFirestore.getInstance()

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

        // handle button clicks

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
                            adapter.onLikeClickListener = { friend ->
//                                tvSongInfo.text = root.context.getString(R.string.song_info_format, song.title, song.artist)
//                                clSongInfo.isInvisible = false
//                                currentlyPlaying = song
//                                musicManager.onSongSelected(song)
                                friendManager.onLikeClick(friend)

                            }
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

