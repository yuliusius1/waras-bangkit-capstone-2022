package com.yulius.warasapp.adapter.paging

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.Article

class ListArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(
    itemView
) {
    fun bind(articles: Article) {
        var newsTitle: TextView = itemView.findViewById(R.id.news_title)
        var newsText: TextView = itemView.findViewById(R.id.news_text)
        var newsSource: TextView = itemView.findViewById(R.id.news_source)
        var newsDate: TextView = itemView.findViewById(R.id.news_date)
        var newsImage: ImageView = itemView.findViewById(R.id.news_image)

        newsTitle.text = articles.title
        newsDate.text = articles.publishedAt
        newsSource.text = articles.source?.name.toString()
        newsText.text = articles.description
    }
}