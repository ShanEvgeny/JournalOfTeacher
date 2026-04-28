package com.example.journalofteacher.domain.repositories

import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam

interface GroupRepo {
    fun create(param: GroupParam): Group

    fun update(group: Group): Group

    fun delete(groupId: Int)

    fun getAll(): List<Group>

    fun getById(groupId: Int): Group?
}