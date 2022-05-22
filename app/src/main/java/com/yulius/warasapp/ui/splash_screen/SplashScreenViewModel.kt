package com.yulius.warasapp.ui.splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.UserPreference

class SplashScreenViewModel(private val pref: UserPreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun getBoardingPage(): LiveData<Boolean> {
        return pref.getBoardingPage().asLiveData()
    }
}