package com.yulius.warasapp.ui.profile.history.daily_report

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.yulius.warasapp.R
import com.yulius.warasapp.adapter.DailyReportAdapter
import com.yulius.warasapp.adapter.DetailHistoryAdapter
import com.yulius.warasapp.data.model.DailyReportList
import com.yulius.warasapp.data.model.History
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityDailyReportBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.ui.profile.history.detail_history.DetailHistoryActivity
import com.yulius.warasapp.util.*

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

        with(binding) {
            rvDaily.layoutManager = LinearLayoutManager(this@DailyReportActivity)
            rvDaily.setHasFixedSize(true)
            rvDaily.adapter = adapter
        }

        viewModel.setData(historyData)
        viewModel.listReports.observe(this){
            adapter.setData(it)
            adapter.notifyDataSetChanged()

        }
    }

    private fun setupView() {
        showLoading()
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.apply {
            ivAvatar.setOnClickListener {
                onBackPressed()
            }
        }

        adapter.setOnItemClickCallback(object :
            DailyReportAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DailyReportList) {
                showReportDialog()
            }
        })
    }

    private fun showReportDialog() {
        val builder =
            AlertDialog.Builder(this, 0).create()
        val view =
            layoutInflater.inflate(R.layout.dialog_report, null)
        val title = view.findViewById<TextView>(R.id.tv_report_title)
        title.text = getString(R.string.daily_report_title, changeTimeFormat(todayDate()))
        val etReport = view.findViewById<TextInputLayout>(R.id.et_reports)
        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

        builder.setView(view)

        btnConfirm.setOnClickListener {
            val report = etReport.editText?.text.toString()
            viewModel.sendDailyReport(report,historyData,object : ResponseCallback{
                override fun getCallback(msg: String, status: Boolean) {
                    builder.dismiss()
                    showDialogs(msg,status)
                }
            })
        }
        btnCancel.setOnClickListener {
            builder.dismiss()
        }

        builder.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun showDialogs(msg: String, status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                setMessage(msg)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(this@DailyReportActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("historyData", historyData)
                    startActivity(intent)
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Oops")
                setMessage(msg)
                setNegativeButton(getString(R.string.repeat)) { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
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