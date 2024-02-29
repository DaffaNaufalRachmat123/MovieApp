package com.movie.common.extension

import java.text.SimpleDateFormat
import java.util.Locale

fun String?.formatDate(originalFormat: String = "yyyy-MM-dd", destFormat: String = "dd MMMM yyyy"): String {
    if (this.isNullOrEmpty()) return ""
    val parser = SimpleDateFormat(originalFormat, Locale.ROOT)
    val formatter = SimpleDateFormat(destFormat, Locale.ROOT)
    val parse = parser.parse(this) ?: return "1970-01-01"
    return formatter.format(parse)
}
