package com.ifba.meuifba.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

// Toast extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

// String extensions
fun String.isValidEmail(): Boolean {
    return this.matches(Regex(Constants.EMAIL_PATTERN))
}

fun String.isValidIfbaEmail(): Boolean {
    return this.endsWith(Constants.EMAIL_DOMAIN) && this.contains("@")
}

fun String.isValidPassword(): Boolean {
    return this.length >= Constants.MIN_PASSWORD_LENGTH &&
            this.length <= Constants.MAX_PASSWORD_LENGTH
}

fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}

// Long (timestamp) extensions
fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toFormattedDateTime(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toFormattedTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toRelativeTime(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    return when {
        diff < 60_000 -> "Agora"
        diff < 3600_000 -> "${diff / 60_000} min atrás"
        diff < 86400_000 -> "${diff / 3600_000}h atrás"
        diff < 604800_000 -> "${diff / 86400_000}d atrás"
        else -> this.toFormattedDate()
    }
}

// Boolean extensions
fun Boolean.toInt(): Int = if (this) 1 else 0

// Int extensions
fun Int.toBoolean(): Boolean = this != 0