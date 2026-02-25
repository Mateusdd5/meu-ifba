package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.domain.model.CategoriaModel
import com.ifba.meuifba.domain.usecase.usuario.GetPreferenciasUsuarioUseCase
import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriasViewModel @Inject constructor(
    private val getPreferenciasUsuarioUseCase: GetPreferenciasUsuarioUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // Estado da UI
    private val _uiState = MutableStateFlow<CategoriasUiState>(CategoriasUiState.Loading)
    val uiState: StateFlow<CategoriasUiState> = _uiState.asStateFlow()

    // Categorias disponíveis (todas)
    private val _categorias = MutableStateFlow<List<CategoriaModel>>(emptyList())
    val categorias: StateFlow<List<CategoriaModel>> = _categorias.asStateFlow()

    // Categorias preferidas do usuário
    private val _categoriasPreferidas = MutableStateFlow<List<CategoriaModel>>(emptyList())
    val categoriasPreferidas: StateFlow<List<CategoriaModel>> = _categoriasPreferidas.asStateFlow()

    // ID do usuário logado
    private val usuarioId: Long
        get() = preferencesManager.userId

    init {
        loadCategorias()
        loadPreferencias()
    }

    // Carregar todas as categorias
    private fun loadCategorias() {
        // TODO: Criar UseCase GetCategoriasUseCase quando API estiver pronta
        // Por enquanto, lista mock
        viewModelScope.launch {
            _categorias.value = listOf(
                CategoriaModel(
                    id = 1,
                    nome = "Palestra",
                    descricao = "Apresentações e talks",
                    icone = "🎤",
                    cor = "#2196F3"
                ),
                CategoriaModel(
                    id = 2,
                    nome = "Workshop",
                    descricao = "Atividades práticas",
                    icone = "🛠️",
                    cor = "#FF9800"
                ),
                CategoriaModel(
                    id = 3,
                    nome = "Seminário",
                    descricao = "Discussões acadêmicas",
                    icone = "📚",
                    cor = "#9C27B0"
                ),
                CategoriaModel(
                    id = 4,
                    nome = "Competição",
                    descricao = "Eventos competitivos",
                    icone = "🏆",
                    cor = "#F44336"
                ),
                CategoriaModel(
                    id = 5,
                    nome = "Hackathon",
                    descricao = "Maratonas de programação",
                    icone = "💻",
                    cor = "#4CAF50"
                )
            )
            _uiState.value = CategoriasUiState.Success
        }
    }

    // Carregar preferências do usuário
    private fun loadPreferencias() {
        viewModelScope.launch {
            getPreferenciasUsuarioUseCase(usuarioId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _categoriasPreferidas.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        // Erro ao carregar preferências (não crítico)
                        _categoriasPreferidas.value = emptyList()
                    }
                    is Resource.Loading -> {
                        // Ignorar
                    }
                }
            }
        }
    }

    // Verificar se categoria é preferida
    fun isCategoriaPreferida(categoriaId: Long): Boolean {
        return _categoriasPreferidas.value.any { it.id == categoriaId }
    }

    // Toggle preferência (adicionar/remover)
    fun togglePreferencia(categoria: CategoriaModel) {
        // TODO: Implementar UseCase UpdatePreferenciasUseCase quando API estiver pronta
        viewModelScope.launch {
            val currentPreferidas = _categoriasPreferidas.value.toMutableList()

            if (isCategoriaPreferida(categoria.id)) {
                // Remover
                currentPreferidas.removeAll { it.id == categoria.id }
            } else {
                // Adicionar
                currentPreferidas.add(categoria)
            }

            _categoriasPreferidas.value = currentPreferidas

            // TODO: Salvar no backend quando API estiver pronta
        }
    }

    // Recarregar
    fun refresh() {
        loadCategorias()
        loadPreferencias()
    }
}

// Estados possíveis
sealed class CategoriasUiState {
    object Loading : CategoriasUiState()
    object Success : CategoriasUiState()
    data class Error(val message: String) : CategoriasUiState()
}