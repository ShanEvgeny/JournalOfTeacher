package com.example.journalofteacher.domain.usecases.mark

import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.repositories.MarkRepo

class GetAllMarksUseCase(private val markRepo: MarkRepo) {
    fun execute(): List<Mark>{
        return markRepo.getAll()
    }
}