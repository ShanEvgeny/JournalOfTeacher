package com.example.journalofteacher.domain.usecases.student

import com.example.journalofteacher.domain.repositories.StudentRepo

class DeleteStudentUseCase(private val studentRepo: StudentRepo) {
    fun execute(studentId: Int) {
        if (studentRepo.getById(studentId) == null)
            throw IllegalArgumentException()
        studentRepo.delete(studentId)
    }
}