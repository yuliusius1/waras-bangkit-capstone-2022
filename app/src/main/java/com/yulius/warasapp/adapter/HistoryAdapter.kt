package com.yulius.warasapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.data.model.History
import com.yulius.warasapp.databinding.ItemRowHistoryBinding
import java.util.ArrayList

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    private var listHistory= ArrayList<History>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setHistory(data: List<History>?) {
        if (data == null) return
        this.listHistory.clear()
        this.listHistory.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (
         id_history,
         day_to_heal ,
         date_to_heal ,
         status ,
         recommendations ,
         created_at ,
         id_user ) = listHistory[position]
        holder.binding.historyTitle.text = "Diagnose - $id_history"
        holder.binding.historyText.text = status
        holder.binding.dateStart.text =  created_at.substring(0,10)
        holder.binding.dateEnd.text = date_to_heal

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listHistory[position])
        }
    }

    override fun getItemCount(): Int = listHistory.size

    interface OnItemClickCallback {
        fun onItemClicked(data: History)
    }

    class ListViewHolder(var binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root)
}