package com.ifba.meuifba.presentation.screen.evento

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.presentation.viewmodel.EventoDetailViewModel
import com.ifba.meuifba.presentation.viewmodel.EventoDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventoDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    viewModel: EventoDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        when (uiState) {
            is EventoDetailUiState.Deleted -> {
                snackbarHostState.showSnackbar("Evento deletado com sucesso")
                onNavigateBack()
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Evento") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::shareEvento) {
                        Icon(Icons.Default.Share, "Compartilhar")
                    }

                    if (viewModel.canEditOrDelete()) {
                        var showMenu by remember { mutableStateOf(false) }

                        Box {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(Icons.Default.MoreVert, "Mais opções")
                            }

                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Editar") },
                                    onClick = {
                                        showMenu = false
                                        val state = uiState
                                        if (state is EventoDetailUiState.Success) {
                                            onNavigateToEdit(state.evento.id)
                                        }
                                    },
                                    leadingIcon = {
                                        Icon(Icons.Default.Edit, null)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Deletar") },
                                    onClick = {
                                        showMenu = false
                                        showDeleteDialog = true
                                    },
                                    leadingIcon = {
                                        Icon(Icons.Default.Delete, null)
                                    }
                                )
                            }
                        }
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
                is EventoDetailUiState.Loading -> {
                    LoadingState()
                }
                is EventoDetailUiState.Success -> {
                    EventoDetailContent(
                        evento = state.evento,
                        onMarcacaoToggle = viewModel::toggleMarcacao
                    )
                }
                is EventoDetailUiState.Error -> {
                    ErrorState(
                        message = state.message,
                        onBack = onNavigateBack
                    )
                }
                is EventoDetailUiState.Deleted -> {
                    // Já tratado no LaunchedEffect
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Deletar evento") },
            text = { Text("Tem certeza que deseja deletar este evento? Esta ação não pode ser desfeita.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.deleteEvento()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Deletar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(
    message: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "⚠️",
                fontSize = 64.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Erro ao carregar evento",
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
            Button(onClick = onBack) {
                Text("Voltar")
            }
        }
    }
}

@Composable
private fun EventoDetailContent(
    evento: EventoModel,
    onMarcacaoToggle: () -> Unit
) {
    // Decodifica imagem Base64 para exibição no header
    val headerBitmap = remember(evento.imagemPrincipal) {
        evento.imagemPrincipal?.let {
            try {
                val bytes = Base64.decode(it, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
            } catch (e: Exception) { null }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header — imagem do evento ou ícone da categoria como fallback
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (headerBitmap != null) {
                    Image(
                        bitmap = headerBitmap,
                        contentDescription = "Imagem do evento",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = evento.categoria.icone,
                        fontSize = 80.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Badge da categoria
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "${evento.categoria.icone} ${evento.categoria.nome}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Medium
                )
            }

            // Título
            Text(
                text = evento.titulo,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Criador
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Por ${evento.criador.nome}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Informações principais
            InfoRow(
                icon = Icons.Default.CalendarToday,
                label = "Data",
                value = "${evento.dataFormatada} (${evento.diaDaSemana})"
            )
            InfoRow(
                icon = Icons.Default.AccessTime,
                label = "Horário",
                value = "${evento.horarioInicio} - ${evento.horarioFim} (${evento.duracao})"
            )
            InfoRow(
                icon = Icons.Default.LocationOn,
                label = "Local",
                value = evento.local
            )
            InfoRow(
                icon = Icons.Default.Group,
                label = "Público-alvo",
                value = evento.publicoAlvo ?: ""
            )

            if ((evento.cargaHoraria ?: 0) > 0) {
                InfoRow(
                    icon = Icons.Default.Schedule,
                    label = "Carga horária",
                    value = "${evento.cargaHoraria}h"
                )
            }

            InfoRow(
                icon = Icons.Default.EmojiEvents,
                label = "Certificação",
                value = if (evento.certificacao) "Sim" else "Não"
            )

            // Vagas
            if ((evento.numeroVagas ?: 0) > 0) {
                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Vagas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LinearProgressIndicator(
                    progress = { evento.vagasPercentual / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )

                Text(
                    text = "${evento.vagasDisponiveis} vagas disponíveis de ${evento.numeroVagas}",
                    fontSize = 14.sp,
                    color = if (evento.isLotado)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Descrição
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Sobre o evento",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = evento.descricao,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            // Requisitos
            if (!evento.requisitos.isNullOrBlank()) {
                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Requisitos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = evento.requisitos,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        // Botão fixo na parte inferior
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = onMarcacaoToggle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                enabled = !evento.isLotado || evento.isMarcado
            ) {
                Icon(
                    imageVector = if (evento.isMarcado)
                        Icons.Default.BookmarkRemove
                    else
                        Icons.Default.BookmarkAdd,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when {
                        evento.isLotado && evento.isMarcado -> "Remover interesse"
                        evento.isLotado -> "Evento lotado"
                        evento.isMarcado -> "Remover interesse"
                        else -> "Marcar interesse"
                    },
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .padding(top = 2.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}