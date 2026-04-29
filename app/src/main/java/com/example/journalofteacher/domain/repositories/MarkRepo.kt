package com.example.journalofteacher.domain.repositories

import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.entities.MarkParam

interface MarkRepo {
    fun create(param: MarkParam): Mark

    fun update(mark: Mark): Mark

    fun delete(markId: Int)

    fun getAll(): List<Mark>

    fun getById(markId: Int): Mark?
}