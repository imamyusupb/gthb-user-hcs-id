package com.hcs.findmedev.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcs.findmedev.R
import com.hcs.findmedev.databinding.FollowingFragmentBinding
import com.hcs.findmedev.presentation.search.SearchUserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private var _binding: FollowingFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FollowingAdapter


    private val viewModel: FollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FollowingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = FollowingAdapter(emptyList()) { user ->
            val bundle = bundleOf("username" to user.username)
            findNavController().navigate(
                R.id.detailUserFragment,
                bundle
            )
        }
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.following.collect { users ->
                        adapter.updateList(users)
                    }
                }
                launch {
                    viewModel.loading.collect { isLoading ->
                    }
                }
                launch {
                    viewModel.error.collect { errorMsg ->
                        errorMsg?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(username: String) = FollowingFragment().apply {
            arguments = Bundle().apply { putString("username", username) }
        }
    }
}
