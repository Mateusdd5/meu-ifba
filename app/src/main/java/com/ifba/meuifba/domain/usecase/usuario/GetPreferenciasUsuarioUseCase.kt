package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.domain.model.CategoriaModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferenciasUsuarioUseCase @Inject constructor(
    // private val preferenciaRepository: PreferenciaUsuarioRepository,
    // private val categoriaRepository: CategoriaEventoRepository
) {

    operator fun invoke(usuarioId: Long): Flow<Resource<List<CategoriaModel>>> {
        TODO("Implementar quando Repositories estiverem prontos")
    }

    /*
    // Implementação futura:
    // - Buscar IDs das categorias preferidas
    // - Buscar dados completos de cada categoria
    // - Retornar lista de CategoriaModel com flag isPreferida=true
    */
}