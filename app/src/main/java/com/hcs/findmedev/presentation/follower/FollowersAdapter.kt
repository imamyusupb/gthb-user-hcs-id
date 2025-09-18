package com.hcs.findmedev.presentation.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hcs.findmedev.databinding.ItemUsersBinding
import com.hcs.findmedev.domain.model.GithubUser

class FollowersAdapter(
    private var followers: List<GithubUser>,
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    inner class FollowersViewHolder(val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val user = followers[position]
        holder.binding.tvUsername.text = user.username
        holder.binding.imageView2.visibility = ViewGroup.GONE
        holder.binding.tvReposInfo.text = user.url
        holder.binding.ivAvatar.load(user.avatarUrl) {
            placeholder(com.hcs.core.R.drawable.image_placeholder)
            error(com.hcs.core.R.drawable.failed_placeholder)
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount(): Int = followers.size

    fun updateList(newUsers: List<GithubUser>) {
        followers = newUsers
        notifyDataSetChanged()
    }
}
