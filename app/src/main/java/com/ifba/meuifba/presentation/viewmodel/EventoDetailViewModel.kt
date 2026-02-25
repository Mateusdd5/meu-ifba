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

    // Pega o ID do evento da navegação
    private val eventoId: Long = savedStateHandle.get<Long>("eventoId") ?: 0L

    // Estado da UI
    private val _uiState = MutableStateFlow<EventoDetailUiState>(EventoDetailUiState.Loading)
    val uiState: StateFlow<EventoDetailUiState> = _uiState.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId  // ← CORRIGIDO

    init {
        loadEvento()
    }

    // Carregar detalhes do evento
    private fun loadEvento() {
        viewModelScope.launch {
            _uiState.value = EventoDetailUiState.Loading

            val evento = getEventoByIdUseCase(eventoId)

            _uiState.value = if (evento != null) {
                EventoDetailUiState.Success(evento)
            } else {
                EventoDetailUiState.Error("Evento não encontrado")
            }
        }
    }

    // Marcar/desmarcar participação
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
                is Resource.Success -> {
                    // Recarregar evento para atualizar estado
                    loadEvento()
                }
                is Resource.Error -> {
                    // TODO: Mostrar erro na UI (Snackbar/Toast)
                }
                is Resource.Loading -> {
                    // Ignorar
                }
            }
        }
    }

    // Compartilhar evento
    fun shareEvento() {
        val currentState = _uiState.value
        if (currentState is EventoDetailUiState.Success) {
            // TODO: Implementar compartilhamento via Intent quando tiver UI
            // Será algo tipo:
            // val shareIntent = Intent().apply {
            //     action = Intent.ACTION_SEND
            //     putExtra(Intent.EXTRA_TEXT, "Evento: ${currentState.evento.titulo}")
            //     type = "text/plain"
            // }
        }
    }

    // Deletar evento (apenas criador)
    fun deleteEvento() {
        viewModelScope.launch {
            _uiState.value = EventoDetailUiState.Loading

            when (val result = deleteEventoUseCase(eventoId)) {
                is Resource.Success -> {
                    _uiState.value = EventoDetailUiState.Deleted
                }
                is Resource.Error -> {
                    _uiState.value = EventoDetailUiState.Error(
                        result.message ?: "Erro ao deletar evento"
                    )
                }
                is Resource.Loading -> {
                    // Já está em loading
                }
            }
        }
    }

    // Verificar se usuário é criador
    fun isCreator(): Boolean {
        val currentState = _uiState.value
        return if (currentState is EventoDetailUiState.Success) {
            currentState.evento.criador.id == usuarioId
        } else {
            false
        }
    }
}

// Estados possíveis da tela de detalhes
sealed class EventoDetailUiState {
    object Loading : EventoDetailUiState()
    data class Success(val evento: EventoModel) : EventoDetailUiState()
    data class Error(val message: String) : EventoDetailUiState()
    object Deleted : EventoDetailUiState()
}