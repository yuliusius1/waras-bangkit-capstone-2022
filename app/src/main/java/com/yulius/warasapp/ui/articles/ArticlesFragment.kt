package com.yulius.warasapp.ui.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulius.warasapp.adapter.ListArticleAdapter
import com.yulius.warasapp.adapter.OnItemClickCallback
import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.FragmentArticlesBinding
import com.yulius.warasapp.util.ViewModelFactory
import java.util.ArrayList

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ArticlesFragment : Fragment() {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Article>()
    private lateinit var viewModel: ArticlesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(
                    UserPreference.getInstance(
                        requireContext().dataStore
                    )
                )
            )[ArticlesViewModel::class.java]

        var adapter = ListArticleAdapter()

        with(binding) {
            rvArticles.layoutManager = LinearLayoutManager(context)
            rvArticles.setHasFixedSize(true)
            rvArticles.adapter = adapter
        }

        viewModel.setNews()
        viewModel.getNews().observe(viewLifecycleOwner){
            adapter.setNFTs(it.articles)
            adapter.notifyDataSetChanged()
        }

        adapter.setOnItemClickCallback(object :
            OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                val intent =
                    Intent(context, DetailArticleActivity::class.java)
                intent.putExtra("urlArticle", data.url)
                startActivity(intent)
            }
        })


        super.onViewCreated(view, savedInstanceState)
    }
}