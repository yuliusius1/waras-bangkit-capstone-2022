package com.yulius.warasapp.ui.auth.reset_password

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
import com.yulius.warasapp.databinding.ActivityResetPasswordBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.auth.verification_data.VerificationDataActivity
import com.yulius.warasapp.util.ResponseCallback
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var viewModel : ResetPasswordViewModel
    private lateinit var userData: UserLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
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
        )[ResetPasswordViewModel::class.java]
    }

    private fun setupAction() {
        showLoading()
        binding.apply {
            btnSubmit.setOnClickListener {
                var isError = false
                if (TextUtils.isEmpty(etPassword.text)){
                    isError = true
                    etPassword.error = getString(R.string.enter_new_pass)
                }

                if (TextUtils.isEmpty(etRepeatPassword.text)){
                    isError = true
                    etRepeatPassword.error = getString(R.string.enter_repeat_pass)
                }

                if(!isError){
                    viewModel.resetPassword(userData.username, etPassword.text.toString(), object : ResponseCallback{
                        override fun getCallback(msg: String, status: Boolean) {
                            showDialogs(msg,status)
                        }
                    })
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
                    val intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Oops")
                setMessage(msg)
                setNegativeButton(getString(R.string.repeat)) { dialog, _ ->
                    dialog.dismiss()
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