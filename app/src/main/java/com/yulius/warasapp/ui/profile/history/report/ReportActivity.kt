package com.yulius.warasapp.ui.profile.history.report

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.History
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityReportBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ResponseCallback
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)


class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private lateinit var viewModel: ReportViewModel
    private lateinit var historyData : History

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
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
        )[ReportViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.apply {
            val reports = etReport.editText?.text
            var statusHistory = "Sembuh"
            chipStatus.setOnCheckedStateChangeListener() { _, checkedIds ->
                for (i in 0 until checkedIds.size) {
                    statusHistory = when(checkedIds[i]){
                        R.id.chip_1 -> "Sembuh"
                        else -> "Belum Sembuh"
                    }
                }
            }

            ivAvatar.setOnClickListener{
                onBackPressed()
            }
            btnSubmit.setOnClickListener {
                if(TextUtils.isEmpty(reports)){
                    etReport.editText?.error = "Report must be filled out"
                } else {
                    viewModel.sendReport(historyData, reports.toString(),statusHistory,object : ResponseCallback{
                        override fun getCallback(msg: String, status: Boolean) {
                            if(status){
                                viewModel.sendStatus(historyData,statusHistory,object : ResponseCallback{
                                    override fun getCallback(msg: String, status: Boolean) {
                                        showDialogs(msg, status)
                                    }
                                })
                            } else {
                                showDialogs(msg, status)
                            }
                        }
                    })
                }
            }
        }
    }

    private fun showDialogs(msg: String, status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                setMessage(msg)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(this@ReportActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}