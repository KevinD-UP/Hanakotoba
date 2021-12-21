package com.kevin.hanakotoba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.FragmentFlowerDescriptionBinding
import com.kevin.hanakotoba.viewmodels.FlowerDescriptionViewModel
import com.kevin.hanakotoba.viewmodels.MyFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.thread

@AndroidEntryPoint
class FlowerDescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentFlowerDescriptionBinding
    private lateinit var flowerDescriptionViewModel: FlowerDescriptionViewModel
    private lateinit var flower: Flower

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flower_description,container,false)

        val bundle = arguments
        flower = bundle!!.getSerializable("flower") as Flower

        flowerDescriptionViewModel = ViewModelProvider(this).get(FlowerDescriptionViewModel::class.java)
        binding = FragmentFlowerDescriptionBinding.bind(view)


        binding.flowerNameTxt.text = flower.name
        binding.addFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[FlowerDescription - onCreateView] ADD : ${flower.name}", Toast.LENGTH_SHORT).show()
            flowerDescriptionViewModel.addFlowerInGarden(flower.flower_id)
        }

        binding.deleteFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[FlowerDescription - onCreateView] Delete : ${flower.name}", Toast.LENGTH_SHORT).show()
            flowerDescriptionViewModel.deleteFlower(flower)
        }

        return view
    }
}