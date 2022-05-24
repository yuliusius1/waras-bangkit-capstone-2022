package com.yulius.warasapp.ui.diagnose.diagnose3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.databinding.ActivityDiagnose3Binding
import com.yulius.warasapp.ui.diagnose.diagnose4.Diagnose4Activity
import com.yulius.warasapp.ui.diagnose.diagnose2.Diagnose2Activity

class Diagnose3Activity : AppCompatActivity() {
    private lateinit var binding : ActivityDiagnose3Binding
    private lateinit var dataDiagnose : Diagnose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose3Binding.inflate(layoutInflater)
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