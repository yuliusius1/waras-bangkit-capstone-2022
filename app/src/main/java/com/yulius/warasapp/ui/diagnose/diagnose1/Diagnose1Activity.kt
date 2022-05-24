package com.yulius.warasapp.ui.diagnose.diagnose1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.databinding.ActivityDiagnose1Binding
import com.yulius.warasapp.ui.diagnose.diagnose2.Diagnose2Activity
import com.yulius.warasapp.ui.main.MainActivity

class Diagnose1Activity : AppCompatActivity() {

    private lateinit var binding : ActivityDiagnose1Binding
    private lateinit var dataDiagnose : Diagnose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setupView()
        setupAction()
    }

    private fun getData() {
        dataDiagnose = intent.getParcelableExtra<Diagnose>("dataDiagnose") as Diagnose
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.apply {
            previousbtn.setOnClickListener {
                val intent = Intent(this@Diagnose1Activity, MainActivity::class.java)
                startActivity(intent)
            }

            nextbtn.setOnClickListener {
                val intent = Intent(this@Diagnose1Activity, Diagnose2Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }
        }
    }
}