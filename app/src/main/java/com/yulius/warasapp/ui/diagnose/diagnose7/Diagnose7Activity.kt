package com.yulius.warasapp.ui.diagnose.diagnose7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.data.model.Diagnose
import com.yulius.warasapp.databinding.ActivityDiagnose7Binding
import com.yulius.warasapp.ui.diagnose.diagnose6.Diagnose6Activity

class Diagnose7Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnose7Binding
    private lateinit var dataDiagnose: Diagnose
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose7Binding.inflate(layoutInflater)
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
                val intent = Intent(this@Diagnose7Activity, Diagnose6Activity::class.java)
                intent.putExtra("dataDiagnose", dataDiagnose)
                startActivity(intent)
            }

            nextbtn.setOnClickListener {
                val intent = Intent(this@Diagnose7Activity, Diagnose7Activity::class.java)
                startActivity(intent)
            }
        }
    }
}