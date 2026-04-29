package com.example.journalofteacher.domain.usecases.mark

import com.example.journalofteacher.domain.repositories.MarkRepo

class DeleteMarkUseCase (private val markRepo: MarkRepo) {
    fun execute(markId: Int) {
        if (markRepo.getById(markId) == null)
            throw IllegalArgumentException()
        markRepo.delete(markId)
    }
}