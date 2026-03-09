package com.ifba.meuifba.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")

    object EventoDetail : Screen("evento_detail/{eventoId}") {
        fun createRoute(eventoId: Long) = "evento_detail/$eventoId"
    }

    object CreateEvento : Screen("create_evento")
    object EditEvento : Screen("edit_evento/{eventoId}") {
        fun createRoute(eventoId: Long) = "edit_evento/$eventoId"
    }

    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object MeusEventos : Screen("meus_eventos")
    object Notificacoes : Screen("notificacoes")
    object Categorias : Screen("categorias")
    object AdminDashboard : Screen("admin_dashboard")
}