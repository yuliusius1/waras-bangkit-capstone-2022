package com.yulius.warasapp.ui.profile.setting

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.data.model.Reminder
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.ActivitySettingBinding
import com.yulius.warasapp.ui.profile.setting.preference.ReminderPreference
import com.yulius.warasapp.ui.profile.setting.receiver.AlarmReceiver
import com.yulius.warasapp.util.ViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: ThemeViewModel
    
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        
        notification()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ThemeViewModel::class.java]

        viewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
    }

    private fun setupAction() {
        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }

        binding.ivAvatar.setOnClickListener {
            onBackPressed()
        }
    }
    
    fun notification(){
        val reminderPreference = ReminderPreference(this)
        if (reminderPreference.getReminder().isReminded){
            binding.switchNotif.isChecked = true
        }else {
            binding.switchNotif.isChecked = false
        }

        alarmReceiver = AlarmReceiver()
        binding.switchNotif.setOnCheckedChangeListener{ buttonView, ischecked ->
            if (ischecked){
                saveReminder(true)
//                alarmReceiver.setRepeatingAlarm(this, "RepeatingAlarm", "08:00", "Waras reminder")
//                alarmReceiver.setOneTimeAlarm(this, "OneTimeAlarm","2022/05/29" ,"19:07", "Waras reminder One")
            } else{
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(b: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()

        reminder.isReminded = b
        reminderPreference.setReminder(reminder)
    }
}
