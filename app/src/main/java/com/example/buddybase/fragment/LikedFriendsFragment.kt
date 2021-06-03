package com.example.buddybase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buddybase.databinding.FragmentLikedFriendsBinding
import com.example.buddybase.databinding.FragmentRecommendedFriendsBinding

class LikedFriendsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLikedFriendsBinding.inflate(inflater)
        activity?.title = "Liked Friends"
        with(binding){
            // TODO: fill liked friends
        }
        return binding.root
    }
}