package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.CategoriaModel
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.domain.usecase.evento.GetEventosUseCase
import com.ifba.meuifba.domain.usecase.evento.GetEventosPorCategoriaUseCase
import com.ifba.meuifba.domain.usecase.evento.SearchEventosUseCase
import com.ifba.meuifba.domain.usecase.evento.MarcarParticipacaoUseCase
import com.ifba.meuifba.domain.usecase.evento.DesmarcarParticipacaoUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventosUseCase: GetEventosUseCase,
    private val getEventosPorCategoriaUseCase: GetEventosPorCategoriaUseCase,
    private val searchEventosUseCase: SearchEventosUseCase,
    private val marcarParticipacaoUseCase: MarcarParticipacaoUseCase,
    private val desmarcarParticipacaoUseCase: DesmarcarParticipacaoUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // Estado da UI
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Busca
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Filtro por categoria
    private val _selectedCategoria = MutableStateFlow<CategoriaModel?>(null)
    val selectedCategoria: StateFlow<CategoriaModel?> = _selectedCategoria.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId  // ← CORRIGIDO

    init {
        loadEventos()
    }

    // Carregar eventos
    fun loadEventos() {
        viewModelScope.launch {
            getEventosUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> HomeUiState.Loading
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            HomeUiState.Empty
                        } else {
                            HomeUiState.Success(result.data)
                        }
                    }
                    is Resource.Error -> HomeUiState.Error(
                        result.message ?: "Erro ao carregar eventos"
                    )
                }
            }
        }
    }

    // Buscar eventos
    fun searchEventos(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            loadEventos()
            return
        }

        viewModelScope.launch {
            searchEventosUseCase(query).collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> HomeUiState.Loading
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            HomeUiState.Empty
                        } else {
                            HomeUiState.Success(result.data)
                        }
                    }
                    is Resource.Error -> HomeUiState.Error(
                        result.message ?: "Erro ao buscar eventos"
                    )
                }
            }
        }
    }

    // Filtrar por categoria
    fun filterByCategoria(categoria: CategoriaModel?) {
        _selectedCategoria.value = categoria

        if (categoria == null) {
            loadEventos()
            return
        }

        viewModelScope.launch {
            getEventosPorCategoriaUseCase(categoria.id).collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> HomeUiState.Loading
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            HomeUiState.Empty
                        } else {
                            HomeUiState.Success(result.data)
                        }
                    }
                    is Resource.Error -> HomeUiState.Error(
                        result.message ?: "Erro ao filtrar eventos"
                    )
                }
            }
        }
    }

    // Marcar interesse em evento
    fun toggleMarcacao(eventoId: Long, isMarcado: Boolean) {
        viewModelScope.launch {
            val result = if (isMarcado) {
                desmarcarParticipacaoUseCase(usuarioId, eventoId)
            } else {
                marcarParticipacaoUseCase(usuarioId, eventoId)
            }

            when (result) {
                is Resource.Success -> {
                    // Atualizar lista
                    loadEventos()
                }
                is Resource.Error -> {
                    // Mostrar erro (toast ou snackbar na UI)
                }
                is Resource.Loading -> {
                    // Ignorar
                }
            }
        }
    }

    // Limpar busca
    fun clearSearch() {
        _searchQuery.value = ""
        loadEventos()
    }

    // Limpar filtros
    fun clearFilters() {
        _selectedCategoria.value = null
        _searchQuery.value = ""
        loadEventos()
    }
}

// Estados possíveis da Home
sealed class HomeUiState {
    object Loading : HomeUiState()
    object Empty : HomeUiState()
    data class Success(val eventos: List<EventoModel>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}