package com.fahimeh.rideready.core.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dayFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)
private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)

fun LocalDate.toDayLabel(): String = format(dayFormatter)
fun LocalDate.toDateLabel(): String = format(dateFormatter)