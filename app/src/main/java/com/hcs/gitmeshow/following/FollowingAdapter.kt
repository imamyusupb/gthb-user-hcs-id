package com.hcs.gitmeshow.following

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcs.core.model.UserFollowing
import com.hcs.gitmeshow.R
import com.hcs.gitmeshow.databinding.ItemRowUserBinding
import com.hcs.gitmeshow.detail.UserDetailActivity
import com.hcs.gitmeshow.viewUtils.load

class FollowingAdapter(private val mContext: Context) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private var items = mutableListOf<UserFollowing>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowUserBinding = ItemRowUserBinding.bind(itemView)

        fun bind(data: UserFollowing) {
            binding.apply {
                ivUser.load(data.avatarUrl)
                binding.txtUsername.text = data.login
            }
            with(itemView) {
                setOnClickListener {
                    context.startActivity(Intent(context, UserDetailActivity::class.java).apply {
                        putExtra(UserDetailActivity.USERNAME_KEY, data.login)
                    })
                }
            }
        }
    }

    fun setItems(data: MutableList<UserFollowing>) {
        this.items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_row_user, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}