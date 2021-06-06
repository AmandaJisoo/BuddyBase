package com.example.buddybase.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.buddybase.R
import com.example.buddybase.databinding.ActivityProfileBinding
import com.example.buddybase.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

private const val TEST_DATA = "5uuWVzvVphYMEX8XMy5d"
private const val STORAGE_URL = "gs://buddybase-efd0e.appspot.com/user_profile_pics"

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var personDataTemp: Map<String, Any>
    private lateinit var failMsg: String
    private lateinit var profPicBitmap: Bitmap
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        auth = Firebase.auth

        with(binding) {
            btnTempPull.setOnClickListener {
                loadData(binding)
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
    }

    private fun loadData(binding: FragmentProfileBinding) {
        lifecycleScope.launch {
            runCatching {
                val firestore = FirebaseFirestore.getInstance()
                val docRef = firestore.collection("Users").document(TEST_DATA)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            personDataTemp = document.data as Map<String, Any>


//                          Gets image based on document id from firebase storage
                            var storage: FirebaseStorage = FirebaseStorage.getInstance()
                            var storageReference = storage.getReferenceFromUrl(STORAGE_URL).child("${document.id}.jpg")
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