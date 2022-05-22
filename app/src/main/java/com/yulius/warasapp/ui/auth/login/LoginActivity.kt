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
import com.yulius.warasapp.ui.auth.check_email.CheckEmailActivity
import com.yulius.warasapp.ui.auth.register.RegisterActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ResponseCallback
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

        val titleTextView = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA,1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.etUsername, View.ALPHA,1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA,1f).setDuration(300)
        val loginBtn = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA,1f).setDuration(300)
        val forgotPass = ObjectAnimator.ofFloat(binding.tvForgotPass, View.ALPHA,1f).setDuration(300)
        val tvTxtRegist = ObjectAnimator.ofFloat(binding.tvTxtRegister, View.ALPHA,1f).setDuration(300)
        val tvRegist = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA,1f).setDuration(300)

        val together = AnimatorSet().apply {
            playTogether(tvTxtRegist,tvRegist)
        }

        AnimatorSet().apply {
            playSequentially(titleTextView,emailEditTextLayout,passwordEditTextLayout,forgotPass,loginBtn,together)
            start()
        }
    }

    private fun setupAction() {
        val checkSpaces = "\\A\\w{1,20}\\z"
        showLoading()

        binding.apply {
            tvForgotPass.setOnClickListener {
                startActivity(Intent(this@LoginActivity, CheckEmailActivity::class.java))
            }

            btnLogin.setOnClickListener {
                var isError = false

                if(!etUsername.text.toString().matches(checkSpaces.toRegex())){
                    isError = true
                    etUsername.error = getString(R.string.no_whitespace)
                }

                if(TextUtils.isEmpty(etUsername.text)){
                    isError = true
                    etUsername.error = getString(R.string.enter_username)
                }
                if(TextUtils.isEmpty(etPassword.text)){
                    isError = true
                    etPassword.error = getString(R.string.validate_password)
                } else if(etPassword.text?.length!! < 6){
                    isError = true
                    etPassword.error = getString(R.string.validate_password)
                }
                if(!isError){
                    viewModel.loginUser(etUsername.text.toString(), etPassword.text.toString(), object:
                        ResponseCallback {
                        override fun getCallback(msg: String, status: Boolean) {
                            showDialogs(msg,status)
                        }
                    })
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

    private fun showDialogs(msg: String, status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                setMessage(msg)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
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