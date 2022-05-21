package com.yulius.warasapp.ui.articles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.hide()
        val data = intent.getStringExtra("urlArticle")
        binding.webView.loadUrl(data.toString())

    }

    private fun setupAction() {
    }
}