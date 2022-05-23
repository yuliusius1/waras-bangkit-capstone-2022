package com.yulius.warasapp.ui.diagnose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.databinding.ActivityDiagnose3Binding

class Diagnose3Activity : AppCompatActivity() {
    private lateinit var binding : ActivityDiagnose3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnose3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextbtn.setOnClickListener {
            startActivity(Intent(this, Diagnose4Activity::class.java))
            finish()
        }
    }
}