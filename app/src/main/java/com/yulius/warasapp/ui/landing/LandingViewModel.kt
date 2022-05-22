package com.yulius.warasapp.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yulius.warasapp.data.model.UserPreference
import kotlinx.coroutines.launch

class LandingViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveBoardingPage(data: Boolean) {
        viewModelScope.launch {
            pref.saveBoardingPage(data)
        }
    }
}