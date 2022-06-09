package com.yulius.warasapp.ui.profile.contact_us

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.Contact
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityEmailBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)
class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding
    private lateinit var viewModel: ContactUsViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Contact>("DATA")

        binding.tvItemEmail.text = getString(R.string.email_to) + " " + data?.name
        binding.emailBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(getString(R.string.mail_to), "${data?.email}", null))
            startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
        }

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ContactUsViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            ivAvatar.setOnClickListener{
                onBackPressed()
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
