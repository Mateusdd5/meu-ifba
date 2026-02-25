package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.data.repository.PreferenciaUsuarioRepository
import com.ifba.meuifba.domain.model.CategoriaModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferenciasUsuarioUseCase @Inject constructor(
    private val preferenciaRepository: PreferenciaUsuarioRepository
) {
    operator fun invoke(usuarioId: Long): Flow<Resource<List<CategoriaModel>>> {
        return preferenciaRepository.getPreferencias(usuarioId)
    }
}