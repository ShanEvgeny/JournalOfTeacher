package com.example.journalofteacher.domain.repositories

import com.example.journalofteacher.domain.entities.Mark

interface MarkRepo {
    fun create(mark: Mark): Mark

    fun update(mark: Mark): Mark

    fun delete(markId: Int): Boolean

    fun getAll(): List<Mark>

    fun getById(markId: Int): Mark
}