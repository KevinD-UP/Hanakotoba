package com.kevin.hanakotoba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.FragmentUserFlowerDescriptionBinding
import com.kevin.hanakotoba.viewmodels.FlowerDescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFlowerDescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentUserFlowerDescriptionBinding
    private lateinit var flowerDescriptionViewModel: FlowerDescriptionViewModel
    private lateinit var flower: Flower;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_flower_description, container, false)

        binding= FragmentUserFlowerDescriptionBinding.bind(view)

        binding.flowerNameTxt.text = flower.name

        binding.deleteFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Delete ", Toast.LENGTH_SHORT).show();
            flowerDescriptionViewModel.deleteFlowerInGarden(flower.flower_id)
        }

        binding.waterFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Water : ", Toast.LENGTH_SHORT).show();
            flower.watered()
        }

        return view
    }
}