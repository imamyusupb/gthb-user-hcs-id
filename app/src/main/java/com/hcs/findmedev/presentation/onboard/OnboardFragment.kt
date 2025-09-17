package com.hcs.findmedev.presentation.onboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.hcs.core.R
import com.hcs.findmedev.databinding.FragmentOnboardBinding


class OnboardFragment : Fragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: OnboardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = setDataOnboard()
        adapter = OnboardingAdapter(items)
        binding.apply {
            val prefs = requireContext().getSharedPreferences("onboard_prefs", Context.MODE_PRIVATE)
            val finished = prefs.getBoolean("onboard_finished", false)

            if (finished) {
                findNavController().navigate(com.hcs.findmedev.R.id.action_onboardFragment_to_homeFragment)
                return
            }
            progressIndicator.isIndeterminate = true
            onboardingViewPager.adapter = adapter
            dotsIndicator.setViewPager2(binding.onboardingViewPager)
            onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    buttonNext.visibility = if (position == items.lastIndex) View.VISIBLE else View.GONE
                    buttonNext.setOnClickListener {
                        binding.loadingOverlay.visibility = View.VISIBLE

                        view.postDelayed({
                            saveOnboardingFinished()
                            navigateToHome()
                        }, 2000)
                    }
                }
            })
        }
    }

    private fun setDataOnboard(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(
                title = "Portfolio",
                description = getString(R.string.desc_onboard_porto),
                imageRes = R.drawable.img_board_porto
            ),
            OnboardingItem(
                title = "Collaboration",
                description = getString(R.string.desc_onboard_collab),
                imageRes = R.drawable.img_board_collab
            ),
            OnboardingItem(
                title = "Learning Together",
                description = getString(R.string.desc_onboard_learning),
                imageRes = R.drawable.img_board_learning
            )
        )
    }

    private fun saveOnboardingFinished() {
        val prefs = requireContext().getSharedPreferences("onboard_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("onboard_finished", true).apply()
    }

    private fun navigateToHome() {
        findNavController().navigate(com.hcs.findmedev.R.id.action_onboardFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
