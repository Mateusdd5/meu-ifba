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

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ========== SPLASH ==========
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

        // ========== AUTH ==========
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // RegisterScreen
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

        // ========== HOME ==========
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToEventoDetail = { eventoId ->
                    navController.navigate(Screen.EventoDetail.createRoute(eventoId))
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToNotificacoes = {
                    navController.navigate(Screen.Notificacoes.route)
                },
                onNavigateToMeusEventos = {
                    navController.navigate(Screen.MeusEventos.route)
                }
            )
        }

        // ========== EVENTO DETAIL ==========
        composable(
            route = Screen.EventoDetail.route,
            arguments = listOf(
                navArgument("eventoId") {
                    type = NavType.LongType
                }
            )
        ) {
            EventoDetailScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToEdit = { eventoId ->
                    navController.navigate(Screen.EditEvento.createRoute(eventoId))
                }
            )
        }

        // TODO: Adicionar outras telas conforme forem sendo criadas
    }
}