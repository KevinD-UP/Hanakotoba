package com.kevin.hanakotoba

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.hanakotoba.adapters.ResearchFlowersAdapter
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.FragmentResearchFlowersBinding
import com.kevin.hanakotoba.viewmodels.ResearchFlowersViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResearchFlowersFragment : Fragment() {
    private lateinit var binding : FragmentResearchFlowersBinding
    private lateinit var researchViewModel : ResearchFlowersViewModel

    private var wateringIntervalFilter = false


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
            Log.d("DEBUG","IM HERE ! ")
            adapter.setFlower(flower)
        })


        binding.researchBox.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                    filter(p0.toString(),adapter)

            }
        })

        binding.wateringIntervalFilter.setOnClickListener{
            wateringIntervalFilter = !wateringIntervalFilter
            if(wateringIntervalFilter){
                binding.wateringIntervalFilter.setBackgroundResource(R.drawable.rounded_corner_textview)
                adapter.setSortFilter("wateringInterval")

            } else {
                binding.wateringIntervalFilter.setBackgroundResource(0)
                adapter.setSortFilter()
            }
        }

        binding.addFlower.setOnClickListener{
            val addNewFlowerFragment = AddNewFlower()
            val activity = requireActivity()

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, addNewFlowerFragment)
                .addToBackStack(null).commit()
        }

        return view
    }

    fun filter(text: String, adapter : ResearchFlowersAdapter){

        val filteredFlowers = mutableListOf<Flower>()

        researchViewModel.flowers.observe(viewLifecycleOwner, Observer { flower ->
            filteredFlowers.clear()
            for( current_flower in flower){
                if(current_flower.name.lowercase().contains(text.lowercase())){
                    filteredFlowers.add(current_flower)
                }
            }
            adapter.setFlower(filteredFlowers)
        })



    }



}