package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.domain.usecase.usuario.LoginUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _senha = MutableStateFlow("")
    val senha: StateFlow<String> = _senha.asStateFlow()

    fun onEmailChange(newEmail: String) { _email.value = newEmail }
    fun onSenhaChange(newSenha: String) { _senha.value = newSenha }

    fun login() {
        if (_email.value.isBlank()) {
            _uiState.value = LoginUiState.Error("Email não pode estar vazio")
            return
        }
        if (_senha.value.isBlank()) {
            _uiState.value = LoginUiState.Error("Senha não pode estar vazia")
            return
        }
        if (!isEmailValid(_email.value)) {
            _uiState.value = LoginUiState.Error("Email inválido")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            when (val result = loginUseCase(_email.value, _senha.value)) {
                is Resource.Success -> {
                    result.data?.let { usuario ->
                        preferencesManager.saveLoginData(
                            userId = usuario.id,
                            token = preferencesManager.userToken ?: "",
                            rememberMe = false
                        )
                        _uiState.value = LoginUiState.Success(usuario)
                    } ?: run {
                        _uiState.value = LoginUiState.Error("Erro ao fazer login")
                    }
                }
                is Resource.Error -> {
                    _uiState.value = LoginUiState.Error(
                        result.message ?: "Erro ao fazer login"
                    )
                }
                is Resource.Loading -> {}
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun clearError() {
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }

    fun getUserId(): Long = preferencesManager.userId
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val usuario: UsuarioModel) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}