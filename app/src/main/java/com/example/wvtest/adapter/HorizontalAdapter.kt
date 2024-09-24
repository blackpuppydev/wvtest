package com.example.wvtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wvtest.R
import com.example.wvtest.databinding.HorizontalAdapterBinding
import com.example.wvtest.model.Items

abstract class HorizontalAdapter (var listHorizontal:ArrayList<Items>) :
    RecyclerView.Adapter<HorizontalAdapter.HorizontalAdapterViewHolder>() {

    inner class HorizontalAdapterViewHolder(var binding: HorizontalAdapterBinding):RecyclerView.ViewHolder(binding.root){

        fun setItem(items:Items){
            binding.apply {
                Glide.with(image.context)
                    .load(items.coverUrl)
                    .error(R.drawable.errorpic)
                    .into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalAdapterViewHolder {
        val binding = HorizontalAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HorizontalAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int = listHorizontal.size

    override fun onBindViewHolder(holder: HorizontalAdapterViewHolder, position: Int) {
        holder.setItem(listHorizontal[position])

        holder.binding.image.setOnClickListener {
            onSelectItem()
        }

    }

    abstract fun onSelectItem()

}