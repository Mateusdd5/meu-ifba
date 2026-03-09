package com.ifba.meuifba.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.presentation.viewmodel.HomeViewModel
import com.ifba.meuifba.presentation.viewmodel.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToEventoDetail: (Long) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToNotificacoes: () -> Unit,
    onNavigateToMeusEventos: () -> Unit,
    onNavigateToCreateEvento: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToAdminDashboard: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    var showSearchBar by remember { mutableStateOf(false) }

    val isAuthenticated = viewModel.isAuthenticated()
    val isAdmin = viewModel.isAdmin()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!showSearchBar) {
                        Text("Eventos")
                    }
                },
                actions = {
                    if (showSearchBar) {
                        TextField(
                            value = searchQuery,
                            onValueChange = viewModel::searchEventos,
                            placeholder = { Text("Buscar eventos...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            ),
                            trailingIcon = {
                                IconButton(onClick = {
                                    showSearchBar = false
                                    viewModel.clearSearch()
                                }) {
                                    Icon(Icons.Default.Close, "Fechar busca")
                                }
                            }
                        )
                    } else {
                        IconButton(onClick = { showSearchBar = true }) {
                            Icon(Icons.Default.Search, "Buscar")
                        }
                        if (isAuthenticated) {
                            if (isAdmin) {
                                IconButton(onClick = onNavigateToAdminDashboard) {
                                    Icon(Icons.Default.AdminPanelSettings, "Dashboard Admin")
                                }
                            }
                            IconButton(onClick = onNavigateToMeusEventos) {
                                Icon(Icons.Default.Bookmark, "Meus Eventos")
                            }
                            IconButton(onClick = onNavigateToNotificacoes) {
                                Icon(Icons.Default.Notifications, "Notificações")
                            }
                            IconButton(onClick = onNavigateToProfile) {
                                Icon(Icons.Default.Person, "Perfil")
                            }
                        } else {
                            IconButton(onClick = onNavigateToLogin) {
                                Icon(Icons.Default.Login, "Entrar")
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (isAuthenticated) {
                FloatingActionButton(onClick = onNavigateToCreateEvento) {
                    Icon(Icons.Default.Add, "Criar evento")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is HomeUiState.Loading -> LoadingState()
                is HomeUiState.Empty -> EmptyState(onRefresh = viewModel::loadEventos)
                is HomeUiState.Success -> EventosList(
                    eventos = state.eventos,
                    onEventoClick = onNavigateToEventoDetail,
                    onMarcacaoToggle = viewModel::toggleMarcacao
                )
                is HomeUiState.Error -> ErrorState(
                    message = state.message,
                    onRetry = viewModel::loadEventos
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(onRefresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(text = "📅", fontSize = 64.sp, modifier = Modifier.padding(bottom = 16.dp))
            Text(
                text = "Nenhum evento encontrado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Não há eventos disponíveis no momento",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(onClick = onRefresh) { Text("Atualizar") }
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(text = "⚠️", fontSize = 64.sp, modifier = Modifier.padding(bottom = 16.dp))
            Text(
                text = "Erro ao carregar eventos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(onClick = onRetry) { Text("Tentar novamente") }
        }
    }
}

@Composable
private fun EventosList(
    eventos: List<EventoModel>,
    onEventoClick: (Long) -> Unit,
    onMarcacaoToggle: (Long, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(eventos) { evento ->
            EventoCard(
                evento = evento,
                onClick = { onEventoClick(evento.id) },
                onMarcacaoToggle = { onMarcacaoToggle(evento.id, evento.isMarcado) }
            )
        }
    }
}

@Composable
private fun EventoCard(
    evento: EventoModel,
    onClick: () -> Unit,
    onMarcacaoToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = "${evento.categoria.icone} ${evento.categoria.nome}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                IconButton(onClick = onMarcacaoToggle) {
                    Icon(
                        imageVector = if (evento.isMarcado) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "Marcar interesse",
                        tint = if (evento.isMarcado) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = evento.titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = evento.descricao,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${evento.dataFormatada} • ${evento.horarioInicio}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = evento.local,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}