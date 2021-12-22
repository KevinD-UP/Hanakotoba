package com.kevin.hanakotoba

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevin.hanakotoba.databinding.UpdateFlowerFragmentBinding
import com.kevin.hanakotoba.viewmodels.AddNewFlowerViewModel
import com.kevin.hanakotoba.viewmodels.UpdateFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFlower : Fragment() {

    private lateinit var viewModel: UpdateFlowerViewModel
    private lateinit var binding: UpdateFlowerFragmentBinding

    companion object {
        val IMAGE_REQUEST_CODE = 42
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.update_flower_fragment, container, false)
        viewModel = ViewModelProvider(this).get(UpdateFlowerViewModel::class.java)
        binding = UpdateFlowerFragmentBinding.bind(view)

        binding.choosePictureBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.imageView.setImageURI(data?.data)
        }
    }
}