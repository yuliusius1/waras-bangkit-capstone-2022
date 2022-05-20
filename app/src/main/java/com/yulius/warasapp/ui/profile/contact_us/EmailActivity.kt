package com.yulius.warasapp.ui.profile.contact_us

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yulius.warasapp.R
import com.yulius.warasapp.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Contact>("DATA")

        binding.tvItemEmail.text = getString(R.string.email_to) + data?.name
        binding.emailBtn.setOnClickListener {
            val intentToDetail = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "${data?.email}", null))
            startActivity(Intent.createChooser(intentToDetail, "Send email"))
        }
    }
}