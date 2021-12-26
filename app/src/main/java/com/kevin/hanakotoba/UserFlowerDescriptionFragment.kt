package com.kevin.hanakotoba

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.data.FlowerAndGarden
import com.kevin.hanakotoba.databinding.FragmentUserFlowerDescriptionBinding
import com.kevin.hanakotoba.viewmodels.FlowerDescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UserFlowerDescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentUserFlowerDescriptionBinding
    private lateinit var flowerDescriptionViewModel: FlowerDescriptionViewModel
    private lateinit var flower: FlowerAndGarden;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_flower_description, container, false)

        binding = FragmentUserFlowerDescriptionBinding.bind(view)
        val bundle = arguments
        flower = bundle!!.getSerializable("flower") as FlowerAndGarden

        flowerDescriptionViewModel = ViewModelProvider(this).get(FlowerDescriptionViewModel::class.java)
        binding.flowerNameTxt.text = flower.flower.name

      /*  binding.testing.text = flower.lastWateringDate.toString()*/
        val dateFormat = SimpleDateFormat("d / M / yyyy", Locale.FRANCE)
        binding.lastWateringTxt.setText(dateFormat.format(flower.garden.lastWateringDate.time))


        binding.deleteFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Delete ", Toast.LENGTH_SHORT).show();
            flowerDescriptionViewModel.deleteFlowerInGarden(flower.flower.flower_id)
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.nextWateringTxt.setText("$day / $month / $year")

        binding.nextWateringTxt.setOnClickListener{
            val pickDate = DatePickerDialog(requireContext(),3,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                //Asignandole al textview
                Log.d("Date", "$dayOfMonth $monthOfYear $year")
                binding.nextWateringTxt.setText("$dayOfMonth / $monthOfYear / $year")

            }, year, month, day)

            pickDate.show()
        }

      /*  if(flower.shouldBeWatered()) {
            binding.waterFlowerBtn.visibility = View.VISIBLE
        } else {
            binding.waterFlowerBtn.visibility = View.GONE
        }*/

        binding.waterFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Water ", Toast.LENGTH_SHORT).show();
         /*   flower.watered()*/
            flowerDescriptionViewModel.updateFlowerInGarden(flower.flower.flower_id)
        }

        binding.saveBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Save ", Toast.LENGTH_SHORT).show();

        }

        return view
    }
}