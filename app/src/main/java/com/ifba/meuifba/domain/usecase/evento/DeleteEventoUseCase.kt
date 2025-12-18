package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class DeleteEventoUseCase @Inject constructor(
    // private val eventoRepository: EventoRepository
) {

    suspend operator fun invoke(eventoId: Long, usuarioId: Long): Resource<Unit> {
        TODO("Implementar quando EventoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    // - Verificar se usuário é criador do evento ou admin
    // - Verificar se há pessoas marcadas (avisar)
    // - Deletar evento e notificar usuários marcados
    */
}