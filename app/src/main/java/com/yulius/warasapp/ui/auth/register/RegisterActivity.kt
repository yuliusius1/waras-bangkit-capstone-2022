package com.yulius.warasapp.ui.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityRegisterBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ViewModelFactory
import androidx.core.util.Pair
import com.yulius.warasapp.data.model.ResponseData
import com.yulius.warasapp.data.model.UserRegister
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.ui.auth.register2.RegisterActivity2
import com.yulius.warasapp.util.ResponseCallback
import com.yulius.warasapp.util.getAges
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var userRegister: UserRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRegister = UserRegister("","","","","","","")

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.ivLogo, View.ROTATION, 360f).apply {
//            duration = 5000 // In Miles 1000ms = 1s
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()

        val titleTextView = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA,1f).setDuration(300)
        val subTitleTextView = ObjectAnimator.ofFloat(binding.tvSubTitle, View.ALPHA,1f).setDuration(300)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.etName, View.ALPHA,1f).setDuration(300)
        val usernameEditTextLayout = ObjectAnimator.ofFloat(binding.etUsername, View.ALPHA,1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA,1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA,1f).setDuration(300)
        val registBtn = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA,1f).setDuration(300)
        val tvTxtLogin = ObjectAnimator.ofFloat(binding.tvTxtLogin, View.ALPHA,1f).setDuration(300)
        val tvLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA,1f).setDuration(300)

        val together = AnimatorSet().apply {
            playTogether(tvTxtLogin,tvLogin)
        }

        AnimatorSet().apply {
            playSequentially(titleTextView,subTitleTextView,nameEditTextLayout,usernameEditTextLayout,emailEditTextLayout,passwordEditTextLayout,registBtn,together)
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
            val username = binding.etUsername.text.toString()
            val checkSpaces = "\\A\\w{1,20}\\z"
            when {
                !username.matches(checkSpaces.toRegex()) ->{
                    binding.etUsername.error = getString(R.string.no_whitespace)
                }

                username.isEmpty() -> {
                    binding.etUsername.error = getString(R.string.enter_username)
                }

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
                    userRegister = UserRegister(name,username,email,password,"","","")

                    viewModel.checkUsername(username, object : ResponseCallback {
                        override fun getCallback(msg: String, status: Boolean) {
                            if(status){
                                binding.etUsername.error = getString(R.string.user_found_error)
                                showDialogError(getString(R.string.user_found_error),status)
                            } else {
                                viewModel.checkEmail(email, object : ResponseCallback {
                                    override fun getCallback(msg: String, status: Boolean) {
                                        if(status){
                                            binding.etEmail.error = getString(R.string.email_found_error)
                                            showDialogError(getString(R.string.email_found_error),status)
                                        } else {
                                            showDialogError("",status)
                                        }
                                    }
                                })
                            }
                        }
                    })
                }
            }
        }
    }

    fun showDialogError(msg :String, status:Boolean){
        if(!status){
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.success))
                setMessage(getString(R.string.registration_success))
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(this@RegisterActivity,RegisterActivity2::class.java)
                    intent.putExtra("userRegister",userRegister)
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@RegisterActivity,
                        Pair(binding.ivLogo, "logo"),
                        Pair(binding.tvTitle, "title"),
                        Pair(binding.tvLogin, "login"),
                        Pair(binding.tvTxtLogin, "txtLogin"),
                    )
                    startActivity(intent, optionsCompat.toBundle())
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.sorry))
                setMessage(msg)
                setPositiveButton(getString(R.string.repeat)) { dialog, _ ->
                    dialog.dismiss()
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