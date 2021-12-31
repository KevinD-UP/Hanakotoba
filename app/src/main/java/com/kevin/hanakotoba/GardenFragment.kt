package com.kevin.hanakotoba

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.FtsOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.kevin.hanakotoba.adapters.ViewPagerAdapter
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.FragmentGardenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {

    private lateinit var binding : FragmentGardenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_garden,container,false)
        binding = FragmentGardenBinding.bind(view)

        val names = listOf("MY FLOWERS","RESEARCH")
        val myFlowersFragment = MyFlowersFragment(false)
        val myResearchFlowersFragment = ResearchFlowersFragment()

        val pagerAdapter = ViewPagerAdapter(
            this,
            mutableListOf(myFlowersFragment,myResearchFlowersFragment)
        )
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = names[position]
        }.attach()


        return view
    }




}