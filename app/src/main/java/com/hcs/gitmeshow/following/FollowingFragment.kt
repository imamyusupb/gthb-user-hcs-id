package com.hcs.gitmeshow.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcs.core.model.UserFollowing
import com.hcs.core.utils.state.LoaderState
import com.hcs.gitmeshow.databinding.FragmentFollowingBinding
import com.hcs.gitmeshow.detail.UserDetailActivity
import com.hcs.gitmeshow.viewUtils.setGone
import com.hcs.gitmeshow.viewUtils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private val followingViewModel: FollowingViewModel by viewModels()

    private var lists = mutableListOf<UserFollowing>()

    private val followingAdapter: FollowingAdapter by lazy {
        FollowingAdapter(requireContext())
    }

    private val binding: FragmentFollowingBinding by lazy {
        FragmentFollowingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUserName()
        initObserver()
        initRecyclerView()
    }

    private fun handleUserName() {
        val activity = activity as UserDetailActivity
        val username: String? = activity.getUsername()
        username?.let { followingViewModel.getUserFollowing(it) }
    }

    private fun initRecyclerView() {
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = followingAdapter
        }
    }

    private fun initObserver() {
        with(followingViewModel) {
            state.observe(viewLifecycleOwner) {
                handleStateLoading(it)
            }
            resultUserFollowing.observe(viewLifecycleOwner) {
                handleResultUserFollowing(it)
            }
        }
    }

    private fun handlingEmptyFollowing(data: List<UserFollowing>) {
        if (data.isEmpty()) {
            binding.apply {
                baseEmptyFollowing.root.setVisible()
                rcView.setGone()
            }
        } else {
            binding.apply {
                baseEmptyFollowing.root.setGone()
                rcView.setVisible()
            }
        }
    }

    private fun handleResultUserFollowing(data: List<UserFollowing>) {
        handlingEmptyFollowing(data)
        lists.clear()
        lists.addAll(data)
        followingAdapter.setItems(lists)
    }

    private fun handleStateLoading(loading: LoaderState) {
        if (loading is LoaderState.ShowLoading) {
            binding.apply {
                baseLoader.root.setVisible()
                rcView.setGone()
            }
        } else {
            binding.apply {
                baseLoader.root.setGone()
                rcView.setVisible()
            }
        }
    }

}