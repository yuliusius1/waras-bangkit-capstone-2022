package com.yulius.warasapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.R

class ListArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(
    itemView
) {
    var newsTitle: TextView = itemView.findViewById(R.id.news_title)
    var newsText: TextView = itemView.findViewById(R.id.news_text)
    var newsSource: TextView = itemView.findViewById(R.id.news_source)
    var newsDate: TextView = itemView.findViewById(R.id.news_date)
    var newsImage: ImageView = itemView.findViewById(R.id.news_image)

}