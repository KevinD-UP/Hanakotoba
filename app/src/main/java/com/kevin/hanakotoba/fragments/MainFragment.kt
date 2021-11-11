package com.kevin.hanakotoba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.databinding.FragmentMainBinding
import com.kevin.hanakotoba.fragments.MyFlowers.MyFlowersFragment
import com.kevin.hanakotoba.fragments.ResearchFlowers.ResearchFlowersFragment

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main,container,false)

        binding = FragmentMainBinding.bind(view)

        val names = listOf("MY FLOWERS","RESEARCH")

        val myFlowersFragment = MyFlowersFragment()
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