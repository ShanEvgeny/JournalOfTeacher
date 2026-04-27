package com.example.journalofteacher.domain.usecases

import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.repositories.GroupRepo

class GetAllGroupsUseCase(private val groupRepo: GroupRepo) {
    fun execute(): List<Group> {
        return groupRepo.getAll()
    }
}