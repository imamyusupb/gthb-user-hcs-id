package com.hcs.findmedev.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hcs.findmedev.databinding.ItemUsersBinding
import com.hcs.findmedev.domain.model.GithubUser

class SearchUserAdapter(
    private var users: List<GithubUser>,
    private val onItemClick: (GithubUser) -> Unit  // callback ketika item diklik
) : RecyclerView.Adapter<SearchUserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(users[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvUsername.text = user.username
        holder.binding.tvReposInfo.text = user.url
        holder.binding.ivAvatar.load(user.avatarUrl) {
            placeholder(com.hcs.core.R.drawable.image_placeholder)
            error(com.hcs.core.R.drawable.failed_placeholder)
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateList(newUsers: List<GithubUser>) {
        users = newUsers
        notifyDataSetChanged()
    }
}
