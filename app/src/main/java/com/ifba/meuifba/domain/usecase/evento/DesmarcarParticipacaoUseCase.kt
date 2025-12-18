package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class DesmarcarParticipacaoUseCase @Inject constructor(
    // private val marcacaoRepository: MarcacaoParticipacaoRepository,
    // private val eventoRepository: EventoRepository
) {

    suspend operator fun invoke(usuarioId: Long, eventoId: Long): Resource<Unit> {
        TODO("Implementar quando Repositories estiverem prontos")
    }

    /*
    // Implementação futura:
    suspend operator fun invoke(usuarioId: Long, eventoId: Long): Resource<Unit> {
        return try {
            // Verificar se está marcado
            val marcado = marcacaoRepository.isMarcado(usuarioId, eventoId)
            if (!marcado) {
                return Resource.Error("Você não marcou interesse neste evento")
            }

            // Desmarcar participação
            marcacaoRepository.desmarcarParticipacao(usuarioId, eventoId)

            // Incrementar vagas disponíveis
            eventoRepository.incrementarVagas(eventoId)

            // Decrementar estatísticas
            eventoRepository.decrementarMarcacoes(eventoId)

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao desmarcar participação")
        }
    }
    */
}