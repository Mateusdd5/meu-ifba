package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.CategoriaModel
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.domain.usecase.evento.CreateEventoUseCase
import com.ifba.meuifba.domain.usecase.evento.UpdateEventoUseCase
import com.ifba.meuifba.domain.usecase.evento.GetEventoByIdUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventoViewModel @Inject constructor(
    private val createEventoUseCase: CreateEventoUseCase,
    private val updateEventoUseCase: UpdateEventoUseCase,
    private val getEventoByIdUseCase: GetEventoByIdUseCase,
    private val preferencesManager: PreferencesManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Se está editando (tem eventoId) ou criando novo (null)
    private val eventoId: Long? = savedStateHandle.get<Long>("eventoId")
    private val isEditMode: Boolean = eventoId != null

    // Estado da UI
    private val _uiState = MutableStateFlow<CreateEventoUiState>(CreateEventoUiState.Idle)
    val uiState: StateFlow<CreateEventoUiState> = _uiState.asStateFlow()

    // Campos do formulário
    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo.asStateFlow()

    private val _descricao = MutableStateFlow("")
    val descricao: StateFlow<String> = _descricao.asStateFlow()

    private val _local = MutableStateFlow("")
    val local: StateFlow<String> = _local.asStateFlow()

    private val _dataEvento = MutableStateFlow<Long?>(null)
    val dataEvento: StateFlow<Long?> = _dataEvento.asStateFlow()

    private val _horarioInicio = MutableStateFlow("")
    val horarioInicio: StateFlow<String> = _horarioInicio.asStateFlow()

    private val _horarioFim = MutableStateFlow("")
    val horarioFim: StateFlow<String> = _horarioFim.asStateFlow()

    private val _publicoAlvo = MutableStateFlow("")
    val publicoAlvo: StateFlow<String> = _publicoAlvo.asStateFlow()

    private val _cargaHoraria = MutableStateFlow("")
    val cargaHoraria: StateFlow<String> = _cargaHoraria.asStateFlow()

    private val _numeroVagas = MutableStateFlow("")
    val numeroVagas: StateFlow<String> = _numeroVagas.asStateFlow()

    private val _requisitos = MutableStateFlow("")
    val requisitos: StateFlow<String> = _requisitos.asStateFlow()

    private val _certificacao = MutableStateFlow(false)
    val certificacao: StateFlow<Boolean> = _certificacao.asStateFlow()

    private val _categoriaSelecionada = MutableStateFlow<CategoriaModel?>(null)
    val categoriaSelecionada: StateFlow<CategoriaModel?> = _categoriaSelecionada.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId

    init {
        if (isEditMode && eventoId != null) {
            loadEvento(eventoId)
        }
    }

    // Carregar evento para edição
    private fun loadEvento(id: Long) {
        viewModelScope.launch {
            _uiState.value = CreateEventoUiState.Loading

            val evento = getEventoByIdUseCase(id)
            if (evento != null) {
                // Preencher campos com dados do evento
                _titulo.value = evento.titulo
                _descricao.value = evento.descricao
                _local.value = evento.local
                _dataEvento.value = evento.dataEvento
                _horarioInicio.value = evento.horarioInicio
                _horarioFim.value = evento.horarioFim
                _publicoAlvo.value = evento.publicoAlvo
                _cargaHoraria.value = evento.cargaHoraria.toString()
                _numeroVagas.value = evento.numeroVagas.toString()
                _requisitos.value = evento.requisitos ?: ""
                _certificacao.value = evento.certificacao
                _categoriaSelecionada.value = evento.categoria

                _uiState.value = CreateEventoUiState.Idle
            } else {
                _uiState.value = CreateEventoUiState.Error("Evento não encontrado")
            }
        }
    }

    // Funções para atualizar campos
    fun onTituloChange(value: String) { _titulo.value = value }
    fun onDescricaoChange(value: String) { _descricao.value = value }
    fun onLocalChange(value: String) { _local.value = value }
    fun onDataEventoChange(value: Long) { _dataEvento.value = value }
    fun onHorarioInicioChange(value: String) { _horarioInicio.value = value }
    fun onHorarioFimChange(value: String) { _horarioFim.value = value }
    fun onPublicoAlvoChange(value: String) { _publicoAlvo.value = value }
    fun onCargaHorariaChange(value: String) { _cargaHoraria.value = value }
    fun onNumeroVagasChange(value: String) { _numeroVagas.value = value }
    fun onRequisitosChange(value: String) { _requisitos.value = value }
    fun onCertificacaoChange(value: Boolean) { _certificacao.value = value }
    fun onCategoriaSelected(categoria: CategoriaModel) {
        _categoriaSelecionada.value = categoria
    }

    // Salvar evento (criar ou atualizar)
    fun saveEvento() {
        // Validações
        if (_titulo.value.isBlank()) {
            _uiState.value = CreateEventoUiState.Error("Título não pode estar vazio")
            return
        }

        if (_descricao.value.isBlank()) {
            _uiState.value = CreateEventoUiState.Error("Descrição não pode estar vazia")
            return
        }

        if (_dataEvento.value == null) {
            _uiState.value = CreateEventoUiState.Error("Selecione uma data")
            return
        }

        if (_categoriaSelecionada.value == null) {
            _uiState.value = CreateEventoUiState.Error("Selecione uma categoria")
            return
        }

        val cargaHorariaInt = _cargaHoraria.value.toIntOrNull() ?: 0
        val numeroVagasInt = _numeroVagas.value.toIntOrNull() ?: 0

        viewModelScope.launch {
            _uiState.value = CreateEventoUiState.Loading

            val result = if (isEditMode && eventoId != null) {
                // Atualizar evento existente
                updateEventoUseCase(
                    eventoId = eventoId,
                    titulo = _titulo.value,
                    descricao = _descricao.value,
                    categoriaId = _categoriaSelecionada.value!!.id,
                    dataEvento = _dataEvento.value!!,
                    horarioInicio = _horarioInicio.value,
                    horarioFim = _horarioFim.value,
                    local = _local.value,
                    publicoAlvo = _publicoAlvo.value,
                    cargaHoraria = cargaHorariaInt,
                    certificacao = _certificacao.value,
                    requisitos = _requisitos.value.ifBlank { null },
                    numeroVagas = numeroVagasInt
                )
            } else {
                // Criar novo evento
                createEventoUseCase(
                    titulo = _titulo.value,
                    descricao = _descricao.value,
                    categoriaId = _categoriaSelecionada.value!!.id,
                    dataEvento = _dataEvento.value!!,
                    horarioInicio = _horarioInicio.value,
                    horarioFim = _horarioFim.value,
                    local = _local.value,
                    publicoAlvo = _publicoAlvo.value,
                    cargaHoraria = cargaHorariaInt,
                    certificacao = _certificacao.value,
                    requisitos = _requisitos.value.ifBlank { null },
                    numeroVagas = numeroVagasInt,
                    usuarioCriadorId = usuarioId
                )
            }

            _uiState.value = when (result) {
                is Resource.Success -> {
                    result.data?.let { CreateEventoUiState.Success(it) }
                        ?: CreateEventoUiState.Error("Erro ao salvar evento")
                }
                is Resource.Error -> CreateEventoUiState.Error(
                    result.message ?: "Erro ao salvar evento"
                )
                is Resource.Loading -> CreateEventoUiState.Loading
            }
        }
    }

    // Limpar erro
    fun clearError() {
        if (_uiState.value is CreateEventoUiState.Error) {
            _uiState.value = CreateEventoUiState.Idle
        }
    }
}

// Estados possíveis da tela de criar/editar evento
sealed class CreateEventoUiState {
    object Idle : CreateEventoUiState()
    object Loading : CreateEventoUiState()
    data class Success(val evento: EventoModel) : CreateEventoUiState()
    data class Error(val message: String) : CreateEventoUiState()
}