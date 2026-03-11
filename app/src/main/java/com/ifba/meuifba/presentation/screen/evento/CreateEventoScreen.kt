package com.ifba.meuifba.presentation.screen.evento

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ifba.meuifba.presentation.viewmodel.CreateEventoUiState
import com.ifba.meuifba.presentation.viewmodel.CreateEventoViewModel
import java.io.ByteArrayOutputStream
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
    val imagemBase64 by viewModel.imagemBase64.collectAsStateWithLifecycle()

    var categoriasDropdownExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val dataFormatada = dataEvento?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date(it))
    } ?: "Selecionar data"

    // Função que abre o DatePicker
    val abrirDatePicker = {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                cal.set(year, month, day, 0, 0, 0)
                viewModel.onDataEventoChange(cal.timeInMillis)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Launcher para selecionar imagem da galeria
    val imagemLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                val resized = resizeBitmap(originalBitmap, 800)
                val outputStream = ByteArrayOutputStream()
                resized.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
                val bytes = outputStream.toByteArray()
                val base64 = Base64.encodeToString(bytes, Base64.DEFAULT)
                viewModel.onImagemBase64Change(base64)
            } catch (e: Exception) {
                // Falha silenciosa — mantém imagem anterior
            }
        }
    }

    // Bitmap para preview
    val previewBitmap = remember(imagemBase64) {
        imagemBase64?.let {
            try {
                val bytes = Base64.decode(it, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
            } catch (e: Exception) { null }
        }
    }

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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(if (viewModel.isEditMode) "Editar Evento" else "Novo Evento") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Seletor de imagem
            Text("Imagem do evento (opcional)", style = MaterialTheme.typography.labelLarge)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { imagemLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (previewBitmap != null) {
                    Image(
                        bitmap = previewBitmap,
                        contentDescription = "Preview da imagem",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        IconButton(
                            onClick = { viewModel.onImagemBase64Change(null) },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.errorContainer
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remover imagem",
                                    tint = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.padding(4.dp).size(16.dp)
                                )
                            }
                        }
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Toque para adicionar imagem",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            HorizontalDivider()

            // Título
            OutlinedTextField(
                value = titulo,
                onValueChange = viewModel::onTituloChange,
                label = { Text("Título *") },
                leadingIcon = { Icon(Icons.Default.Title, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = uiState !is CreateEventoUiState.Loading
            )

            // Descrição
            OutlinedTextField(
                value = descricao,
                onValueChange = viewModel::onDescricaoChange,
                label = { Text("Descrição *") },
                leadingIcon = { Icon(Icons.Default.Description, null) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                enabled = uiState !is CreateEventoUiState.Loading
            )

            // Categoria
            ExposedDropdownMenuBox(
                expanded = categoriasDropdownExpanded,
                onExpandedChange = { categoriasDropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = categoriaSelecionada?.nome ?: "Selecionar categoria *",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    leadingIcon = { Icon(Icons.Default.Category, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriasDropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    enabled = uiState !is CreateEventoUiState.Loading
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

            // Data — Box clicável cobrindo toda a área do campo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = uiState !is CreateEventoUiState.Loading,
                        onClick = abrirDatePicker
                    )
            ) {
                OutlinedTextField(
                    value = dataFormatada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data *") },
                    leadingIcon = {
                        Icon(Icons.Default.CalendarToday, null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false, // desabilitado para não interceptar o clique do Box
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Horários
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = horarioInicio,
                    onValueChange = viewModel::onHorarioInicioChange,
                    label = { Text("Início") },
                    leadingIcon = { Icon(Icons.Default.AccessTime, null) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("08:00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = uiState !is CreateEventoUiState.Loading
                )
                OutlinedTextField(
                    value = horarioFim,
                    onValueChange = viewModel::onHorarioFimChange,
                    label = { Text("Fim") },
                    leadingIcon = { Icon(Icons.Default.AccessTime, null) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("18:00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = uiState !is CreateEventoUiState.Loading
                )
            }

            // Local
            OutlinedTextField(
                value = local,
                onValueChange = viewModel::onLocalChange,
                label = { Text("Local") },
                leadingIcon = { Icon(Icons.Default.LocationOn, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = uiState !is CreateEventoUiState.Loading
            )

            // Público-alvo
            OutlinedTextField(
                value = publicoAlvo,
                onValueChange = viewModel::onPublicoAlvoChange,
                label = { Text("Público-alvo") },
                leadingIcon = { Icon(Icons.Default.Group, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = uiState !is CreateEventoUiState.Loading
            )

            // Vagas e Carga Horária
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = numeroVagas,
                    onValueChange = viewModel::onNumeroVagasChange,
                    label = { Text("Vagas") },
                    leadingIcon = { Icon(Icons.Default.EventSeat, null) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = uiState !is CreateEventoUiState.Loading
                )
                OutlinedTextField(
                    value = cargaHoraria,
                    onValueChange = viewModel::onCargaHorariaChange,
                    label = { Text("Carga (h)") },
                    leadingIcon = { Icon(Icons.Default.Schedule, null) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = uiState !is CreateEventoUiState.Loading
                )
            }

            // Certificação
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.EmojiEvents, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Oferece certificação")
                }
                Switch(
                    checked = certificacao,
                    onCheckedChange = viewModel::onCertificacaoChange,
                    enabled = uiState !is CreateEventoUiState.Loading
                )
            }

            // Requisitos
            OutlinedTextField(
                value = requisitos,
                onValueChange = viewModel::onRequisitosChange,
                label = { Text("Requisitos (opcional)") },
                leadingIcon = { Icon(Icons.Default.Checklist, null) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4,
                enabled = uiState !is CreateEventoUiState.Loading
            )

            // Botão salvar
            Button(
                onClick = viewModel::saveEvento,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = uiState !is CreateEventoUiState.Loading
            ) {
                if (uiState is CreateEventoUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = if (viewModel.isEditMode) Icons.Default.Save else Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (viewModel.isEditMode) "Salvar alterações" else "Criar evento", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private fun resizeBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    if (width <= maxSize && height <= maxSize) return bitmap
    val ratio = width.toFloat() / height.toFloat()
    val newWidth: Int
    val newHeight: Int
    if (width > height) {
        newWidth = maxSize
        newHeight = (maxSize / ratio).toInt()
    } else {
        newHeight = maxSize
        newWidth = (maxSize * ratio).toInt()
    }
    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
}