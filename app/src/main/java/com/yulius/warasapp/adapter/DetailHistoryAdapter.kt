package com.yulius.warasapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.databinding.ItemSymptomsBinding
import java.util.ArrayList

class DetailHistoryAdapter : RecyclerView.Adapter<DetailHistoryAdapter.ListViewHolder>() {

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
        val binding = ItemSymptomsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.tvSymptoms.text = listData[position]
    }

    override fun getItemCount(): Int = listData.size


    class ListViewHolder(var binding: ItemSymptomsBinding) : RecyclerView.ViewHolder(binding.root)
}