package com.example.journalofteacher.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.entities.StudentParam
import com.example.journalofteacher.domain.usecases.group.GetAllGroupsUseCase
import com.example.journalofteacher.domain.usecases.student.CreateStudentUseCase
import com.example.journalofteacher.domain.usecases.student.DeleteStudentUseCase
import com.example.journalofteacher.domain.usecases.student.GetAllStudentsUseCase
import com.example.journalofteacher.domain.usecases.student.GetStudentByIdUseCase
import com.example.journalofteacher.domain.usecases.student.UpdateStudentUseCase
import kotlinx.coroutines.launch

class StudentVM(
    private val createStudentUseCase: CreateStudentUseCase,
    private val updateStudentUseCase: UpdateStudentUseCase,
    private val deleteStudentUseCase: DeleteStudentUseCase,
    private val getAllStudentsUseCase: GetAllStudentsUseCase,
    private val getStudentByIdUseCase: GetStudentByIdUseCase,
    private val getAllGroupsUseCase: GetAllGroupsUseCase
): ViewModel() {
    private var studentsMutable = MutableLiveData<List<Student>>(emptyList())
    val students: LiveData<List<Student>> = studentsMutable
    private var groupsMutable = MutableLiveData<List<Group>>(emptyList())
    val groups: LiveData<List<Group>> = groupsMutable
    private var selectedGroupIdMutable = MutableLiveData<Int?>()
    val selectedGroupId: LiveData<Int?> = selectedGroupIdMutable
    private var studentToEditMutable = MutableLiveData<Student?>(null)
    val studentToEdit: LiveData<Student?> = studentToEditMutable
    private var errorMessageMutable = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = errorMessageMutable

    init{
        fetchAllGroups()
        fetchAllStudents()
    }

    fun fetchAllStudents(){
        studentsMutable.value = getAllStudentsUseCase.execute()
    }

    fun addStudent(fullName: String){
        try{
            val groupId = selectedGroupId.value
            if (groupId == null){
                errorMessageMutable.value = "Вы не выбрали группу"
                return
            }
            if (fullName.isBlank()){
                errorMessageMutable.value = "Вы не вписали полное имя студента"
                return
            }
            val param = StudentParam(fullName, groupId)
            createStudentUseCase.execute(param)
            fetchAllStudents()
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = "Заданной группы не существует"
        }
    }

    fun updateStudent(fullName: String, studentId: Int){
        try{
            val groupId = selectedGroupId.value
            if (groupId == null){
                errorMessageMutable.value = "Вы не выбрали группу"
                return
            }
            if (fullName.isBlank()){
                errorMessageMutable.value = "Вы не вписали полное имя студента"
                return
            }
            val student = Student(studentId, fullName, groupId)
            updateStudentUseCase.execute(student)
            fetchAllStudents()
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = "Некорректно введенные данные"
        }
    }

    fun deleteStudent(studentId: Int){
        try{
            deleteStudentUseCase.execute(studentId)
            fetchAllStudents()
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = "Такого студента не существует"
        }
    }

    fun changeStudentToEdit(studentId: Int){
        try{
            val student = getStudentByIdUseCase.execute(studentId)
            studentToEditMutable.value = student
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = "Такого студента не существует"
        }
    }

    fun clearStudentToEdit(){
        studentToEditMutable.value = null
    }

    fun fetchAllGroups(){
        groupsMutable.value = getAllGroupsUseCase.execute()
    }

    fun selectGroup(groupId: Int){
        selectedGroupIdMutable.value = groupId
    }

    fun clearSelectedGroup(){
        selectedGroupIdMutable.value = null
    }

    fun clearErrorMessage(){
        errorMessageMutable.value = null
    }
}