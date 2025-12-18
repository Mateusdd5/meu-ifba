package com.ifba.meuifba.domain.model

data class CategoriaModel(
    val id: Long,
    val nome: String,
    val descricao: String?,

    // Campos derivados para UI
    val icone: String, // Emoji ou nome do ícone
    val cor: String, // Cor temática da categoria

    // Estatísticas
    val totalEventos: Int = 0,
    val isPreferida: Boolean = false
)

// Função helper para mapear categoria → ícone
fun CategoriaModel.getIconeEmoji(): String {
    return when (nome.lowercase()) {
        "palestra" -> "🎤"
        "workshop" -> "🛠️"
        "seminário" -> "📚"
        "competição" -> "🏆"
        "hackathon" -> "💻"
        "feira" -> "🔬"
        "feira científica" -> "🔬"
        "visita" -> "🚌"
        "visita técnica" -> "🚌"
        "defesa" -> "🎓"
        "defesa de tcc" -> "🎓"
        "reunião" -> "👥"
        "reunião estudantil" -> "👥"
        else -> "📅"
    }
}

// Função helper para mapear categoria → cor
fun CategoriaModel.getCorTema(): String {
    return when (nome.lowercase()) {
        "palestra" -> "#2196F3" // Azul
        "workshop" -> "#FF9800" // Laranja
        "seminário" -> "#9C27B0" // Roxo
        "competição" -> "#F44336" // Vermelho
        "hackathon" -> "#4CAF50" // Verde
        "feira", "feira científica" -> "#00BCD4" // Ciano
        "visita", "visita técnica" -> "#FFC107" // Amarelo
        "defesa", "defesa de tcc" -> "#3F51B5" // Índigo
        "reunião", "reunião estudantil" -> "#795548" // Marrom
        else -> "#607D8B" // Cinza
    }
}