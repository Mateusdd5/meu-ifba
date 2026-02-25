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

    // Estado da UI
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Campos do formulário
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _senha = MutableStateFlow("")
    val senha: StateFlow<String> = _senha.asStateFlow()

    // Funções para atualizar campos
    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onSenhaChange(newSenha: String) {
        _senha.value = newSenha
    }

    // Função principal de login
    fun login() {
        // Validações
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

        // Chamar API
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            when (val result = loginUseCase(_email.value, _senha.value)) {
                is Resource.Success -> {
                    result.data?.let { usuario ->
                        // Salvar dados do usuário
                        // TODO: Quando API retornar token, usar: token = result.token
                        preferencesManager.saveLoginData(
                            userId = usuario.id,
                            token = "temp_token_${usuario.id}", // Token temporário até API estar pronta
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
                is Resource.Loading -> {
                    // Já está em loading
                }
            }
        }
    }

    // Validação de email
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Limpar erro
    fun clearError() {
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }

    // Helper para pegar userId (usado nas telas)
    fun getUserId(): Long = preferencesManager.userId
}

// Estados possíveis da tela de Login
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val usuario: UsuarioModel) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}