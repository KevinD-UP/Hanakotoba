package com.kevin.hanakotoba

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.hanakotoba.adapters.MyFlowerAdapter
import com.kevin.hanakotoba.data.FlowerAndGarden
import com.kevin.hanakotoba.databinding.FragmentMyFlowersBinding
import com.kevin.hanakotoba.viewmodels.FlowerDescriptionViewModel
import com.kevin.hanakotoba.viewmodels.MyFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFlowersFragment (private val waterFilter : Boolean): Fragment() {

    private lateinit var myFlowerViewModel : MyFlowerViewModel
    private lateinit var flowerDescriptionViewModel: FlowerDescriptionViewModel


    private lateinit var binding : FragmentMyFlowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_my_flowers,container,false)

        myFlowerViewModel = ViewModelProvider(this).get(MyFlowerViewModel::class.java)
        flowerDescriptionViewModel = ViewModelProvider(this).get(FlowerDescriptionViewModel::class.java)

        binding = FragmentMyFlowersBinding.bind(view)

        val adapter = MyFlowerAdapter(flowerDescriptionViewModel)
        binding.rvMyFlowers.adapter = adapter
        binding.rvMyFlowers.layoutManager = LinearLayoutManager(requireContext())

        initAdapter(adapter)
        initResearchBox(adapter)

        return view
    }

    private fun initAdapter(adapter : MyFlowerAdapter){
        if(waterFilter){
            binding.researchBox.visibility = View.GONE
            filterWater(adapter)
        } else {
            myFlowerViewModel.flowerAndGarden.observe(viewLifecycleOwner, { flowerAndGardens ->
                Log.d("DEBUG","Init Adapter - onChanged")
                adapter.setFlower(flowerAndGardens)
            })
        }
    }

    private fun initResearchBox(adapter : MyFlowerAdapter){
        binding.researchBox.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString(),adapter)

            }
        })
    }

    private fun filter(text: String, adapter : MyFlowerAdapter){

        val filteredFlowers = mutableListOf<FlowerAndGarden>()

        myFlowerViewModel.flowerAndGarden.observe(viewLifecycleOwner, { flowerAndGardens ->
            Log.d("DEBUG","ResearchBox Filter - onChanged")
            filteredFlowers.clear()
            for(current_flower in flowerAndGardens){
                if(current_flower.flower.name.lowercase().contains(text.lowercase())){
                    filteredFlowers.add(current_flower)
                }
            }
        })
        adapter.setFlower(filteredFlowers)

    }

    private fun filterWater(adapter : MyFlowerAdapter){
        val filteredFlowers = mutableListOf<FlowerAndGarden>()
        myFlowerViewModel.flowerAndGarden.observe(viewLifecycleOwner, { flowerAndGardens ->
            Log.d("DEBUG","FilterWater - onChanged")
            Log.d("DEBUG","---------------------")
            filteredFlowers.clear()

            for(current_flower in flowerAndGardens){
                if(current_flower.canBeWatered()){
                    Log.d("DEBUG","ADDING")
                    filteredFlowers.add(current_flower)
                } else {
                    Log.d("DEBUG","DO NOT ADD")
                }
            }
            Log.d("DEBUG","Size" + filteredFlowers.size.toString())
            adapter.setFlower(filteredFlowers)

        })
    }


}