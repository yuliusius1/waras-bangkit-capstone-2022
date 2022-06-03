package com.yulius.warasapp.ui.profile.history.daily_report

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.adapter.DailyReportAdapter
import com.yulius.warasapp.adapter.DetailHistoryAdapter
import com.yulius.warasapp.data.model.History
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityDailyReportBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class DailyReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyReportBinding
    private lateinit var historyData : History
    private var adapter = DailyReportAdapter()
    private lateinit var viewModel : DailyReportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDailyReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupData() {
        historyData = intent.getParcelableExtra<History>("historyData") as History
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DailyReportViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }

        viewModel.setData(historyData)
        viewModel.listReports.observe(this){
            Log.d("TAG", "setupViewModel: $it")
        }
    }

    private fun setupView() {
        showLoading()
        supportActionBar?.hide()
    }

    private fun setupAction() {

    }

    private fun showLoading() {
        viewModel.isLoading.observe(this) {
            binding.apply {
                when {
                    it -> progressBar.visibility = View.VISIBLE
                    else -> progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}