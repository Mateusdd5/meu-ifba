package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifba.meuifba.data.remote.dto.DashboardResponse
import com.ifba.meuifba.data.repository.AdminRepository
import com.ifba.meuifba.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AdminDashboardUiState>(AdminDashboardUiState.Loading)
    val uiState: StateFlow<AdminDashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = AdminDashboardUiState.Loading
            when (val result = adminRepository.getDashboard()) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _uiState.value = AdminDashboardUiState.Success(result.data)
                    } else {
                        _uiState.value = AdminDashboardUiState.Error("Dados indisponíveis")
                    }
                }
                is Resource.Error -> {
                    _uiState.value = AdminDashboardUiState.Error(
                        result.message ?: "Erro ao carregar dashboard"
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = AdminDashboardUiState.Loading
                }
            }
        }
    }
}

sealed class AdminDashboardUiState {
    object Loading : AdminDashboardUiState()
    data class Success(val dashboard: DashboardResponse) : AdminDashboardUiState()
    data class Error(val message: String) : AdminDashboardUiState()
}