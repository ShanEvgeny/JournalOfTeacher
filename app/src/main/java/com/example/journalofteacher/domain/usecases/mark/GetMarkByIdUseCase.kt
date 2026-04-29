package com.example.journalofteacher.domain.usecases.mark

import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.repositories.MarkRepo
import java.lang.IllegalArgumentException

class GetMarkByIdUseCase(private val markRepo: MarkRepo) {
    fun execute(markId: Int): Mark {
        val mark = markRepo.getById(markId) ?: throw IllegalArgumentException()
        return mark
    }
}