package com.yulius.warasapp.ui.profile.feedback

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
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivityFeedbackBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.util.ResponseCallback
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class FeedbackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackBinding
    private lateinit var viewModel: FeedbackViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = User("","","","","","","",false,"","",0)

        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupView() {
        showLoading()
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[FeedbackViewModel::class.java]

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
            ivAvatar.setOnClickListener {
                onBackPressed()
            }

            btnSubmit.setOnClickListener {
                val feedback = binding.edtSaran.editText?.text.toString()
                val rating = binding.ratingBar.rating.toDouble()
                viewModel.sendFeedback(feedback, user.id, rating, object: ResponseCallback{
                    override fun getCallback(msg: String, status: Boolean) {
                        showDialogs(msg,status)
                    }
                })
            }
        }
    }

    private fun showDialogs(msg: String, status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                setMessage(msg)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(this@FeedbackActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
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
