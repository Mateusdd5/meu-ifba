package com.ifba.meuifba.utils

object Constants {

    // Database
    const val DATABASE_NAME = "meu_ifba_database"
    const val DATABASE_VERSION = 1

    // Network
    const val BASE_URL_EMULATOR = "http://10.0.2.2:8080/api/"
    const val BASE_URL_DEVICE = "http://192.168.1.100:8080/api/"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    // Email validation
    const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@ifba\\.edu\\.br"
    const val EMAIL_DOMAIN = "@ifba.edu.br"

    // Password requirements
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_PASSWORD_LENGTH = 32

    // File upload
    const val MAX_IMAGE_SIZE_MB = 5
    const val MAX_DOCUMENT_SIZE_MB = 10
    const val MAX_IMAGE_SIZE_BYTES = MAX_IMAGE_SIZE_MB * 1024 * 1024
    const val MAX_DOCUMENT_SIZE_BYTES = MAX_DOCUMENT_SIZE_MB * 1024 * 1024

    // Allowed file types
    val ALLOWED_IMAGE_TYPES = listOf("image/jpeg", "image/jpg", "image/png")
    val ALLOWED_DOCUMENT_TYPES = listOf("application/pdf")

    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_PAGE = 0

    // Notification channels
    const val NOTIFICATION_CHANNEL_ID = "meu_ifba_notifications"
    const val NOTIFICATION_CHANNEL_NAME = "Eventos"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "Notificações sobre eventos do IFBA"

    // SharedPreferences keys
    const val PREFS_NAME = "meu_ifba_prefs"
    const val KEY_USER_ID = "user_id"
    const val KEY_USER_TOKEN = "user_token"
    const val KEY_USER_TYPE = "user_type"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_REMEMBER_ME = "remember_me"

    // Intent extras
    const val EXTRA_EVENTO_ID = "extra_evento_id"
    const val EXTRA_USUARIO_ID = "extra_usuario_id"

    // Tipos de usuário — valores padronizados usados no banco e em toda a aplicação
    const val TIPO_USUARIO = "USUARIO"   // estudante cadastrado
    const val TIPO_ADMIN = "ADMIN"       // administrador do IFBA

    // Status de inscrição
    const val STATUS_ABERTAS = "abertas"
    const val STATUS_ENCERRADAS = "encerradas"
    const val STATUS_EM_BREVE = "em_breve"

    // Tipos de notificação
    const val NOTIF_RECOMENDACAO = "recomendacao"
    const val NOTIF_LEMBRETE = "lembrete"
    const val NOTIF_ATUALIZACAO = "atualizacao"

    // Tipos de mídia
    const val TIPO_IMAGEM = "imagem"
    const val TIPO_DOCUMENTO = "documento"
}