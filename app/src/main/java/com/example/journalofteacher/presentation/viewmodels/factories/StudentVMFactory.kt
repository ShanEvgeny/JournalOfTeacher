package com.example.journalofteacher.presentation.viewmodels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.journalofteacher.data.repositories.GroupRepoImpl
import com.example.journalofteacher.data.repositories.StudentRepoImpl
import com.example.journalofteacher.data.storages.GroupStorage
import com.example.journalofteacher.data.storages.StudentStorage
import com.example.journalofteacher.domain.usecases.group.GetAllGroupsUseCase
import com.example.journalofteacher.domain.usecases.student.CreateStudentUseCase
import com.example.journalofteacher.domain.usecases.student.DeleteStudentUseCase
import com.example.journalofteacher.domain.usecases.student.GetAllStudentsUseCase
import com.example.journalofteacher.domain.usecases.student.GetStudentByIdUseCase
import com.example.journalofteacher.domain.usecases.student.UpdateStudentUseCase
import com.example.journalofteacher.presentation.viewmodels.StudentVM

class StudentVMFactory(context: Context): ViewModelProvider.Factory {
    private val studentStorage =  StudentStorage(context)
    private val groupStorage = GroupStorage(context)
    private val studentRepo = StudentRepoImpl(studentStorage)
    private val groupRepo = GroupRepoImpl(groupStorage)
    private val createStudentUseCase = CreateStudentUseCase(studentRepo, groupRepo)
    private val updateStudentUseCase = UpdateStudentUseCase(studentRepo, groupRepo)
    private val deleteStudentUseCase = DeleteStudentUseCase(studentRepo)
    private val getAllStudentsUseCase = GetAllStudentsUseCase(studentRepo)
    private val getStudentByIdUseCase = GetStudentByIdUseCase(studentRepo)
    private val getAllGroupsUseCase = GetAllGroupsUseCase(groupRepo)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentVM(
            createStudentUseCase,
            updateStudentUseCase,
            deleteStudentUseCase,
            getAllStudentsUseCase,
            getStudentByIdUseCase,
            getAllGroupsUseCase
        ) as T
    }
}