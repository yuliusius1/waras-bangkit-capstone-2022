package com.yulius.warasapp.ui.auth.verification_data

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserLogin
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityVerificationDataBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.auth.reset_password.ResetPasswordActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class VerificationDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationDataBinding
    private lateinit var viewModel : VerificationDataViewModel
    private lateinit var userData: UserLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.getParcelableExtra<UserLogin>("userData") as UserLogin

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
        )[VerificationDataViewModel::class.java]
    }

    private fun setupAction() {
        showLoading()
        binding.apply {
            btnSubmit.setOnClickListener {
                val dateOfBirth = binding.datePicker.year.toString() + "-" + (binding.datePicker.month + 1).toString() + "-" + binding.datePicker.dayOfMonth.toString()

                if(TextUtils.isEmpty(etTel.text)){
                    etTel.error = getString(R.string.enter_tel)
                } else {
                    if(etTel.text.toString() == userData.telephone || dateOfBirth == userData.date_of_birth.substring(0,10) ){
                        showDialogs(getString(R.string.verification_success),true)
                    } else {
                        showDialogs(getString(R.string.verification_error),false)
                    }
                }
            }
        }
    }

    private fun showDialogs(msg: String, status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                setMessage(msg)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(this@VerificationDataActivity, ResetPasswordActivity::class.java)
                    intent.putExtra("userData", userData)
                    startActivity(intent)
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Oops")
                setMessage(msg)
                setNegativeButton(getString(R.string.repeat)) { _, _ ->
                    val intent = Intent(this@VerificationDataActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                create()
                show()
            }
        }
    }

    private fun showLoading() {
        viewModel.isLoading.observe(this) {
            binding.apply {
                when {
                    it -> progressBar.visibility = View.VISIBLE
                    else -> progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}