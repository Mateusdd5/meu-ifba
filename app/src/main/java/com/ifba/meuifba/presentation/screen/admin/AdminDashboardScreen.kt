package com.ifba.meuifba.presentation.screen.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ifba.meuifba.data.remote.dto.CategoriaStatResponse
import com.ifba.meuifba.data.remote.dto.DashboardResponse
import com.ifba.meuifba.data.remote.dto.EventoPopularResponse
import com.ifba.meuifba.presentation.viewmodel.AdminDashboardUiState
import com.ifba.meuifba.presentation.viewmodel.AdminDashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Admin") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::loadDashboard) {
                        Icon(Icons.Default.Refresh, "Atualizar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is AdminDashboardUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is AdminDashboardUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("⚠️", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.message,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = viewModel::loadDashboard) {
                            Text("Tentar novamente")
                        }
                    }
                }
                is AdminDashboardUiState.Success -> {
                    DashboardContent(dashboard = state.dashboard)
                }
            }
        }
    }
}

@Composable
private fun DashboardContent(dashboard: DashboardResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cards de resumo
        Text(
            text = "Visão Geral",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumoCard(
                modifier = Modifier.weight(1f),
                icon = "📅",
                label = "Eventos",
                value = dashboard.totalEventos.toString()
            )
            ResumoCard(
                modifier = Modifier.weight(1f),
                icon = "👥",
                label = "Usuários",
                value = dashboard.totalUsuarios.toString()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResumoCard(
                modifier = Modifier.weight(1f),
                icon = "🔖",
                label = "Marcações",
                value = dashboard.totalMarcacoes.toString()
            )
            ResumoCard(
                modifier = Modifier.weight(1f),
                icon = "🔮",
                label = "Futuros",
                value = dashboard.eventosFuturos.toString()
            )
        }

        Divider()

        // Eventos mais populares
        Text(
            text = "Eventos Mais Populares",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        if (dashboard.eventosMaisPopulares.isEmpty()) {
            Text(
                text = "Nenhum dado disponível ainda",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            dashboard.eventosMaisPopulares.forEachIndexed { index, evento ->
                EventoPopularCard(posicao = index + 1, evento = evento)
            }
        }

        Divider()

        // Eventos por categoria
        Text(
            text = "Eventos por Categoria",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        if (dashboard.eventosPorCategoria.isEmpty()) {
            Text(
                text = "Nenhum dado disponível ainda",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            val maxTotal = dashboard.eventosPorCategoria.maxOf { it.total }.toFloat()
            dashboard.eventosPorCategoria.forEach { stat ->
                CategoriaStatRow(stat = stat, maxTotal = maxTotal)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ResumoCard(
    modifier: Modifier = Modifier,
    icon: String,
    label: String,
    value: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun EventoPopularCard(posicao: Int, evento: EventoPopularResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Posição
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = when (posicao) {
                    1 -> MaterialTheme.colorScheme.primary
                    2 -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.surfaceVariant
                },
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "#$posicao",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (posicao) {
                            1 -> MaterialTheme.colorScheme.onPrimary
                            2 -> MaterialTheme.colorScheme.onSecondary
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = evento.titulo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = evento.categoria,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${evento.vagasDisponiveis}/${evento.numeroVagas} vagas",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = evento.totalMarcacoes.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "marcações",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CategoriaStatRow(stat: CategoriaStatResponse, maxTotal: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stat.categoria, fontSize = 14.sp)
            Text(
                text = "${stat.total} evento${if (stat.total != 1) "s" else ""}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { if (maxTotal > 0) stat.total / maxTotal else 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
        )
    }
}