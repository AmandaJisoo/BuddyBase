package com.example.buddybase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.buddybase.databinding.ActivityCardViewProfileBinding
import com.example.buddybase.model.UserInfo


class ProfilesAdapter : RecyclerView.Adapter<ProfilesAdapter.ProfileViewHolder>() {

    private var profiles: List<UserInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProfileViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.activity_card_view_profile,
            parent,
            false
        )
    )

    override fun getItemCount() = profiles?.size ?: 0

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        profiles?.let {
            holder.binding.profile = it[position]
            holder.binding.executePendingBindings()
        }
    }

    fun setProfiles(profiles: List<UserInfo>) {
        this.profiles = profiles
        notifyDataSetChanged()
    }

    inner class ProfileViewHolder(val binding: ActivityCardViewProfileBinding) :
        RecyclerView.ViewHolder(binding.root)

}