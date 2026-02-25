package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.NotificacaoModel
import com.ifba.meuifba.domain.usecase.notificacao.GetNotificacoesUseCase
import com.ifba.meuifba.domain.usecase.notificacao.GetCountNotificacoesNaoLidasUseCase
import com.ifba.meuifba.domain.usecase.notificacao.MarcarNotificacaoLidaUseCase
import com.ifba.meuifba.domain.usecase.notificacao.MarcarTodasNotificacoesLidasUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificacoesViewModel @Inject constructor(
    private val getNotificacoesUseCase: GetNotificacoesUseCase,
    private val getCountNaoLidasUseCase: GetCountNotificacoesNaoLidasUseCase,
    private val marcarNotificacaoLidaUseCase: MarcarNotificacaoLidaUseCase,
    private val marcarTodasLidasUseCase: MarcarTodasNotificacoesLidasUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // Estado da UI
    private val _uiState = MutableStateFlow<NotificacoesUiState>(NotificacoesUiState.Loading)
    val uiState: StateFlow<NotificacoesUiState> = _uiState.asStateFlow()

    // Contador de não lidas (para badge)
    private val _countNaoLidas = MutableStateFlow(0)
    val countNaoLidas: StateFlow<Int> = _countNaoLidas.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId  // ← CORRIGIDO

    init {
        loadNotificacoes()
        observeCountNaoLidas()
    }

    // Carregar notificações
    private fun loadNotificacoes() {
        viewModelScope.launch {
            getNotificacoesUseCase(usuarioId).collect { result ->
                _uiState.value = when (result) {
                    is Resource.Loading -> NotificacoesUiState.Loading
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            NotificacoesUiState.Empty
                        } else {
                            NotificacoesUiState.Success(result.data)
                        }
                    }
                    is Resource.Error -> NotificacoesUiState.Error(
                        result.message ?: "Erro ao carregar notificações"
                    )
                }
            }
        }
    }

    // Observar contador de não lidas
    private fun observeCountNaoLidas() {
        viewModelScope.launch {
            getCountNaoLidasUseCase(usuarioId).collect { count ->
                _countNaoLidas.value = count
            }
        }
    }

    // Marcar notificação como lida
    fun marcarComoLida(notificacaoId: Long) {
        viewModelScope.launch {
            marcarNotificacaoLidaUseCase(notificacaoId)
            // Recarregar para atualizar lista
            loadNotificacoes()
        }
    }

    // Marcar todas como lidas
    fun marcarTodasComoLidas() {
        viewModelScope.launch {
            marcarTodasLidasUseCase(usuarioId)
            loadNotificacoes()
        }
    }

    // Recarregar
    fun refresh() {
        loadNotificacoes()
    }
}

// Estados possíveis das notificações
sealed class NotificacoesUiState {
    object Loading : NotificacoesUiState()
    object Empty : NotificacoesUiState()
    data class Success(val notificacoes: List<NotificacaoModel>) : NotificacoesUiState()
    data class Error(val message: String) : NotificacoesUiState()
}