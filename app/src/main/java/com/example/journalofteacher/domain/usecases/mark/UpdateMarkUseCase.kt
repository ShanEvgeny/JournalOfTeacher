package com.example.journalofteacher.domain.usecases.mark

import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.exceptions.BadMarkValueException
import com.example.journalofteacher.domain.exceptions.MarkNotFoundException
import com.example.journalofteacher.domain.exceptions.StudentNotFoundException
import com.example.journalofteacher.domain.repositories.MarkRepo
import com.example.journalofteacher.domain.repositories.StudentRepo
import kotlin.ranges.contains

class UpdateMarkUseCase(
    private val markRepo: MarkRepo,
    private val studentRepo: StudentRepo
) {
    fun execute(mark: Mark): Mark{
        if (studentRepo.getById(mark.studentId) == null)
            throw StudentNotFoundException()
        if (markRepo.getById(mark.id) == null)
            throw MarkNotFoundException()
        if (mark.markValue !in 2..5 && mark.markValue != null)
            throw BadMarkValueException()
        return markRepo.update(mark)

    }
}