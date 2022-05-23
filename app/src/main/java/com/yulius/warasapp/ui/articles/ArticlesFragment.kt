package com.yulius.warasapp.ui.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import com.yulius.warasapp.util.DEFAULT_QUERY_ARTICLES
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ArticlesFragment : Fragment() {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArticlesViewModel
    private var adapter = ListArticleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        showLoading()


        with(binding) {
            rvArticles.layoutManager = LinearLayoutManager(context)
            rvArticles.setHasFixedSize(true)
            rvArticles.adapter = adapter
        }

        viewModel.setNews(DEFAULT_QUERY_ARTICLES)
        viewModel.getNews().observe(viewLifecycleOwner){
            adapter.setArticles(it.articles)
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

        searchView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun searchView() {
        binding.svArticle.isSubmitButtonEnabled = true
        binding.svArticle.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                viewModel.setNews(p0)
                viewModel.getNews().observe(viewLifecycleOwner){
                    adapter.setArticles(it.articles)
                    adapter.notifyDataSetChanged()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun showLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.apply {
                when {
                    it -> progressBar.visibility = View.VISIBLE
                    else -> progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}