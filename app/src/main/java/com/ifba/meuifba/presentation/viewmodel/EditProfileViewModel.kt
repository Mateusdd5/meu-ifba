package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.CursoModel
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.domain.usecase.usuario.GetUsuarioByIdUseCase
import com.ifba.meuifba.domain.usecase.usuario.UpdateUsuarioUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUsuarioByIdUseCase: GetUsuarioByIdUseCase,
    private val updateUsuarioUseCase: UpdateUsuarioUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // Estado da UI
    private val _uiState = MutableStateFlow<EditProfileUiState>(EditProfileUiState.Loading)
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    // Campos do formulário
    private val _nome = MutableStateFlow("")
    val nome: StateFlow<String> = _nome.asStateFlow()

    private val _cursoSelecionado = MutableStateFlow<CursoModel?>(null)
    val cursoSelecionado: StateFlow<CursoModel?> = _cursoSelecionado.asStateFlow()

    // Lista de cursos disponíveis
    private val _cursosDisponiveis = MutableStateFlow<List<CursoModel>>(emptyList())
    val cursosDisponiveis: StateFlow<List<CursoModel>> = _cursosDisponiveis.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId

    init {
        loadProfile()
    }

    // Carregar dados atuais do perfil
    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = EditProfileUiState.Loading

            val usuario = getUsuarioByIdUseCase(usuarioId)

            if (usuario != null) {
                _nome.value = usuario.nome
                _cursoSelecionado.value = usuario.curso
                _uiState.value = EditProfileUiState.Idle
            } else {
                _uiState.value = EditProfileUiState.Error("Erro ao carregar perfil")
            }
        }
    }

    // Funções para atualizar campos
    fun onNomeChange(newNome: String) {
        _nome.value = newNome
    }

    fun onCursoSelected(curso: CursoModel) {
        _cursoSelecionado.value = curso
    }

    // Salvar alterações
    fun saveProfile() {
        // Validações
        if (_nome.value.isBlank()) {
            _uiState.value = EditProfileUiState.Error("Nome não pode estar vazio")
            return
        }

        if (_cursoSelecionado.value == null) {
            _uiState.value = EditProfileUiState.Error("Selecione um curso")
            return
        }

        viewModelScope.launch {
            _uiState.value = EditProfileUiState.Loading

            when (val result = updateUsuarioUseCase(
                usuarioId = usuarioId,
                nome = _nome.value,
                cursoId = _cursoSelecionado.value!!.id
            )) {
                is Resource.Success -> {
                    result.data?.let { usuario ->
                        _uiState.value = EditProfileUiState.Success(usuario)
                    } ?: run {
                        _uiState.value = EditProfileUiState.Error("Erro ao atualizar perfil")
                    }
                }
                is Resource.Error -> {
                    _uiState.value = EditProfileUiState.Error(
                        result.message ?: "Erro ao atualizar perfil"
                    )
                }
                is Resource.Loading -> {
                    // Já está em loading
                }
            }
        }
    }

    // Limpar erro
    fun clearError() {
        if (_uiState.value is EditProfileUiState.Error) {
            _uiState.value = EditProfileUiState.Idle
        }
    }

    // Carregar cursos disponíveis
    fun loadCursos() {
        // TODO: Implementar quando API estiver pronta
        _cursosDisponiveis.value = emptyList()
    }
}

// Estados possíveis da edição de perfil
sealed class EditProfileUiState {
    object Idle : EditProfileUiState()
    object Loading : EditProfileUiState()
    data class Success(val usuario: UsuarioModel) : EditProfileUiState()
    data class Error(val message: String) : EditProfileUiState()
}