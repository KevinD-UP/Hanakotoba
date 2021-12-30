package com.kevin.hanakotoba

import android.annotation.SuppressLint
import android.app.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.viewmodels.FlowerDescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.kevin.hanakotoba.databinding.FragmentFlowerDescriptionBinding


@AndroidEntryPoint
class FlowerDescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentFlowerDescriptionBinding
    private lateinit var flowerDescriptionViewModel: FlowerDescriptionViewModel
    private lateinit var flower: Flower

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flower_description,container,false)
        val bundle = arguments

        flower = bundle!!.getSerializable("flower") as Flower
        flowerDescriptionViewModel = ViewModelProvider(this).get(FlowerDescriptionViewModel::class.java)

        binding = FragmentFlowerDescriptionBinding.bind(view)
        binding.flowerNameTxt.text = flower.name
        binding.WateringIntervalTxt.setText(flower.wateringInterval.toString())

        val alarm =  Alarm(requireContext())
        alarm.createNotificationChannel()

        val c = Calendar.getInstance()
        setDate(c)
        setTime(c)
        initAddPlant(alarm,c)

        return view
    }

    @SuppressLint("ResourceType")
    private fun setDate(c: Calendar) {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        binding.datePicker.setText("$day/$month/$year")

        binding.datePicker.setOnClickListener{
            val pickDate = DatePickerDialog(requireContext(),3,
                { view, year, monthOfYear, dayOfMonth ->
                    Log.d("Date", "$dayOfMonth $monthOfYear $year")
                    binding.datePicker.setText("$dayOfMonth/$monthOfYear/$year")
                    c.set(year,monthOfYear,dayOfMonth)

                }, year, month, day)

            pickDate.show()
        }
    }

    private fun setTime(c: Calendar) {
        c.set(Calendar.HOUR_OF_DAY,8)
        c.set(Calendar.MINUTE,0)
        c.set(Calendar.SECOND,0)

        binding.timePicker.setText(String.format("%02d:%02d", c.get(Calendar.HOUR), c.get(Calendar.MINUTE)))


        binding.timePicker.setOnClickListener {
            val timePicker = TimePickerDialog(requireContext(),3, { _, hour, minute->
                binding.timePicker.setText(String.format("%02d:%02d", hour, minute))
                c.set(Calendar.HOUR_OF_DAY,hour)
                c.set(Calendar.MINUTE,minute)
                c.set(Calendar.SECOND,0)
            },8,0,true)
            timePicker.show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initAddPlant(alarm : Alarm, calendar : Calendar) {
        binding.AddPlantBtn.setOnClickListener {
            flowerDescriptionViewModel.addFlowerInGarden(flower,calendar).observe(viewLifecycleOwner, {
                calendar.add(Calendar.DATE,flower.wateringInterval)
                alarm.scheduleNotification(flower.name,it.toString(),calendar)
            })
            dismiss()
        }
    }


}