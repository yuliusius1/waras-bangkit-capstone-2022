package com.yulius.warasapp.ui.profile.contact_us

import androidx.lifecycle.*
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference

class ContactUsViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}