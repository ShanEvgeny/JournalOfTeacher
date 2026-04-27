package com.example.journalofteacher.data.storages

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.journalofteacher.data.MyDBHelper
import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam

class GroupStorage(context: Context) {
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

    fun create(param: GroupParam): Group{
        val db = openWritable()
        val values = ContentValues().apply {
            put("title", param.title)
        }
        val newGroupId = db.insert("study_groups", null, values).toInt()
        val newGroup = Group(newGroupId, param.title)
        close()
        return newGroup
    }

    fun update(param: GroupParam, groupId: Int): Group{
        val db = openWritable()
        val values = ContentValues().apply {
            put("title", param.title)
        }
        val groupId = db.update(
            "study_groups",
            values,
            "id = ?",
            arrayOf(groupId.toString())
        )
        val updatedGroup = Group(groupId, param.title)
        close()
        return updatedGroup
    }

    fun delete(groupId: Int){
        val db = openWritable()
        db.delete("study_groups", "id = ?", arrayOf(groupId.toString()))
    }

    fun getAll(): List<Group>{
        val groups = mutableListOf<Group>()
        val db = openReadable()
        val cursor = db.rawQuery("SELECT * FROM study_groups", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            groups.add(Group(id, title))
            cursor.moveToNext()
        }
        cursor.close()
        close()
        return groups
    }

    fun getById(groupId: Int): Group?{
        var group: Group? = null
        val db = openReadable()
        val cursor = db.rawQuery("SELECT * FROM study_groups WHERE id = ?", arrayOf(groupId.toString()));
        if (cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            group = Group(id, title)
        }
        cursor.close()
        close()
        return group
    }
}