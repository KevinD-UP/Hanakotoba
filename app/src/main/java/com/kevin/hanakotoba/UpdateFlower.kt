package com.kevin.hanakotoba

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.BitmapFactory
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
import com.kevin.hanakotoba.databinding.UpdateFlowerFragmentBinding
import com.kevin.hanakotoba.viewmodels.UpdateFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable
import java.io.FileOutputStream


@AndroidEntryPoint
class UpdateFlower : Fragment() {

    private lateinit var viewModel: UpdateFlowerViewModel
    private lateinit var binding: UpdateFlowerFragmentBinding
    private lateinit var imageFlower: String
    private lateinit var flower: Flower

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
        val view = inflater.inflate(R.layout.update_flower_fragment, container, false)
        binding = UpdateFlowerFragmentBinding.bind(view)

        val bundle = arguments
        flower = bundle!!.getSerializable("flower") as Flower

        //FILL FORM
        binding.name.setText(flower.name)
        binding.latinName.setText(flower.latinName)
        binding.description.setText(flower.description)
        binding.wateringInterval.setText(flower.wateringInterval.toString())

        imageFlower = flower.imageUrl

        val imgFile = File(flower.imageUrl)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            binding.imageView.setImageBitmap(myBitmap)
        }

        viewModel = ViewModelProvider(this).get(UpdateFlowerViewModel::class.java)

        binding.updateFlowerBtn.setOnClickListener {
            try {
                val flowerId = flower.flower_id
                val name = binding.name.text.toString()
                val latinName = binding.latinName.text.toString()
                val description = binding.description.text.toString()
                val wateringInterval = binding.wateringInterval.text.toString()
                val imageFlower = this.imageFlower

                if (name != "" && wateringInterval != "") {
                    val flowerToUpdate = Flower(
                        flowerId,
                        name = name,
                        latinName = latinName,
                        description = description,
                        wateringInterval = wateringInterval.toInt(),
                        imageUrl = imageFlower
                    )
                    viewModel.updateFlower(flowerToUpdate)
                    Toast.makeText(
                        context,
                        "Flower $name ($latinName) has been updated",
                        Toast.LENGTH_SHORT
                    ).show()
                    this.requireActivity().supportFragmentManager.popBackStack()
                } else {
                    alertEvent()
                }
            } catch (e: Exception) {
                alertEvent()
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

    private fun alertEvent(){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Can't update flower")
        builder.setMessage("Please fill all correctly required field !")
        builder.setPositiveButton("Okay") { dialog, _ ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }
}