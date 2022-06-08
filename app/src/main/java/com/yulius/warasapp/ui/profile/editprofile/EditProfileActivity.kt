package com.yulius.warasapp.ui.profile.editprofile

import android.app.DatePickerDialog
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
import com.yulius.warasapp.databinding.ActivityEditProfileBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ResponseCallback
import com.yulius.warasapp.util.ViewModelFactory
import com.yulius.warasapp.util.getAges
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    var calender: Calendar = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var viewModel : EditProfileViewModel
    private lateinit var user : User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupAction() {
        showLoading()
        binding.apply {
            ivAvatar.setOnClickListener{
                onBackPressed()
            }
            dateEditText.setOnClickListener {
                DatePickerDialog(
                    this@EditProfileActivity,
                    dateSetListener,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            saveButton.setOnClickListener {
                var isError = false

                if (TextUtils.isEmpty(nameEditTextLayout.editText?.text)){
                    isError = true
                    nameEditTextLayout.editText?.error = getString(R.string.enter_name)
                }

                if (TextUtils.isEmpty(emailEditTextLayout.editText?.text)){
                    isError = true
                    emailEditTextLayout.editText?.error = getString(R.string.enter_email)
                }

                if (TextUtils.isEmpty(dateEditTextLayout.editText?.text)){
                    isError = true
                    dateEditTextLayout.editText?.error = getString(R.string.enter_date)
                }

                if(getAges(dateEditTextLayout.editText?.text.toString()) < 0){
                    isError = true
                    dateEditTextLayout.error = getString(R.string.wrong_date_of_birth)
                }

                if (TextUtils.isEmpty(telpEditTextLayout.editText?.text)){
                    isError = true
                    telpEditTextLayout.editText?.error = getString(R.string.enter_tel)
                }

                if(emailEditTextLayout.editText?.text.toString() != user.email){
                    viewModel.checkEmail(emailEditTextLayout.editText?.text.toString(), object : ResponseCallback{
                        override fun getCallback(msg: String, status: Boolean) {
                            if(!status){
                                isError = true
                            }
                        }
                    })
                }

                if (!isError){
                    viewModel.updateUser(
                        user,
                        nameEditTextLayout.editText?.text.toString(),
                        emailEditTextLayout.editText?.text.toString(),
                        gender.selectedItem.toString(),
                        telpEditTextLayout.editText?.text.toString(),
                        dateEditTextLayout.editText?.text.toString(),
                        object :ResponseCallback {
                            override fun getCallback(msg: String, status: Boolean) {
                                showDialogs(msg,status)
                            }
                        }
                    )

                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[EditProfileViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                user = it
                binding.apply {
                    nameEditTextLayout.editText?.setText(it.full_name)
                    emailEditTextLayout.editText?.setText(it.email)
                    dateEditTextLayout.editText?.setText(it.date_of_birth.substring(0,10))
                    telpEditTextLayout.editText?.setText(it.telephone)
                    if(it.gender == "Female"){
                        gender.setSelection(1)
                    }
                }

                dateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        calender.set(Calendar.YEAR, year)
                        calender.set(Calendar.MONTH, monthOfYear)
                        calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateDateInView()
                    }
            }
        }
    }

    private fun setupView() {
        showLoading()
        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun updateDateInView() {
        val myFormat = getString(R.string.date_formate)
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dateEditText.setText(sdf.format(calender.time))
    }


    private fun showDialogs(msg:String,status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                setMessage(msg)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    startActivity(Intent(this@EditProfileActivity, MainActivity::class.java))
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
