package com.kevin.hanakotoba

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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
    private lateinit var flower: FlowerAndGarden

    @RequiresApi(Build.VERSION_CODES.O)
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

        val dateFormat = SimpleDateFormat("d / M / yyyy", Locale.FRANCE)
        val currentFlowerTime : Calendar = flower.garden.nextWateringDate

        binding.lastWateringTxt.setText(dateFormat.format(flower.garden.lastWateringDate.time))
        binding.nextWateringTxt.setText(dateFormat.format(flower.garden.nextWateringDate.time))
        binding.timePicker.setText(String.format("%02d:%02d", currentFlowerTime.get(Calendar.HOUR_OF_DAY), currentFlowerTime.get(Calendar.MINUTE)))

        displayWaterBtn()
        setNextDate(currentFlowerTime)
        setTime(currentFlowerTime)
        initDelete()
        initWater()
        initSave(currentFlowerTime)

        return view
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    private fun setNextDate(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.nextWateringTxt.setOnClickListener{
            val pickDate = DatePickerDialog(requireContext(),3,
                { _, year, monthOfYear, dayOfMonth ->
                    val month = monthOfYear +1
                    binding.nextWateringTxt.setText("$dayOfMonth / $month / $year")
                    calendar.set(year,monthOfYear,dayOfMonth)

                }, year, month, day)
            pickDate.show()
        }
    }

    private fun displayWaterBtn() {
        if(flower.canBeWatered()) {
            binding.waterFlowerBtn.visibility = View.VISIBLE
        } else {
            binding.waterFlowerBtn.visibility = View.GONE
        }

    }

    private fun setTime(calendar : Calendar) {
        binding.timePicker.setOnClickListener{
            val timePicker = TimePickerDialog(requireContext(),3,
                { _, hour, minute->
                    binding.timePicker.setText(String.format("%02d:%02d", hour, minute))
                    calendar.set(Calendar.HOUR_OF_DAY,hour)
                    calendar.set(Calendar.MINUTE,minute)
                    calendar.set(Calendar.SECOND,0)
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true)
            timePicker.show()
        }

    }

    private fun initDelete(){
        binding.deleteFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Delete ", Toast.LENGTH_SHORT).show()
            flowerDescriptionViewModel.deleteFlowerInGarden(flower.garden.gardenId)
            dismiss()

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initWater(){
        binding.waterFlowerBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Water ", Toast.LENGTH_SHORT).show()
            flower.garden.lastWateringDate = flower.garden.nextWateringDate
            val copy : Calendar= flower.garden.nextWateringDate.clone() as Calendar
            copy.add(Calendar.DATE,flower.flower.wateringInterval)
            flower.garden.nextWateringDate = copy
            setAlarm(copy)
            flowerDescriptionViewModel.updateFlowerInGarden(flower.garden)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initSave(calendar : Calendar){
        binding.saveBtn.setOnClickListener {
            Toast.makeText(context, "[UserFlowerDescriptionFragment - onCreateView] Save ", Toast.LENGTH_SHORT).show()
            flower.garden.nextWateringDate = calendar
            flowerDescriptionViewModel.updateFlowerInGarden(flower.garden)
            setAlarm(calendar)
            dismiss()

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setAlarm(calendar : Calendar){
        val alarm = Alarm(requireContext())
        alarm.createNotificationChannel()
        alarm.scheduleNotification(flower.flower.name,flower.garden.gardenId.toString(),calendar)
    }
}