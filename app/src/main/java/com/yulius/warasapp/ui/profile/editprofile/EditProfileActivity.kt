package com.yulius.warasapp.ui.profile.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}