package com.goodmood.feature.editor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goodmood.core.editor.databinding.FragmentEditBinding
import com.goodmood.core.ui.base.BaseFragment
import com.goodmood.feature.editor.ui.tools.ToolFragmentManager
import com.google.android.material.tabs.TabLayoutMediator

internal class EditFragment : BaseFragment() {

    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolFragments = ToolFragmentManager.getToolFragments()
        binding.viewPager.adapter = EditToolAdapter(this, toolFragments)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val fragment = toolFragments[position]
            tab.text = requireActivity().getString(fragment.getTitle())
            tab.icon = ContextCompat.getDrawable(requireActivity(), fragment.getIcon())
        }.attach()
    }

    inner class EditToolAdapter(
        fragment: Fragment,
        private val fragments: List<ToolFragment>
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
}