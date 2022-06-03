package com.yulius.warasapp.ui.diagnose.recommendation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulius.warasapp.R
import com.yulius.warasapp.adapter.RecommendationAdapter
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityRecommendationBinding
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ViewModelFactory
import java.util.ArrayList

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding
    private lateinit var viewModel: RecommendationViewModel
    private var adapter = RecommendationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RecommendationViewModel::class.java]
    }

    private fun setupView() {
        supportActionBar?.hide()
        with(binding) {
            rvRecommendation.layoutManager = LinearLayoutManager(this@RecommendationActivity)
            rvRecommendation.setHasFixedSize(true)
            rvRecommendation.adapter = adapter
        }

        adapter.setData(listRecommendation)
        adapter.notifyDataSetChanged()

    }

    private fun setupAction() {
        binding.ivAvatar.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.btnMain.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private val listRecommendation: ArrayList<String>
        get() {
            val dataRecommendation = resources.getStringArray(R.array.data_recommendation).toList()
            val listData = ArrayList<String>()
            for (i in dataRecommendation.indices) {
                listData.add(dataRecommendation[i])
            }
            return listData
        }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}