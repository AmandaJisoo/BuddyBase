package com.example.buddybase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
//import coil.load
import com.example.buddybase.FriendDiffCallback
import com.example.buddybase.R
import com.example.buddybase.databinding.ItemRecommendedFriendBinding
import com.example.buddybase.model.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import org.json.JSONObject

//class RecommendedFriendsAdapter(private val listOfFriends: Map<String, Any>): RecyclerView.Adapter<RecommendedFriendsAdapter.FriendsViewHolder>() {
class RecommendedFriendsAdapter(private var matchedFriends: MutableList<UserInfo>): RecyclerView.Adapter<RecommendedFriendsAdapter.FriendsViewHolder>() {

//    private var matchedFriends: MutableList<UserInfo> = mutableListOf()
    // new
    var onLikeClickListener: (person: UserInfo) -> Unit = {_ ->}
    var onRemoveClickListener: (person: UserInfo) -> Unit = {_ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemRecommendedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
//        for ((userName, value) in listOfFriends) {
////            Log.i("matchesReference_path", "${ ((listOfFriends["Michelle Obama"] as Map<String, Any>)["ImageProfilePic"] as com.google.firebase.firestore.DocumentReference).path }")
////            Log.i("matchesReference_list", "${ ((listOfFriends["Michelle Obama"] as Map<String, Any>)["ImageProfilePic"].toString()) }")
//            val gson = Gson()
//            val friendInfo = value as Map<String, Any>
//            val userInfo = gson.fromJson(JSONObject(friendInfo).toString(), UserInfo::class.java)
//            val profilePicRef = (friendInfo["ImageProfilePic"] as com.google.firebase.firestore.DocumentReference).path
//            userInfo.ImageProfilePic = profilePicRef
//            matchedFriends.add(userInfo)
//        }
//        Log.i("matchesReference", "$matchedFriends")

        val friend = matchedFriends[position]
        with(holder.binding) {
            tvFriendName.text = friend.FullName
            val friendType = friend.Q_FriendType.joinToString { it }
            tvFriendPersonality.text = root.context.getString(
                R.string.recommended_friends_friend_type_format,
                friend.FullName,
                friendType
            )
            // TODO: Parse ImageProfilePic as readable URI string
//            Log.i("ImageProfilePic", friend.ImageProfilePic)
//            val storageRef = FirebaseFirestore.getInstance()
//            val imageRef = storageRef.child(friend.ImageProfilePic)
//            ivFriendProfilePic.load()
//            ivFriendProfilePic.load()

            btnLike.setOnClickListener{
//                btnLike.isClickable = false
                onLikeClickListener(friend)
            }

            btnRemove.setOnClickListener {
                onRemoveClickListener(friend)
                notifyDataSetChanged()
            }
        }
    }

//    fun updateFriends(newFriendList: MutableList<UserInfo>) {
//        val callback = FriendDiffCallback(newFriendList, newFriendList)
//        val result = DiffUtil.calculateDiff(callback)
//        result.dispatchUpdatesTo(this)
//        this.matchedFriends = newFriendList
//    }

    override fun getItemCount(): Int = matchedFriends.size

    class FriendsViewHolder(val binding: ItemRecommendedFriendBinding): RecyclerView.ViewHolder(binding.root)

}