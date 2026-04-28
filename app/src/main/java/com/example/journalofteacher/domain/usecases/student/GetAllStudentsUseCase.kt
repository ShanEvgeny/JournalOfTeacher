package com.example.journalofteacher.domain.usecases.student

import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.entities.StudentParam
import com.example.journalofteacher.domain.repositories.StudentRepo

class GetAllStudentsUseCase (private val studentRepo: StudentRepo) {
    fun execute(): List<Student>{
        return studentRepo.getAll()
    }
}