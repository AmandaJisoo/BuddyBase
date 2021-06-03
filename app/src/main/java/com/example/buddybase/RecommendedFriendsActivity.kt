package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.adapter.RecommendedFriendsAdapter
import com.example.buddybase.databinding.ActivityRecommendedFriendsBinding

class RecommendedFriendsActivity : AppCompatActivity() {

//    private lateinit var rvRecommendedFriends: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecommendedFriendsBinding.inflate(layoutInflater).apply { setContentView(root) }

        val friends = listOf("Eric", "Chee", "Justin", "Bieber", "Lady", "GAGA", "Captain", "America", "League", "Of", "Legends")
        with(binding) {
            val adapter = RecommendedFriendsAdapter(friends)
            rvRecommendedFriends.adapter = adapter
        }
    }
}