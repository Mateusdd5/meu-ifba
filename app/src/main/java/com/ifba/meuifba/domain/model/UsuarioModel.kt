package com.ifba.meuifba.domain.model

data class UsuarioModel(
    val id: Long,
    val nome: String,
    val email: String,
    val tipoUsuario: String,
    val fotoPerfil: String?,
    val curso: CursoModel,
    val dataCadastro: Long,
    val statusConta: String,

    // Campos derivados
    val iniciais: String,
    val dataCadastroFormatada: String,
    val isOrganizador: Boolean,
    val isAdmin: Boolean,

    // Preferências
    val categoriasPreferidas: List<CategoriaModel> = emptyList(),

    // Estatísticas pessoais
    val totalEventosMarcados: Int = 0,
    val totalEventosParticipados: Int = 0,
    val totalEventosCriados: Int = 0
)

data class CursoModel(
    val id: Long,
    val nome: String,
    val area: AreaConhecimentoModel
)

data class AreaConhecimentoModel(
    val id: Long,
    val nome: String,
    val descricao: String?
)