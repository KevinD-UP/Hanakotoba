package com.kevin.hanakotoba

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.AddNewFlowerFragmentBinding
import com.kevin.hanakotoba.databinding.UpdateFlowerFragmentBinding
import com.kevin.hanakotoba.viewmodels.AddNewFlowerViewModel
import com.kevin.hanakotoba.viewmodels.UpdateFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UpdateFlower : Fragment() {

    private lateinit var viewModel: UpdateFlowerViewModel
    private lateinit var binding: UpdateFlowerFragmentBinding
    private lateinit var imageFlower: String

    private val selectPictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.imageView.setImageURI(it)
        imageFlower = it.path.toString()
    }

    private var tempImageUri: Uri? = null
    private var tempImageFilePath = ""
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){ success ->
        if(success){
            binding.imageView.setImageURI(tempImageUri)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.update_flower_fragment, container, false)
        binding = UpdateFlowerFragmentBinding.bind(view)

        imageFlower = ""

        viewModel = ViewModelProvider(this).get(UpdateFlowerViewModel::class.java)

        binding.updateFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UpdateFlower - onCreateView] Update : ", Toast.LENGTH_SHORT).show()
            val name = binding.name.text.toString()
            val latinName = binding.latinName.text.toString()
            val description = binding.description.text.toString()
            val wateringInterval = binding.wateringInterval.text.toString().toInt()
            val imageFlower = this.imageFlower

            val flowerToUpdate = Flower(name = name, latinName = latinName, description = description, wateringInterval = wateringInterval, imageUrl = imageFlower)
            viewModel.updateFlower(flowerToUpdate)
        }

        binding.takePictureBtn.setOnClickListener {
            tempImageUri = FileProvider.getUriForFile(this.requireContext(), "com.kevin.hanakotoba.provider", createImageFile().also {
                tempImageFilePath = it.absolutePath
            })

            cameraLauncher.launch(tempImageUri)
        }

        binding.choosePictureBtn.setOnClickListener {
            selectPictureLauncher.launch("image/*")
        }

        return view
    }

    private fun createImageFile(): File {
        val storageDir = this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("temp_image", ".jpg", storageDir)
    }
}