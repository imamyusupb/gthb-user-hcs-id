package com.hcs.findmedev.presentation.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hcs.findmedev.databinding.ItemUsersBinding
import com.hcs.findmedev.domain.model.GithubUser

class FollowingAdapter(
    private var followers: List<GithubUser>,
    private val onItemClick: (GithubUser) -> Unit
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val user = followers[position]
        holder.binding.imageView2.visibility = ViewGroup.GONE
        holder.binding.tvUsername.text = user.username
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
