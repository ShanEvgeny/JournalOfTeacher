package com.example.journalofteacher.data.repositories

import com.example.journalofteacher.data.storages.GroupStorage
import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam
import com.example.journalofteacher.domain.repositories.GroupRepo

class GroupRepoImpl(private val groupStorage: GroupStorage): GroupRepo {
    override fun create(param: GroupParam): Group {
        return groupStorage.create(param)
    }

    override fun update(
        param: GroupParam,
        groupId: Int
    ): Group {
        return groupStorage.update(param, groupId)
    }

    override fun delete(groupId: Int) {
        return groupStorage.delete(groupId)
    }

    override fun getAll(): List<Group> {
        return groupStorage.getAll()
    }

    override fun getById(groupId: Int): Group? {
        return groupStorage.getById(groupId)
    }

}