package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.domain.usecase.evento.GetEventoByIdUseCase
import com.ifba.meuifba.domain.usecase.evento.MarcarParticipacaoUseCase
import com.ifba.meuifba.domain.usecase.evento.DesmarcarParticipacaoUseCase
import com.ifba.meuifba.domain.usecase.evento.DeleteEventoUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventoDetailViewModel @Inject constructor(
    private val getEventoByIdUseCase: GetEventoByIdUseCase,
    private val marcarParticipacaoUseCase: MarcarParticipacaoUseCase,
    private val desmarcarParticipacaoUseCase: DesmarcarParticipacaoUseCase,
    private val deleteEventoUseCase: DeleteEventoUseCase,
    private val preferencesManager: PreferencesManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val eventoId: Long = savedStateHandle.get<Long>("eventoId") ?: 0L

    private val _uiState = MutableStateFlow<EventoDetailUiState>(EventoDetailUiState.Loading)
    val uiState: StateFlow<EventoDetailUiState> = _uiState.asStateFlow()

    private val usuarioId: Long
        get() = preferencesManager.userId

    init {
        loadEvento()
    }

    private fun loadEvento() {
        viewModelScope.launch {
            _uiState.value = EventoDetailUiState.Loading
            // Passa usuarioId para que isMarcado seja setado corretamente
            val evento = getEventoByIdUseCase(eventoId, usuarioId)
            _uiState.value = if (evento != null) {
                EventoDetailUiState.Success(evento)
            } else {
                EventoDetailUiState.Error("Evento não encontrado")
            }
        }
    }

    fun toggleMarcacao() {
        val currentState = _uiState.value
        if (currentState !is EventoDetailUiState.Success) return

        viewModelScope.launch {
            val result = if (currentState.evento.isMarcado) {
                desmarcarParticipacaoUseCase(usuarioId, eventoId)
            } else {
                marcarParticipacaoUseCase(usuarioId, eventoId)
            }
            when (result) {
                is Resource.Success -> loadEvento()
                is Resource.Error -> { }
                is Resource.Loading -> { }
            }
        }
    }

    fun shareEvento() {
        // TODO: Implementar compartilhamento via Intent
    }

    fun deleteEvento() {
        viewModelScope.launch {
            _uiState.value = EventoDetailUiState.Loading
            when (val result = deleteEventoUseCase(eventoId)) {
                is Resource.Success -> _uiState.value = EventoDetailUiState.Deleted
                is Resource.Error -> _uiState.value = EventoDetailUiState.Error(
                    result.message ?: "Erro ao deletar evento"
                )
                is Resource.Loading -> { }
            }
        }
    }

    fun isCreator(): Boolean {
        val currentState = _uiState.value
        return if (currentState is EventoDetailUiState.Success) {
            currentState.evento.criador.id == usuarioId
        } else false
    }

    fun isAdmin(): Boolean = preferencesManager.isAdmin()

    fun canEditOrDelete(): Boolean = isCreator() || isAdmin()
}

sealed class EventoDetailUiState {
    object Loading : EventoDetailUiState()
    data class Success(val evento: EventoModel) : EventoDetailUiState()
    data class Error(val message: String) : EventoDetailUiState()
    object Deleted : EventoDetailUiState()
}