package com.ifba.meuifba.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ifba.meuifba.presentation.screen.SplashScreen
import com.ifba.meuifba.presentation.screen.auth.LoginScreen
import com.ifba.meuifba.presentation.screen.auth.RegisterScreen
import com.ifba.meuifba.presentation.screen.home.HomeScreen
import com.ifba.meuifba.presentation.screen.evento.EventoDetailScreen
import com.ifba.meuifba.presentation.screen.evento.CreateEventoScreen
import com.ifba.meuifba.presentation.screen.profile.ProfileScreen
import com.ifba.meuifba.presentation.screen.notificacoes.NotificacoesScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToEventoDetail = { eventoId ->
                    navController.navigate(Screen.EventoDetail.createRoute(eventoId))
                },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToNotificacoes = { navController.navigate(Screen.Notificacoes.route) },
                onNavigateToMeusEventos = { navController.navigate(Screen.MeusEventos.route) },
                onNavigateToCreateEvento = { navController.navigate(Screen.CreateEvento.route) }
            )
        }

        composable(
            route = Screen.EventoDetail.route,
            arguments = listOf(navArgument("eventoId") { type = NavType.LongType })
        ) {
            EventoDetailScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToEdit = { eventoId ->
                    navController.navigate(Screen.EditEvento.createRoute(eventoId))
                }
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(route = Screen.Notificacoes.route) {
            NotificacoesScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(route = Screen.CreateEvento.route) {
            CreateEventoScreen(
                onNavigateBack = { navController.navigateUp() },
                onEventoCriado = { navController.navigateUp() }
            )
        }
    }
}