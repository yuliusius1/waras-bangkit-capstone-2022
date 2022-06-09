package com.yulius.warasapp.ui.profile.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: UserPreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}