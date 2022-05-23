package com.yulius.warasapp.ui.diagnose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.databinding.ActivityDiagnose1Binding

class Diagnose1Activity : AppCompatActivity() {

    private lateinit var binding : ActivityDiagnose1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextbtn.setOnClickListener {
            startActivity(Intent(this, Diagnose3Activity::class.java))
            finish()
        }
    }
}