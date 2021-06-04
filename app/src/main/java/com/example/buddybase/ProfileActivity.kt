package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.buddybase.databinding.ActivityProfileBinding
import com.google.firebase.firestore.DocumentReference
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

        binding = ActivityProfileBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {

            btnTempPull.setOnClickListener {
                loadData(binding)
            }
        }
    }

    // TODO: Copy this function so that it gets called when profile is tapped from the tab bar
    private fun loadData(binding: ActivityProfileBinding) {
        lifecycleScope.launch {
            runCatching {
                val firestore = FirebaseFirestore.getInstance()
                val docRef = firestore.collection("Users").document(TEST_DATA)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            personDataTemp = document.data as Map<String, Any>

                            // Debug code to look at where prof pic image is supposed to be
                            val picReference: DocumentReference = personDataTemp["ImageProfilePic"] as DocumentReference
                            picReference.get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        Log.d("loadDataMsg", document.getData().toString())
                                    } else {
                                        Log.d("loadDataMsg", "pic didn't retrieve")
                                    }
                                }

                            with(binding) {
                                //ivVariableProfPic.load(personDataTemp["ImageProfilePic"])
                                tvName.text = "${personDataTemp["FullName"]}"
                                tvVariableAnimal.text = "${personDataTemp["Q_Pet"]}"
                                tvVariableMusic.text = "${personDataTemp["Q_Music"].toString().drop(1).dropLast(1)}"
                                tvVariableShow.text = "Would watch ${personDataTemp["Q_Show"]}"
                                tvPersonalityType.text = "${personDataTemp["Q_Personality"].toString().drop(1).dropLast(1)}"
                                tvVariableFood.text = "${personDataTemp["Q_Taste"]}"
                                tvVariableAttitude.text = "${personDataTemp["Q_FriendType"].toString().drop(1).dropLast(1)}"
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