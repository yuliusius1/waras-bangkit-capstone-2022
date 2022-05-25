package com.yulius.warasapp.ui.diagnose.diagnose3

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
import com.yulius.warasapp.databinding.ActivityDiagnose3Binding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.diagnose.diagnose4.Diagnose4Activity
import com.yulius.warasapp.ui.diagnose.diagnose2.Diagnose2Activity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class Diagnose3Activity : AppCompatActivity() {
    private lateinit var binding : ActivityDiagnose3Binding
    private lateinit var dataDiagnose : Diagnose
    private lateinit var viewModel: Diagnose3ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose3Binding.inflate(layoutInflater)
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
        )[Diagnose3ViewModel::class.java]

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
        when(dataDiagnose.tired){
            1 -> binding.rbYes.isChecked = true
            else -> binding.rbNo.isChecked = true
        }
    }

    private fun setupAction() {
        binding.apply {
            rvSwitch.setOnCheckedChangeListener{ _, checkedId ->
                when(checkedId) {
                    R.id.rb_yes -> dataDiagnose.tired = 1
                    else -> dataDiagnose.tired = 0
                }
            }

            previousbtn.setOnClickListener {
                val intent = Intent(this@Diagnose3Activity, Diagnose2Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }

            nextbtn.setOnClickListener {
                val intent = Intent(this@Diagnose3Activity, Diagnose4Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }
        }
    }
}