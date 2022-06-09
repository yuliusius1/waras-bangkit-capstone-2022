package com.yulius.warasapp.ui.profile.history.detail_history

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yulius.warasapp.R
import com.yulius.warasapp.adapter.DetailHistoryAdapter
import com.yulius.warasapp.data.model.History
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityDetailHistoryBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.profile.history.daily_report.DailyReportActivity
import com.yulius.warasapp.ui.profile.history.report.ReportActivity
import com.yulius.warasapp.util.ViewModelFactory
import com.yulius.warasapp.util.changeTimeFormat
import com.yulius.warasapp.util.changeTimeFormatCreatedAt

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    private lateinit var viewModel: DetailHistoryViewModel
    private lateinit var historyData : History
    private var adapter = DetailHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setupRecycleView()
        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupRecycleView() {
        with(binding) {
            rvSymptoms.layoutManager = LinearLayoutManager(this@DetailHistoryActivity)
            rvSymptoms.setHasFixedSize(true)
            rvSymptoms.adapter = adapter
        }
    }

    private fun getData() {
        historyData = intent.getParcelableExtra<History>("historyData") as History
    }

    private fun setupView() {
        showLoading()
        supportActionBar?.hide()
        binding.apply {
            tvEndDate.text = changeTimeFormat(historyData.date_to_heal)
            tvStartDate.text = changeTimeFormatCreatedAt(historyData.created_at.substring(0,10))
            tvSecondText.text = getString(R.string.text_predict, historyData.day_to_heal)

            if(historyData.status == "Sembuh"){
                btnReport.isEnabled = false
                btnReport.text = getString(R.string.you_already_recover)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailHistoryViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                binding.tvUname.text = it.full_name
            }
        }
        viewModel.setSymptoms(historyData.id_diagnose)
        viewModel.listData.observe(this){
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupAction() {
        binding.apply {
            ivAvatar.setOnClickListener{
                onBackPressed()
            }

            btnDailyReport.setOnClickListener {
                val intent = Intent(this@DetailHistoryActivity, DailyReportActivity::class.java)
                intent.putExtra("historyData",historyData)
                startActivity(intent)
            }

            btnReport.setOnClickListener {
                val intent = Intent(this@DetailHistoryActivity, ReportActivity::class.java)
                intent.putExtra("historyData",historyData)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
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