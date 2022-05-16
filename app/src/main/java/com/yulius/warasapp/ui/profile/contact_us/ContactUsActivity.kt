package com.yulius.warasapp.ui.profile.contact_us

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.R
import com.yulius.warasapp.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}