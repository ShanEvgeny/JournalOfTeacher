package com.example.journalofteacher.data.storages

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getIntOrNull
import com.example.journalofteacher.data.MyDBHelper
import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.entities.MarkParam
import java.time.LocalDateTime

class MarkStorage(context: Context) {
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

    fun create(param: MarkParam): Mark {
        val db = openWritable()
        val values = ContentValues().apply {
            put("student_id", param.studentId)
            put("subject", param.subject)
            put("date_time_lesson", param.dateTimeLesson.toString())
            put("mark_value", param.markValue)
        }
        val newMarkId = db.insert("marks", null, values).toInt()
        val newMark = Mark(newMarkId, param.studentId, param.subject, param.dateTimeLesson, param.markValue)
        close()
        return newMark
    }

    fun update(mark: Mark): Mark {
        val db = openWritable()
        val values = ContentValues().apply {
            put("student_id", mark.studentId)
            put("subject", mark.subject)
            put("date_time_lesson", mark.dateTimeLesson.toString())
            put("mark_value", mark.markValue)
        }
        db.update(
            "marks",
            values,
            "id = ?",
            arrayOf(mark.id.toString())
        )
        close()
        return mark
    }

    fun delete(markId: Int) {
        val db = openWritable()
        db.delete("marks", "id = ?", arrayOf(markId.toString()))
    }

    fun getAll(): List<Mark> {
        val marks = mutableListOf<Mark>()
        val db = openReadable()
        val cursor = db.rawQuery("SELECT * FROM marks", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val studentId = cursor.getInt(cursor.getColumnIndexOrThrow("student_id"))
            val subject = cursor.getString(cursor.getColumnIndexOrThrow("subject"))
            val dateTimeLesson = LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("date_time_lesson")))
            val markValue = cursor.getIntOrNull(cursor.getColumnIndexOrThrow("mark_value"))
            marks.add(Mark(id, studentId, subject, dateTimeLesson, markValue))
            cursor.moveToNext()
        }
        cursor.close()
        close()
        return marks
    }

    fun getById(markId: Int): Mark? {
        var mark: Mark? = null
        val db = openReadable()
        val cursor = db.rawQuery("SELECT * FROM marks WHERE id = ?", arrayOf(markId.toString()))
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val studentId = cursor.getInt(cursor.getColumnIndexOrThrow("student_id"))
            val subject = cursor.getString(cursor.getColumnIndexOrThrow("subject"))
            val dateTimeLesson = LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow("date_time_lesson")))
            val markValue = cursor.getIntOrNull(cursor.getColumnIndexOrThrow("mark_value"))
            mark = Mark(id, studentId, subject, dateTimeLesson, markValue)
        }
        cursor.close()
        close()
        return mark
    }
}