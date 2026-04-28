package com.example.journalofteacher.domain.repositories

import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.entities.StudentParam

interface StudentRepo {
    fun create(param: StudentParam): Student

    fun update(student: Student): Student

    fun delete(studentId: Int)

    fun getAll(): List<Student>

    fun getById(studentId: Int): Student?
}