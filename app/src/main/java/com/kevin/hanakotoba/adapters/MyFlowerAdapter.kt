package com.kevin.hanakotoba.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.kevin.hanakotoba.UserFlowerDescriptionFragment
import com.kevin.hanakotoba.data.AppDatabase
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.data.Garden
import com.kevin.hanakotoba.databinding.ItemLayoutBinding
import com.kevin.hanakotoba.dependencyInjection.DatabaseModule
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class MyFlowerAdapter : RecyclerView.Adapter<MyFlowerAdapter.VH>() {

    private var flowerList = emptyList<Flower>()
    private val databaseModule = DatabaseModule()

    inner class VH (val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = flowerList[position]
        holder.binding.userFlowerName.text = currentItem.name


        if(currentItem.shouldBeWatered()) {
            holder.binding.waterButton.visibility = View.VISIBLE
        } else {
            holder.binding.waterButton.visibility = View.GONE
        }

        holder.binding.waterButton.setOnClickListener {

            Toast.makeText(
                holder.itemView.context,
                "Watered : ${currentItem.name}",
                Toast.LENGTH_SHORT
            ).show()
            currentItem.watered()
            runBlocking { // this: CoroutineScope
                launch { // launch a new coroutine and continue
                    databaseModule
                            .providePlantDao(
                                    databaseModule
                                            .provideAppDatabase(holder.binding.waterButton.context)
                            )
                            .wateredFlower(currentItem.flower_id)
                }
            }
        }

        holder.itemView.setOnClickListener {

            val activity =  FragmentComponentManager.findActivity(holder.itemView.context) as AppCompatActivity

            val fragment : DialogFragment = UserFlowerDescriptionFragment()
            val bundle = Bundle()
            bundle.putSerializable("flower", currentItem);
            fragment.arguments = bundle

            fragment.show(activity.supportFragmentManager,"dialog")
        }

    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFlower(flower :List<Flower> ){
        this.flowerList = flower
        notifyDataSetChanged()
    }
}