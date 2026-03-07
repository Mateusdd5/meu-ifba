package com.ifba.meuifba.presentation.screen.evento

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ifba.meuifba.presentation.viewmodel.CreateEventoUiState
import com.ifba.meuifba.presentation.viewmodel.CreateEventoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventoScreen(
    onNavigateBack: () -> Unit,
    onEventoCriado: () -> Unit,
    viewModel: CreateEventoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val titulo by viewModel.titulo.collectAsStateWithLifecycle()
    val descricao by viewModel.descricao.collectAsStateWithLifecycle()
    val local by viewModel.local.collectAsStateWithLifecycle()
    val horarioInicio by viewModel.horarioInicio.collectAsStateWithLifecycle()
    val horarioFim by viewModel.horarioFim.collectAsStateWithLifecycle()
    val numeroVagas by viewModel.numeroVagas.collectAsStateWithLifecycle()
    val publicoAlvo by viewModel.publicoAlvo.collectAsStateWithLifecycle()
    val cargaHoraria by viewModel.cargaHoraria.collectAsStateWithLifecycle()
    val requisitos by viewModel.requisitos.collectAsStateWithLifecycle()
    val certificacao by viewModel.certificacao.collectAsStateWithLifecycle()
    val categorias by viewModel.categorias.collectAsStateWithLifecycle()
    val categoriaSelecionada by viewModel.categoriaSelecionada.collectAsStateWithLifecycle()
    val dataEvento by viewModel.dataEvento.collectAsStateWithLifecycle()

    var categoriasDropdownExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val dataFormatada = dataEvento?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date(it))
    } ?: ""

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth, 0, 0, 0)
            cal.set(Calendar.MILLISECOND, 0)
            viewModel.onDataEventoChange(cal.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(uiState) {
        when (uiState) {
            is CreateEventoUiState.Success -> onEventoCriado()
            is CreateEventoUiState.Error -> {
                snackbarHostState.showSnackbar((uiState as CreateEventoUiState.Error).message)
                viewModel.clearError()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Evento") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = viewModel::onTituloChange,
                label = { Text("Título *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = descricao,
                onValueChange = viewModel::onDescricaoChange,
                label = { Text("Descrição *") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            ExposedDropdownMenuBox(
                expanded = categoriasDropdownExpanded,
                onExpandedChange = { categoriasDropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = categoriaSelecionada?.nome ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriasDropdownExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoriasDropdownExpanded,
                    onDismissRequest = { categoriasDropdownExpanded = false }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.nome) },
                            onClick = {
                                viewModel.onCategoriaChange(categoria)
                                categoriasDropdownExpanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = local,
                onValueChange = viewModel::onLocalChange,
                label = { Text("Local *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = dataFormatada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Data do evento *") },
                placeholder = { Text("Selecione a data") },
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Selecionar data")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = horarioInicio,
                    onValueChange = viewModel::onHorarioInicioChange,
                    label = { Text("Início *") },
                    placeholder = { Text("08:00") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = horarioFim,
                    onValueChange = viewModel::onHorarioFimChange,
                    label = { Text("Fim *") },
                    placeholder = { Text("17:00") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = publicoAlvo,
                onValueChange = viewModel::onPublicoAlvoChange,
                label = { Text("Público alvo") },
                placeholder = { Text("Ex: Todos os alunos") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = cargaHoraria,
                    onValueChange = viewModel::onCargaHorariaChange,
                    label = { Text("Carga horária (h)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = numeroVagas,
                    onValueChange = viewModel::onNumeroVagasChange,
                    label = { Text("Vagas *") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            OutlinedTextField(
                value = requisitos,
                onValueChange = viewModel::onRequisitosChange,
                label = { Text("Requisitos") },
                placeholder = { Text("Ex: Conhecimento básico em Python") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 3
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Emite certificado?")
                Switch(
                    checked = certificacao,
                    onCheckedChange = viewModel::onCertificacaoChange
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = viewModel::saveEvento,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is CreateEventoUiState.Loading
            ) {
                if (uiState is CreateEventoUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text("Publicar Evento")
                }
            }
        }
    }
}