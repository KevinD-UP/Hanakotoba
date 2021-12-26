package com.kevin.hanakotoba

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
        createNotificationChannel()

        binding.getPicture.setOnClickListener {

        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.datePicker.setText("$day / $month / $year")

        binding.datePicker.setOnClickListener{
            val pickDate = DatePickerDialog(requireContext(),3,DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                Log.d("Date", "$dayOfMonth $monthOfYear $year")
                binding.datePicker.setText("$dayOfMonth / $monthOfYear / $year")
                c.set(year,monthOfYear,dayOfMonth)

            }, year, month, day)

            pickDate.show()
        }

        binding.AddPlantBtn.setOnClickListener {
            flowerDescriptionViewModel.addFlowerInGarden(flower.flower_id,c)
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            Toast.makeText(context, "$day $month $year ", Toast.LENGTH_SHORT).show()


            scheduleNotification((flower.flower_id).toString())
            dismiss()
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification(id : String) {

        val intent = Intent(activity, Notification::class.java)
        val title = flower.name
        val message = "It's a fucking flower"
        intent.putExtra(titleExtra,title)
        intent.putExtra(messageExtra,message)

 /*       if(id != ""){
            intent.setAction(id)
        }*/

        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        showAlert(time,title,message)

    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)

        AlertDialog.Builder(context)
            .setTitle("Notification Scheduled")
            .setMessage("Title " + title + " Message" + message + " \nAt" + dateFormat.format(date) + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()

    }


    private fun getTime() : Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name ="Notif channel"
        val desc = "A description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID,name,importance)
        channel.description = desc

        val notificationManager = activity?.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}