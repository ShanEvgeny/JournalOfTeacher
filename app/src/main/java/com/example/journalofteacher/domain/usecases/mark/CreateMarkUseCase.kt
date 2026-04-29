package com.example.journalofteacher.domain.usecases.mark

import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.entities.MarkParam
import com.example.journalofteacher.domain.exceptions.BadMarkValueException
import com.example.journalofteacher.domain.exceptions.MarkNotFoundException
import com.example.journalofteacher.domain.repositories.MarkRepo
import com.example.journalofteacher.domain.repositories.StudentRepo
import kotlin.ranges.contains

class CreateMarkUseCase(
    private val markRepo: MarkRepo,
    private val studentRepo: StudentRepo
) {
    fun execute(param: MarkParam): Mark {
        if (studentRepo.getById(param.studentId) == null)
            throw MarkNotFoundException()
        if (param.markValue !in 2..5 && param.markValue != null)
            throw BadMarkValueException()
        return markRepo.create(param)
    }
}