package com.yulius.warasapp.ui.profile.history.detail_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference

class DetailHistoryViewModel (private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}