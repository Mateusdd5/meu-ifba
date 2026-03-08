package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.domain.usecase.evento.GetEventosUseCase
import com.ifba.meuifba.domain.usecase.evento.MarcarParticipacaoUseCase
import com.ifba.meuifba.domain.usecase.evento.DesmarcarParticipacaoUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventosUseCase: GetEventosUseCase,
    private val marcarParticipacaoUseCase: MarcarParticipacaoUseCase,
    private val desmarcarParticipacaoUseCase: DesmarcarParticipacaoUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoria = MutableStateFlow<Long?>(null)

    private val usuarioId: Long
        get() = preferencesManager.userId

    fun isAuthenticated(): Boolean = preferencesManager.isAuthenticated()
    fun isAdmin(): Boolean = preferencesManager.isAdmin()

    init {
        loadEventos()
    }

    fun loadEventos() {
        viewModelScope.launch {
            getEventosUseCase(usuarioId).collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> HomeUiState.Loading
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) HomeUiState.Empty
                        else HomeUiState.Success(result.data)
                    }
                    is Resource.Error -> HomeUiState.Error(
                        result.message ?: "Erro ao carregar eventos"
                    )
                }
            }
        }
    }

    fun searchEventos(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                loadEventos()
            } else {
                getEventosUseCase(usuarioId).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val filtrados = result.data?.filter {
                                it.titulo.contains(query, ignoreCase = true) ||
                                        it.descricao.contains(query, ignoreCase = true)
                            } ?: emptyList()
                            _uiState.value = if (filtrados.isEmpty()) HomeUiState.Empty
                            else HomeUiState.Success(filtrados)
                        }
                        is Resource.Error -> _uiState.value = HomeUiState.Error(
                            result.message ?: "Erro ao filtrar eventos"
                        )
                        else -> {}
                    }
                }
            }
        }
    }

    fun toggleMarcacao(eventoId: Long, isMarcado: Boolean) {
        viewModelScope.launch {
            val result = if (isMarcado) {
                desmarcarParticipacaoUseCase(usuarioId, eventoId)
            } else {
                marcarParticipacaoUseCase(usuarioId, eventoId)
            }
            when (result) {
                is Resource.Success -> loadEventos()
                is Resource.Error -> { }
                is Resource.Loading -> { }
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        loadEventos()
    }

    fun clearFilters() {
        _selectedCategoria.value = null
        _searchQuery.value = ""
        loadEventos()
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    object Empty : HomeUiState()
    data class Success(val eventos: List<EventoModel>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}