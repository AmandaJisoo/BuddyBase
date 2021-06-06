package com.example.buddybase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.buddybase.FriendDiffCallback
import com.example.buddybase.R
import com.example.buddybase.UserApplication
import com.example.buddybase.databinding.ItemRecommendedFriendBinding
import com.example.buddybase.manager.FriendManager
import com.example.buddybase.manager.UserManager
import com.example.buddybase.model.UserInfo
import com.google.firebase.storage.StorageReference
import io.github.rosariopfernandes.firecoil.load

class RecommendedFriendsAdapter(private var matchedFriends: MutableList<UserInfo>,
                                private val storageRef: StorageReference?,
                                private val application: UserApplication):
        RecyclerView.Adapter<RecommendedFriendsAdapter.FriendsViewHolder>() {

//    private lateinit var manager: UserManager
    private lateinit var friendManager: FriendManager

    private lateinit var ivProfilePic: ImageView
    private lateinit var friend: UserInfo

    var onLikeClickListener: (person: UserInfo) -> Unit = {_ ->}
    var onRemoveClickListener: (person: UserInfo) -> Unit = {_ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemRecommendedFriendBinding.inflate(LayoutInflater.from(parent.context))
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        friend = matchedFriends[position]
        with(holder.binding) {
            tvFriendName.text = friend.FullName
            val friendType = friend.Q_FriendType.joinToString { it }
            tvFriendPersonality.text = root.context.getString(
                R.string.recommended_friends_friend_type_format,
                friendType
            )

            friendManager = application.friendManager

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
                onLikeClickListener(friendManager.recommendedFriends[position])
            }

            btnRemove.setOnClickListener {
                onRemoveClickListener(friendManager.recommendedFriends[position])
            }
        }
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