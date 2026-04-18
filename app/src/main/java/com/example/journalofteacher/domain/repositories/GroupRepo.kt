package com.example.journalofteacher.domain.repositories

import com.example.journalofteacher.domain.entities.Group

interface GroupRepo {
    fun create(group: Group): Group

    fun update(group: Group): Group

    fun delete(id: Int): Boolean

    fun getAll(groupId: Int): List<Group>

    fun getById(id: Int): Group
}