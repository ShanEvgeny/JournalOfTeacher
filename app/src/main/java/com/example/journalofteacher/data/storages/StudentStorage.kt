package com.example.journalofteacher.data.storages

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.journalofteacher.data.MyDBHelper
import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.entities.StudentParam

class StudentStorage(context: Context) {
    private val myDBHelper = MyDBHelper(context)
    private lateinit var database: SQLiteDatabase

    private fun openWritable(): SQLiteDatabase {
        database = myDBHelper.writableDatabase
        return database
    }

    private fun openReadable(): SQLiteDatabase {
        database = myDBHelper.readableDatabase
        return database
    }

    private fun close() {
        database.close()
    }

    fun create(param: StudentParam): Student {
        val db = openWritable()
        val values = ContentValues().apply {
            put("full_name", param.fullName)
            put("study_group_id", param.groupId)
        }
        val newStudentId = db.insert("students", null, values).toInt()
        val newStudent = Student(newStudentId, param.fullName, param.groupId)
        close()
        return newStudent
    }

    fun update(student: Student): Student{
        val db = openWritable()
        val values = ContentValues().apply {
            put("full_name", student.fullName)
            put("study_group_id", student.groupId)
        }
        val groupId = db.update(
            "students",
            values,
            "id = ?",
            arrayOf(student.id.toString())
        )
        close()
        return student
    }

    fun delete(studentId: Int){
        val db = openWritable()
        db.delete("students", "id = ?", arrayOf(studentId.toString()))
    }

    fun getAll(): List<Student>{
        val students = mutableListOf<Student>()
        val db = openReadable()
        val cursor = db.rawQuery("SELECT * FROM students", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"))
            val groupId = cursor.getString(
                cursor.getColumnIndexOrThrow("study_group_id")
            ).toInt()
            students.add(Student(id, fullName, groupId))
            cursor.moveToNext()
        }
        cursor.close()
        close()
        return students
    }

    fun getById(studentId: Int): Student?{
        var student: Student? = null
        val db = openReadable()
        val cursor = db.rawQuery("SELECT * FROM students WHERE id = ?", arrayOf(studentId.toString()));
        if (cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"))
            val groupId = cursor.getString(
                cursor.getColumnIndexOrThrow("study_group_id")
            ).toInt()
            student = Student(id, fullName, groupId)
        }
        cursor.close()
        close()
        return student
    }
}