package com.example.journalofteacher.domain.repositories

import com.example.journalofteacher.domain.entities.Student

interface StudentRepo {
    fun create(student: Student): Student

    fun update(student: Student): Student

    fun delete(studentId: Int): Boolean

    fun getAllByGroup(groupId: Int): List<Student>

    fun getAll(): List<Student>

    fun getById(studentId: Int): Student
}