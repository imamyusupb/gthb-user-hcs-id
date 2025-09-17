package com.hcs.findmedev.presentation.onboard

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.hcs.findmedev.databinding.FragmentOnboardBinding
import com.hcs.core.R


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
            onboardingViewPager.adapter = adapter
            dotsIndicator.setViewPager2(binding.onboardingViewPager)
            onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    binding.buttonNext.visibility =
                        if (position == items.lastIndex) View.VISIBLE else View.GONE
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
        Toast.makeText(requireContext(), "Onboarding Finished!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
