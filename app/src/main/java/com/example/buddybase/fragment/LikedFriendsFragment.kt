package com.example.buddybase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddybase.UserApplication
import com.example.buddybase.adapter.LikedFriendsAdapter
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.FragmentLikedFriendsBinding
import com.example.buddybase.databinding.FragmentRecommendedFriendsBinding
import com.example.buddybase.manager.FriendManager

class LikedFriendsFragment : Fragment() {
    private lateinit var friendManager: FriendManager
    lateinit var userApp: UserApplication

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLikedFriendsBinding.inflate(inflater)
        activity?.title = "Liked Friends"
        with(binding){
            // TODO: fill liked friends
            userApp = activity?.applicationContext as UserApplication
            val friendManager = userApp.friendManager

            val adapter = LikedFriendsAdapter(friendManager.likedFriends)
            rvLikedFriends.adapter = adapter
        }

        return binding.root
    }
}