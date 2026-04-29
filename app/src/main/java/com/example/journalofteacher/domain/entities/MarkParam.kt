package com.example.journalofteacher.domain.entities

import java.time.LocalDateTime

data class MarkParam(
    val studentId: Int,
    val subject: String,
    val dateTimeLesson: LocalDateTime,
    val markValue: Int?
)
