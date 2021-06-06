package com.example.buddybase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.buddybase.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

// ID for "Bill Gates"
//private const val TEST_DATA = "5uuWVzvVphYMEX8XMy5d"
//private const val STORAGE_URL = "gs://buddybase-efd0e.appspot.com/user_profile_pics"

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var personDataTemp: Map<String, Any>
    private lateinit var failMsg: String
    private lateinit var profPicBitmap: Bitmap
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater).apply { setContentView(root) }
        auth = Firebase.auth
        with(binding) {

            btnTempPull.setOnClickListener {
                loadData(binding)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        Log.i("currentUser", currentUser.toString())
        // pull data based on auth.currentUser
        // loadData(binding, auth.currentUser) or smth
    }

    // TODO: Copy this function so that it gets called when profile is tapped from the tab bar
    private fun loadData(binding: ActivityProfileBinding) {
        lifecycleScope.launch {
            runCatching {
                val firestore = FirebaseFirestore.getInstance()
                val docRef = firestore.collection("Users").document("5uuWVzvVphYMEX8XMy5d")
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            personDataTemp = document.data as Map<String, Any>


//                          Gets image based on document id from firebase storage
                            var storage: FirebaseStorage = FirebaseStorage.getInstance()
                            var storageReference = storage.getReferenceFromUrl("gs://buddybase-efd0e.appspot.com/user_profile_pics").child("${document.id}.jpg")
                            try {
                                var localFile: File = File.createTempFile("images", "jpg")
                                storageReference.getFile(localFile)
                                    .addOnSuccessListener {
                                        profPicBitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                                    }
                                    .addOnFailureListener {

                                    }
                                localFile.deleteOnExit()
                            } catch (e: IOException) {}


                            with(binding) {
                                ivVariableProfPic.load(profPicBitmap)
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