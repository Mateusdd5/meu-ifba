package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.domain.usecase.usuario.GetUsuarioByIdUseCase
import com.ifba.meuifba.domain.usecase.usuario.LogoutUseCase
import com.ifba.meuifba.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUsuarioByIdUseCase: GetUsuarioByIdUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // Estado da UI
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId  // ← CORRIGIDO

    init {
        loadProfile()
    }

    // Carregar perfil
    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            val usuario = getUsuarioByIdUseCase(usuarioId)

            _uiState.value = if (usuario != null) {
                ProfileUiState.Success(usuario)
            } else {
                ProfileUiState.Error("Erro ao carregar perfil")
            }
        }
    }

    // Fazer logout
    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = ProfileUiState.LoggedOut
        }
    }

    // Recarregar perfil (após edição)
    fun refresh() {
        loadProfile()
    }
}

// Estados possíveis do perfil
sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val usuario: UsuarioModel) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
    object LoggedOut : ProfileUiState()
}