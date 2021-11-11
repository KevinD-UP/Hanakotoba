package com.kevin.hanakotoba.fragments.ResearchFlowers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.data.FlowerViewModel
import com.kevin.hanakotoba.databinding.FragmentResearchFlowersBinding
import com.kevin.hanakotoba.fragments.MyFlowers.MyFlowersAdapter

class ResearchFlowersFragment : Fragment() {
    private lateinit var binding : FragmentResearchFlowersBinding

    private lateinit var mFlowerViewModel : FlowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_research_flowers,container,false)
        binding = FragmentResearchFlowersBinding.bind(view)

        val adapter = MyFlowersAdapter()
        binding.rvResearchFlowers .adapter = adapter
        binding.rvResearchFlowers.layoutManager = LinearLayoutManager(requireContext())

        mFlowerViewModel = ViewModelProvider(this).get(FlowerViewModel::class.java)
        mFlowerViewModel.getAllFlowers().observe(viewLifecycleOwner, Observer { flower ->
            adapter.setFlower(flower)

        })

        return view
    }


}