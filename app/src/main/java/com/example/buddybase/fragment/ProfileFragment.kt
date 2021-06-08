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
import coil.transform.CircleCropTransformation
import com.example.buddybase.R
import com.example.buddybase.UserApplication
import com.example.buddybase.databinding.ActivityProfileBinding
import com.example.buddybase.databinding.FragmentProfileBinding
import com.example.buddybase.manager.UserManager
import com.example.buddybase.model.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import io.github.rosariopfernandes.firecoil.load
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.IOException

private const val TEST_DATA = "5uuWVzvVphYMEX8XMy5d"
private const val STORAGE_URL = "gs://buddybase-efd0e.appspot.com/user_profile_pics"

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var personDataTemp: MutableMap<String, Any>
    private lateinit var failMsg: String
    private lateinit var auth: FirebaseAuth
    lateinit var manager: UserManager
    lateinit var userApp: UserApplication
    lateinit var uid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        auth = Firebase.auth

        userApp = activity?.applicationContext as UserApplication
        this.manager = userApp.userManager

        this.uid = auth.currentUser?.uid ?: ""
        loadData(binding, uid)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
    }

    private fun loadData(binding: FragmentProfileBinding, uid: String?) {
        lifecycleScope.launch {
            runCatching {
                val firestore = FirebaseFirestore.getInstance()
                val docRef = firestore.collection("Users").document(uid!!)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            personDataTemp = document.data as MutableMap<String, Any>
//                            val gson = Gson()
//                            if (personDataTemp["ImageProfilePic"] != null) {
//                                (personDataTemp as MutableMap<String, Any>)["ImageProfilePic"] = (personDataTemp["ImageProfilePic"] as DocumentReference).path
//                            }
//                            val userInfo = gson.fromJson(JSONObject(personDataTemp as Map<String, Any>).toString(), UserInfo::class.java)
//                            Log.i("userInfosss", "${userInfo}")

////                          Gets image based on document id from firebase storage
//                            var storage: FirebaseStorage = FirebaseStorage.getInstance()
//                            var storageReference = storage.getReferenceFromUrl(STORAGE_URL).child("${document.id}.jpg")
//                            try {
//                                var localFile: File = File.createTempFile("images", "jpg")
//                                storageReference.getFile(localFile)
//                                    .addOnSuccessListener {
//                                        profPicBitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//                                    }
//                                    .addOnFailureListener {
//
//                                    }
//                                localFile.deleteOnExit()
//                            } catch (e: IOException) {}

                            val storageReference = manager.firebaseStorageReference


                            with(binding) {
                                if (storageReference != null) {
                                    if (personDataTemp["ImageProfilePic"] != null) {
                                        val profPic = (personDataTemp["ImageProfilePic"] as DocumentReference).path
                                        if (profPic != null) {
                                            val img = storageReference.child(profPic)
                                            ivProfPic.load(img) {
                                                crossfade(true)
                                                placeholder(R.mipmap.ic_avatar_placeholder_round)
                                                transformations(CircleCropTransformation())
                                            }
                                        }
                                    }
                                }
                                tvName.text = "${personDataTemp["FullName"]}"
                                tvVariableAnimal.text = "${personDataTemp["Q_Pet"]}"
                                tvVariableMusic.text = "${personDataTemp["Q_Music"].toString().drop(1).dropLast(1)}"
                                tvVariableShow.text = "${personDataTemp["Q_Show"]}"
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