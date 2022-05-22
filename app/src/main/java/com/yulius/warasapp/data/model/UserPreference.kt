package com.yulius.warasapp.data.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[NAME_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
                preferences[TEL_KEY] ?: "",
                preferences[BIRTH_KEY] ?: "",
                preferences[LOGIN_STATUS] ?: false,
                preferences[CREATED_AT] ?: "",
                preferences[UPDATED_AT] ?: "",
                preferences[ID_KEY] ?: 0
            )
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.full_name
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
            preferences[TEL_KEY] = user.telephone
            preferences[BIRTH_KEY] = user.date_of_birth
            preferences[LOGIN_STATUS] = user.isLogin
            preferences[UPDATED_AT] = user.updated_at
            preferences[CREATED_AT] = user.created_at
            preferences[ID_KEY] = user.id
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATUS] = true
        }
    }

    fun getBoardingPage(): Flow<Boolean>{
        return dataStore.data.map { preferences ->
            preferences[BOARDING_KEY] ?: true
        }
    }

    suspend fun saveBoardingPage(setData : Boolean ){
        dataStore.edit { preferences ->
            preferences[BOARDING_KEY] = setData

        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[USERNAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[PASSWORD_KEY] = ""
            preferences[TEL_KEY] = ""
            preferences[BIRTH_KEY] = ""
            preferences[CREATED_AT] = ""
            preferences[UPDATED_AT] = ""
            preferences[ID_KEY] = 0
            preferences[LOGIN_STATUS] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val TEL_KEY = stringPreferencesKey("tel")
        private val CREATED_AT = stringPreferencesKey("ca")
        private val UPDATED_AT = stringPreferencesKey("ua")
        private val ID_KEY = intPreferencesKey("id")
        private val BIRTH_KEY = stringPreferencesKey("birth")
        private val LOGIN_STATUS =
            booleanPreferencesKey("login_status")
        private val EMAIL_KEY = stringPreferencesKey("email")

        private val THEME_KEY = booleanPreferencesKey("theme_setting")
        private val BOARDING_KEY = booleanPreferencesKey("boarding_page")


        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}