package com.yulius.warasapp.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.FragmentProfileBinding
import com.yulius.warasapp.ui.auth.login.LoginActivity
import com.yulius.warasapp.ui.main.MainActivity
import com.yulius.warasapp.ui.profile.change_password.ChangePasswordActivity
import com.yulius.warasapp.ui.profile.contact_us.ContactUsActivity
import com.yulius.warasapp.ui.profile.editprofile.EditProfileActivity
import com.yulius.warasapp.ui.profile.feedback.FeedbackActivity
import com.yulius.warasapp.ui.profile.history.HistoryActivity
import com.yulius.warasapp.ui.profile.setting.SettingActivity
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        binding.ivAvatar.setImageResource(R.drawable.avatar)
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(
                    UserPreference.getInstance(
                        requireContext().dataStore
                    )
                )
            )[ProfileViewModel::class.java]

        viewModel.getUser().observe(viewLifecycleOwner){
            if(it.isLogin){
                binding.tvName.text = it.name
                binding.tvBirth.text = it.birth
                binding.tvTelephone.text = it.telephone
            } else {
                val i = Intent(activity, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }

    private fun setupAction() {
        binding.btnEdit.setOnClickListener {
            startActivity(Intent(activity, EditProfileActivity::class.java))
        }

        binding.btnHistory.setOnClickListener {
            startActivity(Intent(activity, HistoryActivity::class.java))
        }

        binding.btnChangePassword.setOnClickListener {
            startActivity(Intent(activity, ChangePasswordActivity::class.java))
        }

        binding.btnTheme.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }

        binding.btnLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.btnFeedback.setOnClickListener {
            startActivity(Intent(activity, FeedbackActivity::class.java))
        }

        binding.btnContact.setOnClickListener {
            startActivity(Intent(activity, ContactUsActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val builder =
            AlertDialog.Builder(requireContext(), 0).create()
        val view =
            layoutInflater.inflate(R.layout.dialog_logout, null)
        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

        builder.setView(view)

        btnConfirm.setOnClickListener {
            viewModel.logout()
            val i = Intent(activity, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
        btnCancel.setOnClickListener {
            builder.dismiss()
        }

        builder.show()
    }

    companion object {

    }
}