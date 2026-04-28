package com.example.journalofteacher.presentation.viewmodels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.journalofteacher.data.repositories.GroupRepoImpl
import com.example.journalofteacher.data.storages.GroupStorage
import com.example.journalofteacher.domain.usecases.group.CreateGroupUseCase
import com.example.journalofteacher.domain.usecases.group.DeleteGroupUseCase
import com.example.journalofteacher.domain.usecases.group.GetAllGroupsUseCase
import com.example.journalofteacher.domain.usecases.group.GetGroupByIdUseCase
import com.example.journalofteacher.domain.usecases.group.UpdateGroupUseCase
import com.example.journalofteacher.presentation.viewmodels.GroupVM

class GroupVMFactory(context: Context): ViewModelProvider.Factory {
    private val groupStorage = GroupStorage(context)
    private val groupRepo = GroupRepoImpl(groupStorage)
    private val createGroupUseCase = CreateGroupUseCase(groupRepo)
    private val updateGroupUseCase = UpdateGroupUseCase(groupRepo)
    private val deleteGroupUseCase = DeleteGroupUseCase(groupRepo)
    private val getAllGroupsUseCase = GetAllGroupsUseCase(groupRepo)
    private val getGroupByIdUseCase = GetGroupByIdUseCase(groupRepo)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroupVM(
            createGroupUseCase,
            updateGroupUseCase,
            deleteGroupUseCase,
            getAllGroupsUseCase,
            getGroupByIdUseCase
        ) as T
    }
}