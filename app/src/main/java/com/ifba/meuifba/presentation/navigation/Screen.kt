package com.ifba.meuifba.presentation.navigation

sealed class Screen(val route: String) {
    // Splash
    object Splash : Screen("splash")

    // Auth
    object Login : Screen("login")
    object Register : Screen("register")

    // Home
    object Home : Screen("home")

    // Evento
    object EventoDetail : Screen("evento_detail/{eventoId}") {
        fun createRoute(eventoId: Long) = "evento_detail/$eventoId"
    }

    object CreateEvento : Screen("create_evento")
    object EditEvento : Screen("edit_evento/{eventoId}") {
        fun createRoute(eventoId: Long) = "edit_evento/$eventoId"
    }

    // Profile
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object MeusEventos : Screen("meus_eventos")

    // Notificações
    object Notificacoes : Screen("notificacoes")

    // Categorias
    object Categorias : Screen("categorias")
}