package com.kevin.hanakotoba

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.AddNewFlowerFragmentBinding

import com.kevin.hanakotoba.viewmodels.AddNewFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AddNewFlower : Fragment() {

    private lateinit var binding : AddNewFlowerFragmentBinding
    private lateinit var viewModel: AddNewFlowerViewModel

    val REQUEST_IMAGE_CAPTURE = 12345

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

        binding.name.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: Exception) {
                Toast.makeText(context, "Unable To Open Camera", Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === REQUEST_IMAGE_CAPTURE) {
            }
        }
    }
}