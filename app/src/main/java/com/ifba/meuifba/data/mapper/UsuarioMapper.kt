package com.ifba.meuifba.data.mapper

import com.ifba.meuifba.data.local.database.entities.Usuario
import com.ifba.meuifba.data.local.database.entities.Curso
import com.ifba.meuifba.data.local.database.entities.AreaConhecimento
import com.ifba.meuifba.data.remote.dto.UsuarioResponse
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.domain.model.CursoModel
import com.ifba.meuifba.domain.model.AreaConhecimentoModel
import java.text.SimpleDateFormat
import java.util.*

// ========== USUARIO ==========

// Entity -> Model
fun Usuario.toModel(
    curso: Curso,
    area: AreaConhecimento
): UsuarioModel {
    return UsuarioModel(
        id = this.id,
        nome = this.nome,
        email = this.email,
        tipoUsuario = this.tipoUsuario,
        fotoPerfil = this.fotoPerfil,
        curso = CursoModel(
            id = curso.id,
            nome = curso.nomeCurso,    // ← CORRIGIDO
            area = AreaConhecimentoModel(
                id = area.id,
                nome = area.nomeArea,
                descricao = area.descricao
            )
        ),
        dataCadastro = this.dataCadastro,
        statusConta = this.statusConta,
        iniciais = getIniciais(this.nome),
        dataCadastroFormatada = formatDate(this.dataCadastro),
        isOrganizador = this.tipoUsuario == "ORGANIZADOR",
        isAdmin = this.tipoUsuario == "ADMIN"
    )
}

// DTO -> Entity
fun UsuarioResponse.toEntity(): Usuario {
    return Usuario(
        id = this.id,
        nome = this.nome,
        email = this.email,
        senha = "", // Não armazenamos senha localmente
        tipoUsuario = this.tipoUsuario,
        fotoPerfil = this.fotoPerfil,
        cursoId = this.curso.id,
        dataCadastro = this.dataCadastro,
        statusConta = this.statusConta
    )
}

// DTO -> Model
fun UsuarioResponse.toModel(): UsuarioModel {
    return UsuarioModel(
        id = this.id,
        nome = this.nome,
        email = this.email,
        tipoUsuario = this.tipoUsuario,
        fotoPerfil = this.fotoPerfil,
        curso = CursoModel(
            id = this.curso.id,
            nome = this.curso.nome,  // ← Este OK (vem do DTO)
            area = AreaConhecimentoModel(
                id = this.curso.areaConhecimento.id,
                nome = this.curso.areaConhecimento.nome,
                descricao = this.curso.areaConhecimento.descricao
            )
        ),
        dataCadastro = this.dataCadastro,
        statusConta = this.statusConta,
        iniciais = getIniciais(this.nome),
        dataCadastroFormatada = formatDate(this.dataCadastro),
        isOrganizador = this.tipoUsuario == "ORGANIZADOR",
        isAdmin = this.tipoUsuario == "ADMIN"
    )
}

// ========== FUNÇÕES AUXILIARES ==========

private fun getIniciais(nome: String): String {
    return nome.split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(2)
        .joinToString("")
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    return sdf.format(Date(timestamp))
}