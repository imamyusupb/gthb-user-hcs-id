package com.hcs.findmedev.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcs.BottomSheet
import com.hcs.core.R
import com.hcs.findmedev.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categories = listOf(
            CategoryItem(1, "Top Repository"),
            CategoryItem(2, "Top Contribution"),
            CategoryItem(3, "Most View"),
        )
        binding.apply {
            rvCategory.adapter = CategoryAdapter(categories) { item ->
                val sheet = BottomSheet()
                sheet.show(parentFragmentManager, "BottomSheet")
            }
            rvCategory.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            btnSearch.setOnClickListener {
                findNavController().navigate(com.hcs.findmedev.R.id.action_homeFragment_to_searchUserFragment)
            }


            // Data dummy menu
            val menus = listOf(
                MenuItem(R.drawable.ic_search_menu, "Search"),
                MenuItem(R.drawable.feature, "Merge"),
                MenuItem(R.drawable.feature, "Group"),
                MenuItem(R.drawable.feature, "Spam"),
                MenuItem(R.drawable.feature, "Chat"),
                MenuItem(R.drawable.feature, "Project"),
                MenuItem(R.drawable.feature, "Earn"),
                MenuItem(R.drawable.feature, "Job"),
                MenuItem(R.drawable.feature, "Forking")
            )
            rvMenu.adapter = MenuAdapter(menus) { menu ->
                if (menu.title == "Search") {
                    findNavController().navigate(com.hcs.findmedev.R.id.action_homeFragment_to_searchUserFragment)
                } else {
                    val sheet = BottomSheet()
                    sheet.show(parentFragmentManager, "BottomSheet")
                }
            }
            rvMenu.layoutManager = GridLayoutManager(requireContext(), 3)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}