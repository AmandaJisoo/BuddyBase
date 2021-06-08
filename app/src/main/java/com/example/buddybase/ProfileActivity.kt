package com.example.buddybase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.transform.CircleCropTransformation
import com.example.buddybase.databinding.ActivityProfileBinding
import com.example.buddybase.fragment.RecommendedFriendsFragment
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.manager.UserManager
import com.example.buddybase.model.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import io.github.rosariopfernandes.firecoil.load
import kotlinx.coroutines.launch

// ID for "Bill Gates"
//private const val TEST_DATA = "5uuWVzvVphYMEX8XMy5d"
//private const val STORAGE_URL = "gs://buddybase-efd0e.appspot.com/user_profile_pics"

private const val UID_KEY = "UID_KEY"

fun startProfileActivity(context: Context, friend: UserInfo) {
    with(context) {
        val intent = Intent(context, ProfileActivity::class.java).apply {
            putExtra(UID_KEY, friend)
        }
        startActivity(intent)
    }
}

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    lateinit var manager: UserManager
    lateinit var likedFriendsManager: FriendManager
    lateinit var userApp: UserApplication
    private lateinit var auth: FirebaseAuth
    private lateinit var friend: UserInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater).apply { setContentView(root) }
        auth = Firebase.auth
        userApp = this.applicationContext as UserApplication
        this.manager = userApp.userManager
        this.likedFriendsManager = userApp.friendManager

        friend = intent.extras?.getParcelable<UserInfo>(UID_KEY)!!

        loadData(binding, friend)
//        with(binding) {
//            fabLikeBtn.setOnClickListener {
//                Toast.makeText(
//                    applicationContext,
//                    "You have liked ${friend.FullName}",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                likedFriendsManager.onLikeClick(friend)
//            }
//
//            fabDislikeBtn.setOnClickListener {
//                Toast.makeText(
//                    applicationContext,
//                    "You have removed ${friend.FullName}",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                likedFriendsManager.onLikedRemoveClick(friend)
//            }
//        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    // TODO: Copy this function so that it gets called when profile is tapped from the tab bar
    private fun loadData(binding: ActivityProfileBinding, friend: UserInfo) {
        val storageReference = manager.firebaseStorageReference
            with(binding) {
                friend.ImageProfilePic?.let { Log.i("imagePath", it) }
                if (storageReference != null) {
                    val profPic = friend.ImageProfilePic
                    if (profPic != null) {
                        val img = storageReference.child(profPic)
                        ivProfPic.load(img) {
                            crossfade(true)
                            placeholder(R.mipmap.ic_avatar_placeholder_round)
                            transformations(CircleCropTransformation())
                        }
                    }
                }
                tvName.text = friend.FullName
                tvVariableAnimal.text = friend.Q_Pet
                tvVariableMusic.text = friend.Q_Music.toString().drop(1).dropLast(1)
                tvVariableShow.text = "Would watch ${friend.Q_Show}"
                tvPersonalityType.text = friend.Q_Personality.toString().drop(1).dropLast(1)
                tvVariableFood.text = friend.Q_Taste
                tvVariableAttitude.text = friend.Q_FriendType.toString().drop(1).dropLast(1)
            }


    }
}