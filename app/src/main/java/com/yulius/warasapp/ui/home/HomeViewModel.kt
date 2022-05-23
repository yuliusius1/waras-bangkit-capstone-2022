package com.yulius.warasapp.ui.home

import androidx.lifecycle.*
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: UserPreference) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}