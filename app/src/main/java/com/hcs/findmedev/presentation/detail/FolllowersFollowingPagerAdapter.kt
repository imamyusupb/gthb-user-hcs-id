package com.hcs.findmedev.presentation.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hcs.findmedev.presentation.follower.FollowersFragment
import com.hcs.findmedev.presentation.follower.FollowingFragment

class FollowersFollowingPagerAdapter(
    fragment: Fragment,
    private val username: String
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment.newInstance(username)
            else -> FollowingFragment.newInstance(username)
        }
    }
}
