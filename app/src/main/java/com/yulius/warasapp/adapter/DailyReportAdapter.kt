package com.yulius.warasapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.data.model.DailyReportList
import com.yulius.warasapp.databinding.ItemRowDailyReportBinding
import com.yulius.warasapp.util.*

class DailyReportAdapter : RecyclerView.Adapter<DailyReportAdapter.ListViewHolder>() {

    private var listDailyReportList= ArrayList<DailyReportList>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(data: List<DailyReportList>?) {
        if (data == null) return
        this.listDailyReportList.clear()
        this.listDailyReportList.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowDailyReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (
            daily_report,
            _,
            _,
            created_date,
            _
        ) = listDailyReportList[position]
        if(daily_report != null){
            holder.binding.dailyReportText.text = daily_report
            holder.binding.btnAddReport.visibility = View.GONE
        } else {
            holder.binding.dailyText1.visibility = View.GONE
            holder.binding.dailyReportText.visibility = View.GONE
            if(changeFormatTime(created_date) == todayDate()){
                holder.binding.btnAddReport.setOnClickListener {
                    onItemClickCallback.onItemClicked(listDailyReportList[position])
                }
            } else {
                holder.binding.btnAddReport.isEnabled = false
            }

        }
        holder.binding.dailyDate.text = changeTimeFormatCreatedAt(created_date!!)
    }

    override fun getItemCount(): Int = listDailyReportList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: DailyReportList)
    }

    class ListViewHolder(var binding: ItemRowDailyReportBinding) : RecyclerView.ViewHolder(binding.root)
}