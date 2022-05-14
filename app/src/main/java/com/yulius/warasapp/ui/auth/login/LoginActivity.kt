package com.yulius.warasapp.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityLoginBinding
import com.yulius.warasapp.databinding.ActivityMainBinding
import com.yulius.warasapp.ui.auth.register.RegisterActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        MainActivity::class.java
                    )
                )
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogo, View.ROTATION, 360f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleTextView = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA,1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA,1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA,1f).setDuration(500)
        val loginBtn = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA,1f).setDuration(500)
        val tvTxtRegist = ObjectAnimator.ofFloat(binding.tvTxtRegister, View.ALPHA,1f).setDuration(500)
        val tvRegist = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA,1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(tvTxtRegist,tvRegist)
        }

        AnimatorSet().apply {
            playSequentially(titleTextView,emailEditTextLayout,passwordEditTextLayout,loginBtn,together)
            start()
        }
    }

    private fun setupAction() {
        binding.apply {
            showLoading()
            btnLogin.setOnClickListener {
                var isError = false

                if(TextUtils.isEmpty(etEmail.text)){
                    isError = true
                    etEmail.error = getString(R.string.validate_email)
                }
                if(TextUtils.isEmpty(etPassword.text)){
                    isError = true
                    etPassword.error = getString(R.string.validate_password)
                } else if(etPassword.text?.length!! < 6){
                    isError = true
                    etPassword.error = getString(R.string.validate_password)
                }
                if(!isError){
                    viewModel.saveUser(User(
                        "Yulius",
                        "yuliusius",
                        "yiyus49@gmail.com",
                        "1234",
                        true
                    ))

                    showDialogs(true)
                }
            }
            tvRegister.setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        RegisterActivity::class.java
                    )
                )
            }
        }
    }

    private fun showDialogs(status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                val message = getString(R.string.login_success)
                setMessage(message)
                setPositiveButton(getString(R.string.next)) { _, _ ->
//                    finish()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Oops")
                val message = getString(R.string.login_error)
                setMessage(message)
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