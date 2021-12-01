package com.kevin.hanakotoba.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kevin.hanakotoba.FlowerDescriptionFragment
import com.kevin.hanakotoba.UserFlowerDescriptionFragment

import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.ItemLayout2Binding
import dagger.hilt.android.internal.managers.FragmentComponentManager

class ResearchFlowersAdapter : RecyclerView.Adapter<ResearchFlowersAdapter.VH>() {

    private var flowerList = emptyList<Flower>()

    inner class VH (val binding : ItemLayout2Binding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayout2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = flowerList[position]
        holder.binding.userFlowerName.text = currentItem.name

        holder.itemView.setOnClickListener {
            val activity =  FragmentComponentManager.findActivity(holder.itemView.context) as AppCompatActivity

            val fragment : DialogFragment = FlowerDescriptionFragment()

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