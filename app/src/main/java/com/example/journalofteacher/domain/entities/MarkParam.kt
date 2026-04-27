package com.example.journalofteacher.domain.entities

import java.time.LocalDateTime

data class MarkParam(
    val student: String,
    val subject: String,
    val dateTimeLesson: LocalDateTime,
    val markValue: Int?
)
