package com.yulius.warasapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.databinding.ItemRowRecommendationBinding
import com.yulius.warasapp.databinding.ItemSymptomsBinding
import java.util.ArrayList

class RecommendationAdapter : RecyclerView.Adapter<RecommendationAdapter.ListViewHolder>() {

    private var listData= ArrayList<String>()

    fun setData(data: List<String>?) {
        if (data == null) return
        this.listData.clear()
        this.listData.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.recText.text = listData[position]
    }

    override fun getItemCount(): Int = listData.size


    class ListViewHolder(var binding: ItemRowRecommendationBinding) : RecyclerView.ViewHolder(binding.root)
}