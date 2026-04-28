package com.example.journalofteacher.data.repositories

import com.example.journalofteacher.data.storages.StudentStorage
import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.entities.StudentParam
import com.example.journalofteacher.domain.repositories.StudentRepo

class StudentRepoImpl(private val studentStorage: StudentStorage): StudentRepo {
    override fun create(param: StudentParam): Student {
        return studentStorage.create(param)
    }

    override fun update(student: Student): Student {
        return studentStorage.update(student)
    }

    override fun delete(studentId: Int) {
        return studentStorage.delete(studentId)
    }

    override fun getAll(): List<Student> {
        return studentStorage.getAll()
    }

    override fun getById(studentId: Int): Student? {
        return studentStorage.getById(studentId)
    }
}