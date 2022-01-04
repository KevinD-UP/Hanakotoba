package com.kevin.hanakotoba.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.kevin.hanakotoba.FlowerDescriptionFragment

import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.ItemLayout2Binding
import dagger.hilt.android.internal.managers.FragmentComponentManager
import android.graphics.BitmapFactory

import java.io.File


class ResearchFlowersAdapter : RecyclerView.Adapter<ResearchFlowersAdapter.VH>() {

    private var flowerList = emptyList<Flower>()

    var sortBy = ""

    private val callback = object : SortedList.Callback<Flower>() {
        override fun compare(o1: Flower?, o2: Flower?): Int {
         if (o1 != null && o2 != null) {
             if(sortBy == "wateringInterval") {
                 if (o1.wateringInterval < o2.wateringInterval) {
                     return -1
                 } else if (o1.wateringInterval > o2.wateringInterval) {
                     return 1
                 }
             }
         }
            return 0

        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, itemCount)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count)
        }

        override fun areContentsTheSame(oldItem: Flower?, newItem: Flower?): Boolean =
            oldItem?.name == newItem?.name && oldItem?.wateringInterval == newItem?.wateringInterval

        override fun areItemsTheSame(item1: Flower?, item2: Flower?): Boolean =
            item1 === item2
    }

    private val sortedList = SortedList(Flower::class.java, callback)

    inner class VH (val binding : ItemLayout2Binding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayout2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = sortedList[position]
        val imgFile = File(currentItem.imageUrl)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            holder.binding.imageView.setImageBitmap(myBitmap)
        }
        holder.binding.userFlowerName.text = currentItem.name
        holder.binding.description.text = currentItem.description

        holder.itemView.setOnClickListener {
            val activity =  FragmentComponentManager.findActivity(holder.itemView.context) as AppCompatActivity
            val fragment : DialogFragment = FlowerDescriptionFragment()
            val bundle = Bundle()
            bundle.putSerializable("flower", currentItem)
            fragment.arguments = bundle
            fragment.show(activity.supportFragmentManager,"dialog")
        }

    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setSortFilter(filter : String = "") {
        sortBy = filter
        sortedList.clear()
        sortedList.addAll(flowerList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFlower(flower :List<Flower> ){
        this.flowerList = flower
        sortedList.clear()
        sortedList.addAll(flowerList)
        notifyDataSetChanged()
    }


}