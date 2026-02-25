package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.data.repository.UsuarioRepository
import com.ifba.meuifba.domain.model.CursoModel
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.domain.usecase.usuario.RegisterUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val preferencesManager: PreferencesManager,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _nome = MutableStateFlow("")
    val nome: StateFlow<String> = _nome.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _senha = MutableStateFlow("")
    val senha: StateFlow<String> = _senha.asStateFlow()

    private val _confirmarSenha = MutableStateFlow("")
    val confirmarSenha: StateFlow<String> = _confirmarSenha.asStateFlow()

    private val _cursoSelecionado = MutableStateFlow<CursoModel?>(null)
    val cursoSelecionado: StateFlow<CursoModel?> = _cursoSelecionado.asStateFlow()

    private val _cursosDisponiveis = MutableStateFlow<List<CursoModel>>(emptyList())
    val cursosDisponiveis: StateFlow<List<CursoModel>> = _cursosDisponiveis.asStateFlow()

    fun onNomeChange(newNome: String) { _nome.value = newNome }
    fun onEmailChange(newEmail: String) { _email.value = newEmail }
    fun onSenhaChange(newSenha: String) { _senha.value = newSenha }
    fun onConfirmarSenhaChange(newConfirmarSenha: String) { _confirmarSenha.value = newConfirmarSenha }
    fun onCursoSelected(curso: CursoModel) { _cursoSelecionado.value = curso }

    fun register() {
        if (_nome.value.isBlank()) {
            _uiState.value = RegisterUiState.Error("Nome não pode estar vazio")
            return
        }
        if (_email.value.isBlank()) {
            _uiState.value = RegisterUiState.Error("Email não pode estar vazio")
            return
        }
        if (!isEmailValid(_email.value)) {
            _uiState.value = RegisterUiState.Error("Email inválido")
            return
        }
        if (_senha.value.isBlank()) {
            _uiState.value = RegisterUiState.Error("Senha não pode estar vazia")
            return
        }
        if (_senha.value.length < 6) {
            _uiState.value = RegisterUiState.Error("Senha deve ter no mínimo 6 caracteres")
            return
        }
        if (_senha.value != _confirmarSenha.value) {
            _uiState.value = RegisterUiState.Error("As senhas não coincidem")
            return
        }
        if (_cursoSelecionado.value == null) {
            _uiState.value = RegisterUiState.Error("Selecione um curso")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading

            when (val result = registerUseCase(
                nome = _nome.value,
                email = _email.value,
                senha = _senha.value,
                cursoId = _cursoSelecionado.value!!.id
            )) {
                is Resource.Success -> {
                    result.data?.let { usuario ->
                        preferencesManager.saveLoginData(
                            userId = usuario.id,
                            token = "temp_token_${usuario.id}",
                            rememberMe = false
                        )
                        _uiState.value = RegisterUiState.Success(usuario)
                    } ?: run {
                        _uiState.value = RegisterUiState.Error("Erro ao fazer cadastro")
                    }
                }
                is Resource.Error -> {
                    _uiState.value = RegisterUiState.Error(
                        result.message ?: "Erro ao fazer cadastro"
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
        if (_uiState.value is RegisterUiState.Error) {
            _uiState.value = RegisterUiState.Idle
        }
    }

    fun loadCursos() {
        viewModelScope.launch {
            when (val result = usuarioRepository.getCursos()) {
                is Resource.Success -> {
                    _cursosDisponiveis.value = result.data ?: emptyList()
                }
                is Resource.Error -> {
                    // Silencioso — o campo de curso ficará desabilitado
                }
                is Resource.Loading -> {}
            }
        }
    }
}

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    data class Success(val usuario: UsuarioModel) : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}