package com.kevin.hanakotoba

import android.app.AlertDialog
import android.content.DialogInterface
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
import kotlin.concurrent.thread

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

        binding = FragmentUserFlowerDescriptionBinding.bind(view)
        val bundle = arguments
        flower = bundle!!.getSerializable("flower") as Flower

        flowerDescriptionViewModel = ViewModelProvider(this).get(FlowerDescriptionViewModel::class.java)

        binding.flowerNameTxt.text = flower.name

        binding.deleteFlowerBtn.setOnClickListener {
            deleteEvent(flower.flower_id)
        }

        if(flower.shouldBeWatered()) {
            binding.waterFlowerBtn.visibility = View.VISIBLE
        } else {
            binding.waterFlowerBtn.visibility = View.GONE
        }

        binding.waterFlowerBtn.setOnClickListener {
            flower.watered()
            flowerDescriptionViewModel.updateFlowerInGarden(flower.flower_id)
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Water : ", Toast.LENGTH_SHORT).show();
        }

        binding.updateBtn.setOnClickListener {
            val updateFlowerFragment = UpdateFlower()
            val activity = requireActivity()

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, updateFlowerFragment)
                .addToBackStack(null).commit()
        }

        return view
    }

    private fun deleteEvent(flower_id: Int){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Delete ${flower.name}")
        builder.setMessage("Are you sure you want to delete this flower ?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            flowerDescriptionViewModel.deleteFlowerInGarden(flower_id)
            Toast.makeText(
                context,
                "[FlowerDescription - onCreateView] Delete : ${flower.name}",
                Toast.LENGTH_SHORT
            ).show()
            dialog.cancel()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }
}