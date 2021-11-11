package com.kevin.hanakotoba.fragments.FlowerDescription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.data.FlowerViewModel
import com.kevin.hanakotoba.databinding.FragmentFlowerDescriptionBinding

private const val ARG_PARAM1 = "param1"

class FlowerDescription : Fragment() {

    private lateinit var mFlowerViewModel : FlowerViewModel
    private lateinit var binding : FragmentFlowerDescriptionBinding

    private var flowerName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flowerName = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flower_description,container,false)

        mFlowerViewModel = ViewModelProvider(this).get(FlowerViewModel::class.java)
        binding = FragmentFlowerDescriptionBinding.bind(view)

        binding.flowerNameTxt.text = flowerName
        binding.addFlowerBtn.setOnClickListener {


        }

        return view
    }

    //TODO: PASSING FLOWER'S ID INSTEAD OF NAME
    companion object {
        @JvmStatic
        fun newInstance(flowerName: String) =
            FlowerDescription().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, flowerName)
                }
            }
    }
}