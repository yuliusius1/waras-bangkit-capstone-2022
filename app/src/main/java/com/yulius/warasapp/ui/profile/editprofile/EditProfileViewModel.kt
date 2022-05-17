package com.yulius.warasapp.ui.profile.editprofile

import androidx.lifecycle.*
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import kotlinx.coroutines.launch

class EditProfileViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFound = MutableLiveData<Boolean>()
    val isFound: LiveData<Boolean> = _isFound

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun checkUser(username: String): LiveData<Boolean>{
        _isFound.value = false
        return isFound
    }

}