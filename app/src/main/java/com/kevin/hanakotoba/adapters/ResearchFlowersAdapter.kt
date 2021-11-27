package com.kevin.hanakotoba.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.ItemLayout2Binding
import com.kevin.hanakotoba.fragments.FlowerDescription.FlowerDescription


class ResearchFlowersAdapter : RecyclerView.Adapter<ResearchFlowersAdapter.VH>() {

    private var flowerList = emptyList<Flower>()

    inner class VH (val binding : ItemLayout2Binding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =ItemLayout2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = flowerList[position]
        holder.binding.userFlowerName.text = currentItem.name

        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as AppCompatActivity

            //TODO: PASS FLOWER OBJECT INSTEAD OF THE NAME/ID ?
            val testFragment = FlowerDescription.newInstance(currentItem.name)

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,testFragment)
                .addToBackStack(null).commit()


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