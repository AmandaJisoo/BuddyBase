package com.example.buddybase.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.buddybase.UserApplication
import com.example.buddybase.adapter.LikedFriendsAdapter
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.FragmentLikedFriendsBinding
import com.example.buddybase.databinding.FragmentRecommendedFriendsBinding
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.manager.UserManager
import com.example.buddybase.startProfileActivity

class LikedFriendsFragment : Fragment() {
    private lateinit var userManager: UserManager
    lateinit var userApp: UserApplication

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLikedFriendsBinding.inflate(inflater)
        activity?.title = "Liked Friends"
        with(binding){
            userApp = activity?.applicationContext as UserApplication
            val friendManager = userApp.friendManager
            userManager = userApp.userManager

            val adapter = LikedFriendsAdapter(friendManager.likedFriends, userManager.firebaseStorageReference, userApp)

            adapter.onRemoveClickListener = { friend ->
                friendManager.onLikedRemoveClick(friend)
                adapter.notifyDataSetChanged()
            }

            rvLikedFriends.adapter = adapter

            adapter.onFriendClickListener = { friend ->
                if (this@LikedFriendsFragment.isAdded) {
                    startProfileActivity(activity as Context, friend)
                } else {
                    Toast.makeText(activity as Context, "Please try again", Toast.LENGTH_SHORT).show()
                }
            }
        }



        return binding.root
    }
}