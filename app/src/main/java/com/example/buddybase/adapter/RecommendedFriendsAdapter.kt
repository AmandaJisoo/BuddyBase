package com.example.buddybase.adapter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.buddybase.FriendDiffCallback
import com.example.buddybase.R
import com.example.buddybase.TestActivity
import com.example.buddybase.UserApplication
import com.example.buddybase.databinding.ItemRecommendedFriendBinding
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.manager.UserManager
import com.example.buddybase.model.UserInfo
import com.google.firebase.storage.StorageReference
import io.github.rosariopfernandes.firecoil.load
import org.json.JSONException
import org.json.JSONObject

class RecommendedFriendsAdapter(private var matchedFriends: MutableList<UserInfo>,
                                private val storageRef: StorageReference?,
                                private val application: UserApplication,
                                private val applicationContext: Context
):
        RecyclerView.Adapter<RecommendedFriendsAdapter.FriendsViewHolder>() {

//    private lateinit var manager: UserManager
    private lateinit var friendManager: FriendManager
    private lateinit var userManager: UserManager

    private lateinit var ivProfilePic: ImageView
    private lateinit var friend: UserInfo

    var onLikeClickListener: (person: UserInfo) -> Unit = {_ ->}
    var onRemoveClickListener: (person: UserInfo) -> Unit = {_ ->}
    var onFriendClickListener: (person: UserInfo) -> Unit = {_ ->}

    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    // I know.. it'll be okay as long as you don't tell the bad guys :)
    private val serverKey = "key=" + "AAAAqr-v7PI:APA91bHahHey6HoHc6XYQtj9gjxw7TiY3Xebiw78YHNeJxWAAr1ra6YafSJGAyMwKsQZ-9Up6rDzm7JXxeykdFEA6NV7vibc7QCWJJx5A-lfAW0NgnhIiUkhHW0MreZviu1QYl8wsRjd"
    private val contentType = "application/json"
    private val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(applicationContext) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemRecommendedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        friend = matchedFriends[position]
        with(holder.binding) {
            tvFriendName.text = friend.FullName
            val friendType = friend.Q_FriendType?.joinToString { it }
            tvFriendPersonality.text = root.context.getString(
                R.string.recommended_friends_friend_type_format,
                friendType
            )

            friendManager = application.friendManager
            userManager = application.userManager

            // load image
            ivProfilePic = ivFriendProfilePic
//            loadProfilePic()
            val storageReference = storageRef
            if (storageReference != null) {
                val imgProfilePic = friend.ImageProfilePic
                if (imgProfilePic != null) {
                    val imageRef = storageReference.child(imgProfilePic)
                    ivFriendProfilePic.load(imageRef) {
                        crossfade(true)
                        placeholder(R.mipmap.ic_avatar_placeholder_round)
                        transformations(CircleCropTransformation())
                    }
                } else {
                    ivFriendProfilePic.load(R.mipmap.ic_avatar_placeholder_round) {
                        transformations(CircleCropTransformation())
                    }
                }
            }


//            userApp = activity?.applicationContext as UserApplication
//            userApp =
//            friendManager = application.friendManager
//            friendManager

            btnLike.setOnClickListener{
                Log.i("Whatisgoingon4", "${position}")
                Log.i("Whatisgoingon5", "${matchedFriends[position]}")

                Log.i("godsuya2", "${matchedFriends[position].uid}")

                val topic = "/topics/${matchedFriends[position].uid}" //topic has to match what the receiver subscribed to

                val notification = JSONObject()
                val notifcationBody = JSONObject()

                try {
                    notifcationBody.put("title", "${userManager.uid}")
                    notifcationBody.put("message", "${userManager.fullName} wants to be friends")
                    notification.put("to", topic)
                    notification.put("data", notifcationBody)
                    Log.e("TAG", "try")
                } catch (e: JSONException) {
                    Log.e("TAG", "onCreate: " + e.message)
                }

                sendNotification(notification)
                Toast.makeText(
                    applicationContext,
                    "You have liked ${friendManager.recommendedFriends[position].FullName}",
                    Toast.LENGTH_SHORT
                ).show()

                onLikeClickListener(friendManager.recommendedFriends[position])
            }

            btnRemove.setOnClickListener {
                Log.i("yuh", "removing from list")
                Toast.makeText(
                    applicationContext,
                    "You have removed ${friendManager.recommendedFriends[position].FullName}",
                    Toast.LENGTH_SHORT
                ).show()

                onRemoveClickListener(friendManager.recommendedFriends[position])
            }

            itemRoot.setOnClickListener {
                onFriendClickListener(friendManager.recommendedFriends[position])
            }
        }
    }

    private fun sendNotification(notification: JSONObject) {
        Log.e("TAG", "sendNotification")
        val jsonObjectRequest = object : JsonObjectRequest(FCM_API, notification,
            Response.Listener { response ->
                Log.i("TAG", "onResponse: $response")
            },
            Response.ErrorListener {
                Log.i("godsuya", "onErrorResponse: Didn't work")
            }) {

            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = serverKey
                params["Content-Type"] = contentType
                return params
            }


        }
        requestQueue.add(jsonObjectRequest)
    }

//    fun loadProfilePic() {
//        val storageReference = storageRef
//        if (storageReference != null) {
//            val imgProfilePic = friend.ImageProfilePic
//            if (imgProfilePic != null) {
//                val imageRef = storageReference.child(imgProfilePic)
//                ivProfilePic.load(imageRef) {
//                    crossfade(true)
//                    placeholder(R.mipmap.ic_avatar_placeholder_round)
//                    transformations(CircleCropTransformation())
//                }
//            }
//        }
//    }

//    fun updateFriends(newFriendList: MutableList<UserInfo>) {
//        val callback = FriendDiffCallback(newFriendList, newFriendList)
//        val result = DiffUtil.calculateDiff(callback)
//        result.dispatchUpdatesTo(this)
//        this.matchedFriends = newFriendList
//    }

    override fun getItemCount(): Int = matchedFriends.size

    class FriendsViewHolder(val binding: ItemRecommendedFriendBinding): RecyclerView.ViewHolder(binding.root)

}