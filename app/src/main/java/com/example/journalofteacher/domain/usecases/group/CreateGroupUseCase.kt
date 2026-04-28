package com.example.journalofteacher.domain.usecases.group

import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam
import com.example.journalofteacher.domain.repositories.GroupRepo

class CreateGroupUseCase(private val groupRepo: GroupRepo) {
    fun execute(param: GroupParam): Group {
        return groupRepo.create(param)
    }
}