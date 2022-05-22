package com.yulius.warasapp.ui.auth.verification_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference

class VerificationDataViewModel(private val pref: UserPreference): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val user = MutableLiveData<User>()
}