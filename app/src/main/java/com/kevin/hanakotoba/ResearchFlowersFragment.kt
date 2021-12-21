package com.kevin.hanakotoba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.hanakotoba.adapters.ResearchFlowersAdapter
import com.kevin.hanakotoba.databinding.FragmentResearchFlowersBinding
import com.kevin.hanakotoba.viewmodels.ResearchFlowersViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResearchFlowersFragment : Fragment() {
    private lateinit var binding : FragmentResearchFlowersBinding

    private lateinit var researchViewModel : ResearchFlowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_research_flowers,container,false)
        binding = FragmentResearchFlowersBinding.bind(view)

        val adapter = ResearchFlowersAdapter()
        binding.rvResearchFlowers.adapter = adapter
        binding.rvResearchFlowers.layoutManager = LinearLayoutManager(requireContext())

        researchViewModel = ViewModelProvider(this).get(ResearchFlowersViewModel::class.java)
        researchViewModel.flowers.observe(viewLifecycleOwner, Observer { flower ->
            adapter.setFlower(flower)
        })

        binding.addFlower.setOnClickListener{
            val testFragment = ResearchFlowersFragment()
            val activity = requireActivity()

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, testFragment)
                .addToBackStack(null).commit()
        }

        return view
    }


}