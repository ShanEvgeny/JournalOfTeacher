package com.example.journalofteacher.domain.usecases.student

import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.repositories.StudentRepo

class GetStudentByIdUseCase(private val studentRepo: StudentRepo) {
    fun execute(studentId: Int): Student{
        val student = studentRepo.getById(studentId) ?: throw IllegalArgumentException()
        return student
    }
}