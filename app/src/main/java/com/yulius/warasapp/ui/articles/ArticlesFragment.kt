package com.yulius.warasapp.ui.articles

import android.content.Context
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulius.warasapp.adapter.ListArticleAdapter
import com.yulius.warasapp.adapter.LoadingStateAdapter
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.FragmentArticlesBinding
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ArticlesFragment : Fragment() {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ListArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater,container,false)
        adapter = ListArticleAdapter()

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

        viewModel.setNews()
        viewModel.getNews().observe(viewLifecycleOwner){
            Log.d("TAG", "onViewCreated: $it")
            adapter.setData(it)
        }

        with(binding) {
            rvArticles.layoutManager = LinearLayoutManager(context)
            rvArticles.setHasFixedSize(true)
            rvArticles.adapter = adapter
        }
        super.onViewCreated(view, savedInstanceState)
    }
}