package com.kevin.hanakotoba.fragments.MyFlowers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.data.FlowerViewModel
import com.kevin.hanakotoba.databinding.FragmentUserFlowerDescriptionBinding

private const val ARG_PARAM1 = "param1"

class UserFlowerDescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentUserFlowerDescriptionBinding
    private lateinit var mFlowerViewModel : FlowerViewModel

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
        val view = inflater.inflate(R.layout.fragment_user_flower_description, container, false)

        binding= FragmentUserFlowerDescriptionBinding.bind(view)
        mFlowerViewModel = ViewModelProvider(this).get(FlowerViewModel::class.java)

        binding.flowerNameTxt.text = flowerName

        binding.deleteFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Delete ", Toast.LENGTH_SHORT).show();

        }

        binding.waterFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Water : ", Toast.LENGTH_SHORT).show();

        }

        return view
    }

    //TODO: PASS FLOWER OBJECT INSTEAD OF THE NAME/ID ?
    companion object {
        @JvmStatic
        fun newInstance(flowerName: String) =
            UserFlowerDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, flowerName)
                }
            }
    }
}