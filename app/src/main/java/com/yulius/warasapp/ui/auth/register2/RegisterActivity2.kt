package com.yulius.warasapp.ui.auth.register2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.model.UserRegister
import com.yulius.warasapp.databinding.ActivityRegister2Binding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.auth.register.RegisterActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ResponseCallback
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class RegisterActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityRegister2Binding
    private lateinit var viewModel: RegisterViewModel2
    private lateinit var userRegister: UserRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        userRegister = intent.getParcelableExtra<UserRegister>("userRegister") as UserRegister

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
        )[RegisterViewModel2::class.java]

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                startActivity(
                    Intent(
                        this@RegisterActivity2,
                        MainActivity::class.java
                    )
                )
            }
        }
    }

    private fun setupAction() {
        showLoading()
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnPrevious.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val tel = binding.etTel.text.toString()
            val gender = binding.gender.selectedItem.toString()

            val dateOfBirth = binding.datePicker.year.toString() + "-" + (binding.datePicker.month + 1).toString() + "-" + binding.datePicker.dayOfMonth.toString()

            binding.apply {
                if(tel.isEmpty()) {
                    etTel.error = getString(R.string.enter_tel)
                } else {
                    userRegister.telephone = tel
                    userRegister.gender = gender
                    userRegister.date_of_birth = dateOfBirth
                    viewModel.saveUser(userRegister,object: ResponseCallback {
                        override fun getCallback(msg: String, status: Boolean) {
                            showDialogs(msg,status)
                        }
                    })
                }
            }
        }
    }

    private fun showDialogs(msg:String, status:Boolean) {
        if(status){
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.success))
                setMessage(msg)
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