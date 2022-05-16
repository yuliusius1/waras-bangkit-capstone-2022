package com.yulius.warasapp.ui.profile.change_password

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityChangePasswordBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ChangePasswordViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                user = it
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            btnSubmit.setOnClickListener {
                Log.d("TAG", "setupAction: ${etCurrPass.text}, ${etNewPass.text}, ${etRepeatPass.text}")
                var isError = false

                if(TextUtils.isEmpty(etCurrPass.text)){
                    isError = true
                    etCurrPass.error = "Current Password must be filled"
                }
                if(TextUtils.isEmpty(etNewPass.text)){
                    isError = true
                    etCurrPass.error = "New Password must be filled"
                }
                if(TextUtils.isEmpty(etNewPass.text)){
                    isError = true
                    etCurrPass.error = "Repeat New Password must be filled"
                }

                if(etCurrPass.text.toString() != user.password){
                    isError = true
                    etCurrPass.error = "Wrong Password"
                }

                if(etCurrPass.text.toString() == etNewPass.text.toString()){
                    isError = true
                    etNewPass.error = "New Password Cannot be same as current password"
                } else if(etRepeatPass.text.toString() != etNewPass.text.toString()){
                    isError = true
                    etRepeatPass.error = "Repeat password must be equals to New Password"
                }


                if(!isError){
                    viewModel.getUser().observe(this@ChangePasswordActivity){
                        viewModel.saveUser(
                            User(
                                it.name,
                                it.username,
                                it.email,
                                etCurrPass.text.toString(),
                                it.telephone,
                                it.birth,
                                true
                            )
                        )
                    }

                    showDialogs(true)
                }
            }
        }
    }

    private fun showDialogs(status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                val message = "Change Password Success"
                setMessage(message)
                setPositiveButton(getString(R.string.next)) { _, _ ->
//                    finish()
                    startActivity(Intent(this@ChangePasswordActivity, MainActivity::class.java))
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

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
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