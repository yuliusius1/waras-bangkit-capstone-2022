package com.yulius.warasapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.databinding.ItemRowArticleBinding
import com.yulius.warasapp.util.dateFormat
import java.util.ArrayList

class ListArticleAdapter : RecyclerView.Adapter<ListArticleAdapter.ListViewHolder>() {

    private var listArticle= ArrayList<Article>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setArticles(data: List<Article>?) {
        if (data == null) return
        this.listArticle.clear()
        this.listArticle.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (source, _,title,description,_,urlToImage, publishedAt,_) = listArticle[position]
        holder.binding.newsTitle.text = title
        holder.binding.newsText.text = description
        holder.binding.newsDate.text = dateFormat(publishedAt)
        holder.binding.newsSource.text = source?.name
        Glide.with(holder.binding.root)
            .load(urlToImage)
            .into(holder.binding.newsImage)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listArticle[position])
        }
    }

    override fun getItemCount(): Int = listArticle.size

    class ListViewHolder(var binding: ItemRowArticleBinding) : RecyclerView.ViewHolder(binding.root)

}