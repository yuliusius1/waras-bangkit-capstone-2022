package com.yulius.warasapp.ui.diagnose.diagnose2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.databinding.ActivityDiagnose2Binding
import com.yulius.warasapp.ui.diagnose.diagnose3.Diagnose3Activity
import com.yulius.warasapp.ui.diagnose.diagnose1.Diagnose1Activity

class Diagnose2Activity : AppCompatActivity() {
    private lateinit var binding : ActivityDiagnose2Binding
    private lateinit var dataDiagnose : Diagnose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose2Binding.inflate(layoutInflater)
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
                val intent = Intent(this@Diagnose2Activity, Diagnose1Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }

            nextbtn.setOnClickListener {
                val intent = Intent(this@Diagnose2Activity, Diagnose3Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }
        }
    }
}