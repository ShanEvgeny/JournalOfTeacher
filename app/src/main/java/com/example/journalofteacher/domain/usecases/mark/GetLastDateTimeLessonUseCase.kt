package com.example.journalofteacher.domain.usecases.mark

import com.example.journalofteacher.domain.repositories.MarkRepo
import java.time.LocalDateTime

class GetLastDateTimeLessonUseCase(private val markRepo: MarkRepo) {
    fun execute(): LocalDateTime?{
        return markRepo.getAll().maxOfOrNull { it.dateTimeLesson }
    }
}