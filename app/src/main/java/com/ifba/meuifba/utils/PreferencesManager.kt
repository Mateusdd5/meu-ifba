package com.ifba.meuifba.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREFS_NAME,
        Context.MODE_PRIVATE
    )

    // User ID
    var userId: Long
        get() = prefs.getLong(Constants.KEY_USER_ID, -1L)
        set(value) = prefs.edit().putLong(Constants.KEY_USER_ID, value).apply()

    // User Token
    var userToken: String?
        get() = prefs.getString(Constants.KEY_USER_TOKEN, null)
        set(value) = prefs.edit().putString(Constants.KEY_USER_TOKEN, value).apply()

    // User Type
    var userType: String
        get() = prefs.getString(Constants.KEY_USER_TYPE, Constants.TIPO_USUARIO) ?: Constants.TIPO_USUARIO
        set(value) = prefs.edit().putString(Constants.KEY_USER_TYPE, value).apply()

    // Is Logged In
    var isLoggedIn: Boolean
        get() = prefs.getBoolean(Constants.KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(Constants.KEY_IS_LOGGED_IN, value).apply()

    // Remember Me
    var rememberMe: Boolean
        get() = prefs.getBoolean(Constants.KEY_REMEMBER_ME, false)
        set(value) = prefs.edit().putBoolean(Constants.KEY_REMEMBER_ME, value).apply()

    // Salvar dados de login
    fun saveLoginData(
        userId: Long,
        token: String,
        userType: String = Constants.TIPO_USUARIO,
        rememberMe: Boolean = false
    ) {
        this.userId = userId
        this.userToken = token
        this.userType = userType
        this.isLoggedIn = true
        this.rememberMe = rememberMe
    }

    // Limpar dados de login (logout)
    fun clearLoginData() {
        prefs.edit().apply {
            remove(Constants.KEY_USER_ID)
            remove(Constants.KEY_USER_TOKEN)
            remove(Constants.KEY_USER_TYPE)
            remove(Constants.KEY_IS_LOGGED_IN)
        }.apply()
    }

    // Limpar tudo
    fun clearAll() {
        prefs.edit().clear().apply()
    }

    // Verificar se usuário está autenticado (tem conta e token)
    fun isAuthenticated(): Boolean {
        return isLoggedIn && userId != -1L && !userToken.isNullOrBlank()
    }

    // Verificações de tipo
    fun isAdmin(): Boolean = userType == Constants.TIPO_ADMIN
}