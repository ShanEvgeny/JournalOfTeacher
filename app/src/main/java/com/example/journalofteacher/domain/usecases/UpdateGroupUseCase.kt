package com.example.journalofteacher.domain.usecases

import android.content.res.Resources
import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam
import com.example.journalofteacher.domain.repositories.GroupRepo

class UpdateGroupUseCase(private val groupRepo: GroupRepo) {
    fun execute(param: GroupParam, groupId: Int): Group {
        if (groupRepo.getById(groupId) == null)
            throw IllegalArgumentException()
        return groupRepo.update(param, groupId)
    }
}