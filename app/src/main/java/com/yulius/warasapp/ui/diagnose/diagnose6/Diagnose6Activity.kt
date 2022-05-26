package com.yulius.warasapp.ui.diagnose.diagnose6

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityDiagnose6Binding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.diagnose.diagnose7.Diagnose7Activity
import com.yulius.warasapp.ui.diagnose.diagnose5.Diagnose5Activity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class Diagnose6Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnose6Binding
    private lateinit var dataDiagnose : Diagnose
    private lateinit var viewModel: Diagnose6ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[Diagnose6ViewModel::class.java]

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

    private fun getData() {
        dataDiagnose = intent.getParcelableExtra<Diagnose>("dataDiagnose") as Diagnose
    }

    private fun setupView() {
        supportActionBar?.hide()
        when(dataDiagnose.short_breath){
            1 -> binding.rbYes.isChecked = true
            else -> binding.rbNo.isChecked = true
        }
    }

    private fun setupAction() {
        binding.apply {
            rvSwitch.setOnCheckedChangeListener{ _, checkedId ->
                when(checkedId) {
                    R.id.rb_yes -> dataDiagnose.short_breath = 1
                    else -> dataDiagnose.short_breath = 0
                }
            }

            previousbtn.setOnClickListener {
                val intent = Intent(this@Diagnose6Activity, Diagnose5Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }

            nextbtn.setOnClickListener {
                val intent = Intent(this@Diagnose6Activity, Diagnose7Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }
        }
    }
}