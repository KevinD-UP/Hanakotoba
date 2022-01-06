package com.kevin.hanakotoba

import android.annotation.SuppressLint
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

import com.kevin.hanakotoba.viewmodels.AddNewFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable
import java.io.FileOutputStream


@AndroidEntryPoint
class AddNewFlower : Fragment() {

    private lateinit var binding : AddNewFlowerFragmentBinding
    private lateinit var viewModel: AddNewFlowerViewModel
    private lateinit var imageFlower: String

    private val selectPictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.imageView.setImageURI(it)

        val draw = binding.imageView.drawable as BitmapDrawable
        val bitmap = draw.bitmap

        val outStream: FileOutputStream?
        val sdCard = this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val dir = File(sdCard!!.absolutePath)
        dir.mkdirs()
        val fileName = String.format("%d.jpg", System.currentTimeMillis())
        val outFile = File(dir, fileName)
        outStream = FileOutputStream(outFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        imageFlower = outFile.absolutePath
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

        val view = inflater.inflate(R.layout.add_new_flower_fragment, container, false)
        binding = AddNewFlowerFragmentBinding.bind(view)

        imageFlower = ""

        viewModel = ViewModelProvider(this)[AddNewFlowerViewModel::class.java]

        binding.addFlowerBtn.setOnClickListener {
            try {
                val name = binding.name.text.toString()
                val latinName = binding.latinName.text.toString()
                val description = binding.description.text.toString()
                val wateringInterval = binding.wateringInterval.text.toString()
                val imageFlower = this.imageFlower

                if((name != "" || latinName != "") && wateringInterval != "") {
                    val flowerToAdd = Flower(
                        name = name,
                        latinName = latinName,
                        description = description,
                        wateringInterval = wateringInterval.toInt(),
                        imageUrl = imageFlower
                    )
                    viewModel.addFlower(flowerToAdd)
                    Toast.makeText(
                        context,
                        "Flower $name ($latinName) has been added",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Field aren't completed correctly, please verify informations",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }catch (e: Exception){
                Toast.makeText(
                    context,
                    "Field aren't completed correctly, please verify informations",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.takePictureBtn.setOnClickListener {
            tempImageUri = FileProvider.getUriForFile(this.requireContext(), "com.kevin.hanakotoba.provider", createImageFile())

            imageFlower = tempImageFilePath

            cameraLauncher.launch(tempImageUri)
        }

        binding.choosePictureBtn.setOnClickListener {
            selectPictureLauncher.launch("image/*")
        }

        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "tempImage_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            tempImageFilePath = absolutePath
        }
    }
}