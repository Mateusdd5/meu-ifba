package com.ifba.meuifba.data.mapper

import com.ifba.meuifba.data.local.database.entities.AreaConhecimento
import com.ifba.meuifba.data.local.database.entities.Curso
import com.ifba.meuifba.data.remote.dto.AreaConhecimentoResponse
import com.ifba.meuifba.data.remote.dto.CursoResponse
import com.ifba.meuifba.domain.model.CursoModel
import com.ifba.meuifba.domain.model.AreaConhecimentoModel

// ========== CURSO ==========

// Entity -> Model
fun Curso.toModel(area: AreaConhecimento): CursoModel {
    return CursoModel(
        id = this.id,
        nome = this.nomeCurso,
        area = AreaConhecimentoModel(
            id = area.id,
            nome = area.nomeArea,
            descricao = area.descricao
        )
    )
}

// DTO -> Entity
fun CursoResponse.toEntity(): Curso {
    return Curso(
        id = this.id,
        nomeCurso = this.nome,
        areaId = this.areaConhecimento.id
    )
}

// DTO -> Model
fun CursoResponse.toModel(): CursoModel {
    return CursoModel(
        id = this.id,
        nome = this.nome,
        area = AreaConhecimentoModel(
            id = this.areaConhecimento.id,
            nome = this.areaConhecimento.nome,
            descricao = this.areaConhecimento.descricao
        )
    )
}

// ========== AREA CONHECIMENTO ==========

// DTO -> Entity
fun AreaConhecimentoResponse.toEntity(): AreaConhecimento {
    return AreaConhecimento(
        id = this.id,
        nomeArea = this.nome,
        descricao = this.descricao
    )
}