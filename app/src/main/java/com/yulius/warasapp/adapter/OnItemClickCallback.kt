package com.yulius.warasapp.adapter

import com.yulius.warasapp.data.model.Article

interface OnItemClickCallback {
    fun onItemClicked(data: Article)
}
