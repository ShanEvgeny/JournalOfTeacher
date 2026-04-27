package com.example.journalofteacher.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context):
    SQLiteOpenHelper(context, "journal.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS study_groups (" +
                    "id INTEGER PRIMARY KEY, " +
                    "title TEXT UNIQUE)"
        )
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY, " +
                    "full_name TEXT, " +
                    "study_group_id INTEGER," +
                    "CONSTRAINT fk_group FOREIGN KEY (study_group_id) REFERENCES study_groups(id))"
        )
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS marks (" +
                    "id INTEGER PRIMARY KEY, " +
                    "student_id INTEGER, " +
                    "subject TEXT, " +
                    "date_time_lesson TIMESTAMP, " +
                    "mark_value INTEGER," +
                    "CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(id))"
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS journal.db")
        onCreate(db)
    }

}