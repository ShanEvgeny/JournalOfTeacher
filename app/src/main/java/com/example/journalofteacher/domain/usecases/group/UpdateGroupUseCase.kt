package com.example.journalofteacher.domain.usecases.group

import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam
import com.example.journalofteacher.domain.repositories.GroupRepo

class UpdateGroupUseCase(private val groupRepo: GroupRepo) {
    fun execute(group: Group): Group {
        if (groupRepo.getById(group.id) == null)
            throw IllegalArgumentException()
        return groupRepo.update(group)
    }
}