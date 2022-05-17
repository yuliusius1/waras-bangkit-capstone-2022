package com.yulius.warasapp.ui.profile.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference

class FeedbackViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}