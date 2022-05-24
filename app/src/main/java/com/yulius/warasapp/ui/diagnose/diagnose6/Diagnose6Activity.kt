package com.yulius.warasapp.ui.diagnose.diagnose6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.databinding.ActivityDiagnose6Binding
import com.yulius.warasapp.ui.diagnose.diagnose7.Diagnose7Activity
import com.yulius.warasapp.ui.diagnose.diagnose5.Diagnose5Activity

class Diagnose6Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnose6Binding
    private lateinit var dataDiagnose : Diagnose
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose6Binding.inflate(layoutInflater)
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