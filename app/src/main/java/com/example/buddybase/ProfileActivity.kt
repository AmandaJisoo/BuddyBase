package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.lifecycle.lifecycleScope
import com.example.buddybase.databinding.ActivityProfileBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

// ID for "Bill Gates"
private const val TEST_DATA = "5uuWVzvVphYMEX8XMy5d"

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var personDataTemp: Map<String, Any>
    private lateinit var failMsg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        lifecycleScope.launch {
//            runCatching {
//                val firestore = FirebaseFirestore.getInstance()
//                val docRef = firestore.collection("Users").document(TEST_DATA)
//                docRef.get()
//                    .addOnSuccessListener { document ->
//                        if (document != null) {
//                            personDataTemp = document.data as Map<String, Any>
//                        } else {
//                            failMsg = "Doc doesn't exist"
//                        }
//                    }
//                    .addOnFailureListener{ exception ->
//                        Log.d("uhOh", "get failed with", exception)
//                    }
//            }.onFailure {
//                failMsg = "Failed"
//            }
//        }

        binding = ActivityProfileBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
//            if (this@ProfileActivity::personDataTemp.isInitialized) {
//                tvName.text = "${personDataTemp["FullName"]}"
//                tvAnimalPreference.text =
//                    "Enjoys the company of ${personDataTemp["Q_Pet"]}"
//                tvMusicPreferences.text = "Likes ${personDataTemp["Q_Music"].toString()}"
//                tvShowPreferences.text = "Would watch ${personDataTemp["Q_Show"]}"
//                tvPersonalityType.text = "Acts ${personDataTemp["Q_Personality"].toString()}"
//                tvFoodPreferences.text = "Feels like eating something ${personDataTemp["Q_Taste"]}"
//                tvAttitude.text = "Looking for a friend who is ${personDataTemp["Q_FriendType"].toString()}"
//            }

            btnTempPull.setOnClickListener {
                loadData(binding)
            }
        }
    }

    private fun loadData(binding: ActivityProfileBinding) {
        lifecycleScope.launch {
            runCatching {
                val firestore = FirebaseFirestore.getInstance()
                val docRef = firestore.collection("Users").document(TEST_DATA)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            personDataTemp = document.data as Map<String, Any>
                            with(binding) {
                                tvName.text = "${personDataTemp["FullName"]}"
                                tvAnimalPreference.text =
                                    "Enjoys the company of ${personDataTemp["Q_Pet"]}"
                                tvMusicPreferences.text =
                                    "Likes ${personDataTemp["Q_Music"].toString()}"
                                tvShowPreferences.text = "Would watch ${personDataTemp["Q_Show"]}"
                                tvPersonalityType.text =
                                    "Acts ${personDataTemp["Q_Personality"].toString()}"
                                tvFoodPreferences.text =
                                    "Feels like eating something ${personDataTemp["Q_Taste"]}"
                                tvAttitude.text =
                                    "Looking for a friend who is ${personDataTemp["Q_FriendType"].toString().substring(1, personDataTemp["Q_FriendType"].toString().length - 1)}"
                            }
                        } else {
                            failMsg = "Doc doesn't exist"
                        }
                    }
                    .addOnFailureListener{ exception ->
                        Log.d("uhOh", "get failed with", exception)
                    }
            }.onFailure {
                failMsg = "Failed"
            }
        }
    }
}