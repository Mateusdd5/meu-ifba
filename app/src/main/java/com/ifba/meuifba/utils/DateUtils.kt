package com.ifba.meuifba.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DATE_FORMAT = "dd/MM/yyyy"
    private const val TIME_FORMAT = "HH:mm"
    private const val DATETIME_FORMAT = "dd/MM/yyyy HH:mm"

    // Obter timestamp atual
    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis()
    }

    // Converter String para timestamp
    fun stringToTimestamp(dateString: String, format: String = DATE_FORMAT): Long? {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.parse(dateString)?.time
        } catch (e: Exception) {
            null
        }
    }

    // Converter timestamp para String
    fun timestampToString(timestamp: Long, format: String = DATE_FORMAT): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    // Verificar se data é futura
    fun isFutureDate(timestamp: Long): Boolean {
        return timestamp > getCurrentTimestamp()
    }

    // Verificar se data é passada
    fun isPastDate(timestamp: Long): Boolean {
        return timestamp < getCurrentTimestamp()
    }

    // Verificar se data é hoje
    fun isToday(timestamp: Long): Boolean {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_YEAR)

        calendar.timeInMillis = timestamp
        val eventDay = calendar.get(Calendar.DAY_OF_YEAR)

        return today == eventDay &&
                calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
    }

    // Obter início do dia (00:00:00)
    fun getStartOfDay(timestamp: Long = getCurrentTimestamp()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    // Obter fim do dia (23:59:59)
    fun getEndOfDay(timestamp: Long = getCurrentTimestamp()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    // Adicionar dias a uma data
    fun addDays(timestamp: Long, days: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.timeInMillis
    }

    // Diferença em dias entre duas datas
    fun daysBetween(start: Long, end: Long): Int {
        val diff = end - start
        return (diff / (1000 * 60 * 60 * 24)).toInt()
    }

    // Formatar duração de evento
    fun formatEventDuration(inicio: String, fim: String): String {
        return "$inicio - $fim"
    }

    // Obter dia da semana
    fun getDayOfWeek(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Domingo"
            Calendar.MONDAY -> "Segunda-feira"
            Calendar.TUESDAY -> "Terça-feira"
            Calendar.WEDNESDAY -> "Quarta-feira"
            Calendar.THURSDAY -> "Quinta-feira"
            Calendar.FRIDAY -> "Sexta-feira"
            Calendar.SATURDAY -> "Sábado"
            else -> ""
        }
    }
}