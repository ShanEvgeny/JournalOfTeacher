package com.example.journalofteacher.domain.usecases

import com.example.journalofteacher.domain.repositories.GroupRepo

class DeleteGroupUseCase(private val groupRepo: GroupRepo) {
    fun execute(groupId: Int){
        if (groupRepo.getById(groupId) == null)
            throw IllegalArgumentException()
        return groupRepo.delete(groupId)
    }
}