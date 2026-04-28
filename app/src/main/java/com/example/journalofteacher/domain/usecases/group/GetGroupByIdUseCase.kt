package com.example.journalofteacher.domain.usecases.group

import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.repositories.GroupRepo

class GetGroupByIdUseCase(private val groupRepo: GroupRepo) {
    fun execute(groupId: Int): Group {
        val group = groupRepo.getById(groupId) ?: throw IllegalArgumentException()
        return group
    }
}