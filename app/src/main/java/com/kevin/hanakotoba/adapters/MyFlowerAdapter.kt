package com.kevin.hanakotoba.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.kevin.hanakotoba.Alarm
import com.kevin.hanakotoba.UserFlowerDescriptionFragment
import com.kevin.hanakotoba.data.FlowerAndGarden
import com.kevin.hanakotoba.databinding.ItemLayoutBinding
import com.kevin.hanakotoba.viewmodels.FlowerDescriptionViewModel
import dagger.hilt.android.internal.managers.FragmentComponentManager
import java.util.*
import java.util.concurrent.TimeUnit
import java.io.File


class MyFlowerAdapter (private var flowerDescriptionViewModel: FlowerDescriptionViewModel) : RecyclerView.Adapter<MyFlowerAdapter.VH>() {

    private var flowerList = emptyList<FlowerAndGarden>()
    private lateinit var context : Context
    inner class VH (val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return VH(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentFlowerAndGarden = flowerList[position]
        holder.binding.userFlowerName.text = currentFlowerAndGarden.flower.name

        val msDiff = currentFlowerAndGarden.garden.nextWateringDate.timeInMillis - Calendar.getInstance().timeInMillis
        val daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff)
        Log.d("TIME",daysDiff.toString())
        val imgFile = File(currentFlowerAndGarden.flower.imageUrl)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            holder.binding.imageView.setImageBitmap(myBitmap)
        }


        holder.binding.nextWateringTxt.text = "Water in $daysDiff days"
        if(currentFlowerAndGarden.canBeWatered()) {
            holder.binding.waterButton.visibility = View.VISIBLE
        } else {
            holder.binding.waterButton.visibility = View.GONE
        }

        holder.binding.waterButton.setOnClickListener {
            currentFlowerAndGarden.garden.lastWateringDate = currentFlowerAndGarden.garden.nextWateringDate
            val copy = currentFlowerAndGarden.garden.nextWateringDate.clone() as Calendar
            copy.add(Calendar.DATE,currentFlowerAndGarden.flower.wateringInterval)
            currentFlowerAndGarden.garden.nextWateringDate = copy

            setAlarm(currentFlowerAndGarden,copy)
            flowerDescriptionViewModel.updateFlowerInGarden(currentFlowerAndGarden.garden)
        }

        holder.itemView.setOnClickListener {
            val activity =  FragmentComponentManager.findActivity(holder.itemView.context) as AppCompatActivity
            val fragment : DialogFragment = UserFlowerDescriptionFragment()
            val bundle = Bundle()
            bundle.putSerializable("flower", currentFlowerAndGarden)
            fragment.arguments = bundle
            fragment.show(activity.supportFragmentManager,"dialog")
        }

    }

    override fun getItemCount(): Int {
        return flowerList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setFlower(flower: List<FlowerAndGarden>){

        Log.d("DEBUG","Setting flower list")
        this.flowerList = flower
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setAlarm(flower : FlowerAndGarden, calendar : Calendar){
        val alarm = Alarm(context)
        alarm.createNotificationChannel()
        alarm.scheduleNotification(flower.flower.name,flower.garden.gardenId.toString(),calendar)
    }

}