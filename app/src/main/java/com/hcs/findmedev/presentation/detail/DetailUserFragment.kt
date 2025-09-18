package com.hcs.findmedev.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.hcs.findmedev.databinding.FragmentDetailUserBinding
import com.hcs.findmedev.domain.model.GithubUserDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: return
        viewModel.loadUserDetail(username)

        setupObserver()
        setupListener()

        val pagerAdapter = FollowersFollowingPagerAdapter(this, username)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Followers" else "Following"
        }.attach()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.userDetail.collect { user ->
                        user?.let { showUserDetail(it) }
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->
                        binding.root.isEnabled = !isLoading
                    }
                }

                launch {
                    viewModel.error.collect { errorMessage ->
                        if (errorMessage != null) {
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupListener() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showUserDetail(user: GithubUserDetail) {
        binding.apply {
            tvName.text = user.name ?: "(No Name)"
            tvUsername.text = "@${user.username}"
            tvBio.text = user.bio ?: "Computer Science Student, currently dwelling on android development."
            tvCompany.text = user.company ?: "(Company not set)"
            tvLocation.text = user.location ?: "(Location not set)"
            tvBlog.text = user.blog ?: "(Blog not set)"

            tvRepos.text = "${user.repositoryCount}\nrepository"
            tvGists.text = "${user.gistCount}\ngist"
            tvFollowing.text = "${user.followingCount}\nfollowing"
            tvFollowers.text = "${user.followerCount}\nfollower"

            ivAvatar.load(user.avatarUrl) {
                placeholder(com.hcs.core.R.drawable.image_placeholder)
                error(com.hcs.core.R.drawable.failed_placeholder)
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
