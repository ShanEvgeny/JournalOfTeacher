package com.example.journalofteacher.domain.usecases.student

import com.example.journalofteacher.domain.entities.StudentParam
import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.repositories.GroupRepo
import com.example.journalofteacher.domain.repositories.StudentRepo

class CreateStudentUseCase(private val studentRepo: StudentRepo,
                           private val groupRepo: GroupRepo) {
    fun execute(param: StudentParam): Student{
        if (groupRepo.getById(param.groupId) == null)
            throw IllegalArgumentException()
        return studentRepo.create(param)
    }
}