package com.yulius.warasapp.ui.diagnose.result

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityResultDiagnoseBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.diagnose.diagnose7.Diagnose7Activity
import com.yulius.warasapp.ui.diagnose.recommendation.RecommendationActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.ui.profile.history.HistoryActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ResultDiagnoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultDiagnoseBinding
    private lateinit var dataDiagnose: Diagnose
    private lateinit var viewModel: ResultDiagnoseViewModel
    private var resultModel : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDiagnoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setupView()
        setupViewModel()
        setupAction()
    }

    private fun getData() {
        dataDiagnose = intent.getParcelableExtra<Diagnose>("dataDiagnose") as Diagnose
        resultModel = intent.getIntExtra("resultModel",0)
        binding.tvResult.text = getString(R.string.result_text, resultModel.toString())
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ResultDiagnoseViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                binding.tvUsername.text = it.full_name
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            submitBtn.setOnClickListener {
                val intent = Intent(this@ResultDiagnoseActivity, RecommendationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }
}