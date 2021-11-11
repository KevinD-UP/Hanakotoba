package com.kevin.hanakotoba.fragments.MyFlowers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.ItemLayoutBinding
import com.kevin.hanakotoba.fragments.FlowerDescription.FlowerDescription


class MyFlowersAdapter : RecyclerView.Adapter<MyFlowersAdapter.VH>() {

    private var flowerList = emptyList<Flower>()

    inner class VH(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = flowerList[position]
        holder.binding.textView.text = currentItem.name

        holder.itemView.setOnClickListener {
            /*Toast.makeText(holder.itemView.context, currentItem.name, Toast.LENGTH_SHORT).show();*/
            val activity = holder.itemView.context as AppCompatActivity

            val testFragment = FlowerDescription.newInstance(currentItem.name)

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, testFragment)
                .addToBackStack(null).commit()


        }

    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFlower(flower: List<Flower>) {
        this.flowerList = flower
        notifyDataSetChanged()
    }

}
