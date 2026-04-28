package com.example.journalofteacher.domain.usecases.student

import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.entities.StudentParam
import com.example.journalofteacher.domain.repositories.GroupRepo
import com.example.journalofteacher.domain.repositories.StudentRepo

class UpdateStudentUseCase(private val studentRepo: StudentRepo,
                           private val groupRepo: GroupRepo) {
    fun execute(student: Student): Student{
        if (studentRepo.getById(student.id) == null ||
            groupRepo.getById(student.groupId) == null)
            throw IllegalArgumentException()
        return studentRepo.update(student)
    }
}