package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.domain.usecase.evento.GetEventosMarcadosUseCase
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
class MeusEventosViewModel @Inject constructor(
    private val getEventosMarcadosUseCase: GetEventosMarcadosUseCase,
    private val desmarcarParticipacaoUseCase: DesmarcarParticipacaoUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // Estado da UI
    private val _uiState = MutableStateFlow<MeusEventosUiState>(MeusEventosUiState.Loading)
    val uiState: StateFlow<MeusEventosUiState> = _uiState.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId

    init {
        loadEventosMarcados()
    }

    // Carregar eventos marcados
    fun loadEventosMarcados() {
        viewModelScope.launch {
            getEventosMarcadosUseCase(usuarioId).collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> MeusEventosUiState.Loading
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            MeusEventosUiState.Empty
                        } else {
                            MeusEventosUiState.Success(result.data)
                        }
                    }
                    is Resource.Error -> MeusEventosUiState.Error(
                        result.message ?: "Erro ao carregar eventos"
                    )
                }
            }
        }
    }

    // Desmarcar evento
    fun desmarcarEvento(eventoId: Long) {
        viewModelScope.launch {
            when (desmarcarParticipacaoUseCase(usuarioId, eventoId)) {
                is Resource.Success -> {
                    // Recarregar lista
                    loadEventosMarcados()
                }
                is Resource.Error -> {
                    // TODO: Mostrar erro na UI
                }
                is Resource.Loading -> {
                    // Ignorar
                }
            }
        }
    }

    // Recarregar
    fun refresh() {
        loadEventosMarcados()
    }
}

// Estados possíveis
sealed class MeusEventosUiState {
    object Loading : MeusEventosUiState()
    object Empty : MeusEventosUiState()
    data class Success(val eventos: List<EventoModel>) : MeusEventosUiState()
    data class Error(val message: String) : MeusEventosUiState()
}