package br.com.iftm.edu.nostresswedding.core.utils

/**
 * Formata data String no formato 2034-07-10 para long
 *
 * @param date String no formato 2034-07-10
 * @return Long com a data em milissegundos
 */

fun String.toLongDate(): Long {
    val parts = this.split("-")
    return if (parts.size == 3) {
        val year = parts[0].toInt()
        val month = parts[1].toInt() - 1 // Meses começam do zero
        val day = parts[2].toInt()
        val calendar = java.util.Calendar.getInstance()
        calendar.set(year, month, day, 0, 0, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        calendar.timeInMillis
    } else {
        0L
    }
}