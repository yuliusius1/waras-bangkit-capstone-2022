package com.yulius.warasapp.ui.profile.history

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yulius.warasapp.adapter.HistoryAdapter
import com.yulius.warasapp.adapter.ListArticleAdapter
import com.yulius.warasapp.adapter.OnItemClickCallback
import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.data.model.Contact
import com.yulius.warasapp.data.model.History
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityHistoryBinding
import com.yulius.warasapp.ui.articles.DetailArticleActivity
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.profile.history.detail_history.DetailHistoryActivity
import com.yulius.warasapp.util.DEFAULT_QUERY_ARTICLES
import com.yulius.warasapp.util.ViewModelFactory
import java.util.ArrayList

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private var adapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()

    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[HistoryViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                viewModel.setHistory(it.id)
            }
        }

        with(binding) {
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.setHasFixedSize(true)
            rvHistory.adapter = adapter
        }

        viewModel.getHistory().observe(this){
            adapter.setHistory(it)
            adapter.notifyDataSetChanged()
        }

        adapter.setOnItemClickCallback(object :
            HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: History) {
                Log.d("TAG", "onItemClicked: $data")
                val intent =
                    Intent(this@HistoryActivity, DetailHistoryActivity::class.java)
                intent.putExtra("historyData", data)
                startActivity(intent)
            }
        })
    }

    private fun setupAction() {
        binding.apply {
            ivAvatar.setOnClickListener{
                onBackPressed()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        viewModel.getHistory().observe(this){
            adapter.setHistory(it)
            adapter.notifyDataSetChanged()
        }
        super.onResume()
    }
}