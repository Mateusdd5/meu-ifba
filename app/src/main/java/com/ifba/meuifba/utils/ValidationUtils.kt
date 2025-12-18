package com.ifba.meuifba.utils

object ValidationUtils {

    // Validar email
    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && email.matches(Regex(Constants.EMAIL_PATTERN))
    }

    // Validar email IFBA
    fun isIfbaEmail(email: String): Boolean {
        return email.isNotBlank() &&
                email.endsWith(Constants.EMAIL_DOMAIN) &&
                email.split("@").size == 2 &&
                email.split("@")[0].isNotEmpty()
    }

    // Validar senha
    fun isValidPassword(password: String): Boolean {
        return password.length >= Constants.MIN_PASSWORD_LENGTH &&
                password.length <= Constants.MAX_PASSWORD_LENGTH
    }

    // Verificar força da senha
    fun getPasswordStrength(password: String): PasswordStrength {
        if (password.length < Constants.MIN_PASSWORD_LENGTH) return PasswordStrength.WEAK

        var strength = 0

        // Critérios de força
        if (password.length >= 8) strength++
        if (password.any { it.isUpperCase() }) strength++
        if (password.any { it.isLowerCase() }) strength++
        if (password.any { it.isDigit() }) strength++
        if (password.any { !it.isLetterOrDigit() }) strength++

        return when {
            strength <= 2 -> PasswordStrength.WEAK
            strength == 3 -> PasswordStrength.MEDIUM
            else -> PasswordStrength.STRONG
        }
    }

    // Validar confirmação de senha
    fun passwordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && password.isNotBlank()
    }

    // Validar campo não vazio
    fun isNotEmpty(text: String): Boolean {
        return text.trim().isNotBlank()
    }

    // Validar comprimento mínimo
    fun hasMinLength(text: String, minLength: Int): Boolean {
        return text.length >= minLength
    }

    // Validar comprimento máximo
    fun hasMaxLength(text: String, maxLength: Int): Boolean {
        return text.length <= maxLength
    }

    // Validar tamanho de arquivo
    fun isValidFileSize(sizeInBytes: Long, maxSizeMB: Int): Boolean {
        val maxSizeBytes = maxSizeMB * 1024 * 1024
        return sizeInBytes <= maxSizeBytes
    }

    // Validar tipo de arquivo de imagem
    fun isValidImageType(mimeType: String): Boolean {
        return Constants.ALLOWED_IMAGE_TYPES.contains(mimeType.lowercase())
    }

    // Validar tipo de arquivo de documento
    fun isValidDocumentType(mimeType: String): Boolean {
        return Constants.ALLOWED_DOCUMENT_TYPES.contains(mimeType.lowercase())
    }

    // Validar número de vagas
    fun isValidVagasNumber(vagas: Int): Boolean {
        return vagas > 0
    }

    // Validar carga horária
    fun isValidCargaHoraria(horas: Int): Boolean {
        return horas > 0 && horas <= 200 // Limite razoável
    }

    // Validar horário (formato HH:mm)
    fun isValidTimeFormat(time: String): Boolean {
        return time.matches(Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]$"))
    }

    // Validar se horário fim é após horário início
    fun isEndTimeAfterStartTime(startTime: String, endTime: String): Boolean {
        if (!isValidTimeFormat(startTime) || !isValidTimeFormat(endTime)) {
            return false
        }

        val (startHour, startMin) = startTime.split(":").map { it.toInt() }
        val (endHour, endMin) = endTime.split(":").map { it.toInt() }

        return when {
            endHour > startHour -> true
            endHour == startHour -> endMin > startMin
            else -> false
        }
    }
}

enum class PasswordStrength {
    WEAK,
    MEDIUM,
    STRONG
}