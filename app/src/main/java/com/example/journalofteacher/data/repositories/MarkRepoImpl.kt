package com.example.journalofteacher.data.repositories

import com.example.journalofteacher.data.storages.MarkStorage
import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.entities.MarkParam
import com.example.journalofteacher.domain.repositories.MarkRepo

class MarkRepoImpl(private val markStorage: MarkStorage): MarkRepo{
    override fun create(param: MarkParam): Mark {
        return markStorage.create(param)
    }

    override fun update(mark: Mark): Mark {
        return markStorage.update(mark)
    }

    override fun delete(markId: Int) {
        markStorage.delete(markId)
    }

    override fun getAll(): List<Mark> {
        return markStorage.getAll()
    }

    override fun getById(markId: Int): Mark? {
        return markStorage.getById(markId)
    }
}