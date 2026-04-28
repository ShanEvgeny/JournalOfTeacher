package com.example.journalofteacher.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam
import com.example.journalofteacher.domain.usecases.group.CreateGroupUseCase
import com.example.journalofteacher.domain.usecases.group.DeleteGroupUseCase
import com.example.journalofteacher.domain.usecases.group.GetAllGroupsUseCase
import com.example.journalofteacher.domain.usecases.group.GetGroupByIdUseCase
import com.example.journalofteacher.domain.usecases.group.UpdateGroupUseCase

class GroupVM(
    private val createGroupUseCase: CreateGroupUseCase,
    private val updateGroupUseCase: UpdateGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase
): ViewModel() {
    private var groupsMutable = MutableLiveData<List<Group>>(emptyList())
    val groups: LiveData<List<Group>> = groupsMutable
    private var groupToEditMutable = MutableLiveData<Group?>(null)
    val groupToEdit: LiveData<Group?> = groupToEditMutable
    private var errorMessageMutable = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = errorMessageMutable

    init{
        fetchAllGroups()
    }

    fun addGroup(groupTitle: String){
        if (groupTitle.isBlank()){
            errorMessageMutable.value = "Вы не заполнили название группы"
            return
        }
        val param = GroupParam(groupTitle)
        createGroupUseCase.execute(param)
        fetchAllGroups()
    }

    fun updateGroup(groupTitle: String, groupId: Int){
        try {
            if (groupTitle.isBlank()){
                errorMessageMutable.value = "Вы не заполнили название группы"
                return
            }
            val group = Group(groupId, groupTitle)
            updateGroupUseCase.execute(group)
            fetchAllGroups()
        }
        catch (ex: IllegalArgumentException){
            errorMessageMutable.value = "Нельзя обновить несущестующую группу"
        }
    }

    fun fetchAllGroups(){
        groupsMutable.value = getAllGroupsUseCase.execute()
    }

    fun deleteGroup(groupId: Int){
        try{
            deleteGroupUseCase.execute(groupId)
            fetchAllGroups()
        }
        catch (ex: IllegalArgumentException){
            errorMessageMutable.value = "Нельзя удалить несуществующую группу"
        }
    }

    fun changeGroupToEdit(groupId: Int){
        try {
            val group = getGroupByIdUseCase.execute(groupId)
            groupToEditMutable.value = group
        }
        catch (ex: IllegalArgumentException){
            errorMessageMutable.value = "Выбранной группы не существует"
        }
    }

    fun clearGroupToEdit(){
        groupToEditMutable.value = null
    }

    fun clearErrorMessage(){
        errorMessageMutable.value = null
    }
}