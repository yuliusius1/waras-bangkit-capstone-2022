package com.yulius.warasapp.ui.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityRegisterBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.auth.login.LoginViewModel
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogo, View.ROTATION, 360f).apply {
            duration = 5000 // In Miles 1000ms = 1s
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleTextView = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA,1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.etName, View.ALPHA,1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA,1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA,1f).setDuration(500)
        val registBtn = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA,1f).setDuration(500)
        val tvTxtLogin = ObjectAnimator.ofFloat(binding.tvTxtLogin, View.ALPHA,1f).setDuration(500)
        val tvLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA,1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(tvTxtLogin,tvLogin)
        }

        AnimatorSet().apply {
            playSequentially(titleTextView,nameEditTextLayout,emailEditTextLayout,passwordEditTextLayout,registBtn,together)
            start()
        }
    }

    private fun setupAction() {
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etName.error = getString(R.string.enter_name)
                }
                email.isEmpty() -> {
                    binding.etEmail.error = getString(R.string.enter_email)
                }
                password.isEmpty() -> {
                    binding.etPassword.error = getString(R.string.enter_password)
                }
                else -> {
                    showDialogs("",true)
                }
            }
        }
    }

    private fun showDialogs(msg:String, status:Boolean) {
        if(status){
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.success))
                setMessage(getString(R.string.your_account_created))
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.sorry))
                val message = getString(R.string.create_account_failed,msg)
                setMessage(message)
                setPositiveButton(getString(R.string.repeat)) { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                startActivity(
                    Intent(
                        this@RegisterActivity,
                        MainActivity::class.java
                    )
                )
            }
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}