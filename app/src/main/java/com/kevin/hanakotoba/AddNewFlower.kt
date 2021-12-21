package com.kevin.hanakotoba

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.AddNewFlowerFragmentBinding

import com.kevin.hanakotoba.viewmodels.AddNewFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewFlower : Fragment() {

    private lateinit var binding : AddNewFlowerFragmentBinding
    private lateinit var viewModel: AddNewFlowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_new_flower_fragment, container, false)
        binding = AddNewFlowerFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(AddNewFlowerViewModel::class.java)

        binding.addFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[FlowerDescription - onCreateView] Add : ", Toast.LENGTH_SHORT).show()
            val name = binding.name.text.toString()
            val latinName = binding.latinName.text.toString()
            val description = binding.description.text.toString()
            val wateringInterval = binding.wateringInterval.text.toString().toInt()

            val flowerToAdd = Flower(name = name, latinName = latinName, description = description, wateringInterval = wateringInterval)
            viewModel.addFlower(flowerToAdd)
        }

        return view
    }

}