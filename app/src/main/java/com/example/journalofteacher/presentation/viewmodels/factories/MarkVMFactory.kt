package com.example.journalofteacher.presentation.viewmodels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.journalofteacher.data.repositories.MarkRepoImpl
import com.example.journalofteacher.data.repositories.StudentRepoImpl
import com.example.journalofteacher.data.storages.MarkStorage
import com.example.journalofteacher.data.storages.StudentStorage
import com.example.journalofteacher.domain.usecases.mark.CreateMarkUseCase
import com.example.journalofteacher.domain.usecases.mark.DeleteMarkUseCase
import com.example.journalofteacher.domain.usecases.mark.GetAllMarksUseCase
import com.example.journalofteacher.domain.usecases.mark.GetLastDateTimeLessonUseCase
import com.example.journalofteacher.domain.usecases.mark.GetMarkByIdUseCase
import com.example.journalofteacher.domain.usecases.mark.UpdateMarkUseCase
import com.example.journalofteacher.domain.usecases.student.GetAllStudentsUseCase
import com.example.journalofteacher.presentation.viewmodels.MarksVM

class MarkVMFactory(context: Context): ViewModelProvider.Factory {
    private val markStorage = MarkStorage(context)
    private val studentStorage = StudentStorage(context)
    private val markRepo = MarkRepoImpl(markStorage)
    private val studentRepo = StudentRepoImpl(studentStorage)
    private val createMarkUseCase = CreateMarkUseCase(markRepo, studentRepo)
    private val updateMarkUseCase = UpdateMarkUseCase(markRepo, studentRepo)
    private val deleteMarkUseCase = DeleteMarkUseCase(markRepo)
    private val getAllMarksUseCase = GetAllMarksUseCase(markRepo)
    private val getMarkByIdUseCase = GetMarkByIdUseCase(markRepo)
    private val getAllStudentsUseCase = GetAllStudentsUseCase(studentRepo)

    private val getLastDateTimeLessonUseCase = GetLastDateTimeLessonUseCase(markRepo)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarksVM(
            createMarkUseCase,
            updateMarkUseCase,
            deleteMarkUseCase,
            getAllMarksUseCase,
            getMarkByIdUseCase,
            getAllStudentsUseCase,
            getLastDateTimeLessonUseCase
        ) as T
    }
}