package com.example.journalofteacher.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.GroupParam
import com.example.journalofteacher.domain.usecases.CreateGroupUseCase
import com.example.journalofteacher.domain.usecases.DeleteGroupUseCase
import com.example.journalofteacher.domain.usecases.GetAllGroupsUseCase
import com.example.journalofteacher.domain.usecases.GetGroupByIdUseCase
import com.example.journalofteacher.domain.usecases.UpdateGroupUseCase

class GroupVM(
    val createGroupUseCase: CreateGroupUseCase,
    val updateGroupUseCase: UpdateGroupUseCase,
    val deleteGroupUseCase: DeleteGroupUseCase,
    val getAllGroupsUseCase: GetAllGroupsUseCase,
    val getGroupByIdUseCase: GetGroupByIdUseCase): ViewModel() {
    private var groupsMutable = MutableLiveData<MutableList<Group>>(mutableListOf())
    val groups: LiveData<MutableList<Group>> = groupsMutable
    private var groupToEditMutable = MutableLiveData<Group>()
    val groupToEdit: LiveData<Group> = groupToEditMutable
    private var errorMessageMutable = MutableLiveData<String>()
    val errorMessage: LiveData<String> = errorMessageMutable

    init{
        val currentList = mutableListOf<Group>()
        currentList.addAll(getAllGroupsUseCase.execute())
        groupsMutable.value = currentList
    }

    fun addGroup(groupTitle: String){
        val param = GroupParam(groupTitle)
        createGroupUseCase.execute(param)
        fetchAllGroups()
    }

    fun updateGroup(groupTitle: String, groupId: Int){
        try {
            val param = GroupParam(groupTitle)
            updateGroupUseCase.execute(param, groupId)
            fetchAllGroups()
        }
        catch (ex: IllegalArgumentException){
            errorMessageMutable.value = "Нельзя обновить несущестующую группу"
        }

    }

    fun fetchAllGroups(){
        val currentList = groupsMutable.value
        currentList?.clear()
        currentList?.addAll(getAllGroupsUseCase.execute())
        groupsMutable.value = currentList
    }

    fun deleteGroup(groupId: Int){
        try{
            deleteGroupUseCase.execute(groupId)
            fetchAllGroups()
        }
        catch (ex: IllegalArgumentException){
            errorMessageMutable.value = "Нельзя удалить несущестующую группу"
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