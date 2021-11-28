package com.kevin.hanakotoba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.hanakotoba.viewmodels.FlowerViewModel
import com.kevin.hanakotoba.databinding.FragmentMyFlowersBinding
import com.kevin.hanakotoba.adapters.MyFlowerAdapter
import dagger.hilt.android.AndroidEntryPoint

//TODO: Enable watering button only when it's time
@AndroidEntryPoint
class MyFlowersFragment : Fragment() {

    private lateinit var mFlowerViewModel : FlowerViewModel

    private lateinit var binding : FragmentMyFlowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_flowers,container,false)
        mFlowerViewModel = ViewModelProvider(this).get(FlowerViewModel::class.java)

        binding = FragmentMyFlowersBinding.bind(view)
        val adapter = MyFlowerAdapter()
        binding.rvMyFlowers.adapter = adapter
        binding.rvMyFlowers.layoutManager = LinearLayoutManager(requireContext())

        mFlowerViewModel.flowers.observe(viewLifecycleOwner, Observer { flower ->
            adapter.setFlower(flower)

        })


        binding.addFlower.setOnClickListener{
            val testFragment = ResearchFlowersFragment()
            val activity = requireActivity()

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,testFragment)
                .addToBackStack(null).commit()
        }
        return view
    }

}