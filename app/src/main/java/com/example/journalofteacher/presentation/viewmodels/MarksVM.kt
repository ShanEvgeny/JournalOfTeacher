package com.example.journalofteacher.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.domain.entities.MarkParam
import com.example.journalofteacher.domain.entities.Student
import com.example.journalofteacher.domain.usecases.mark.CreateMarkUseCase
import com.example.journalofteacher.domain.usecases.mark.DeleteMarkUseCase
import com.example.journalofteacher.domain.usecases.mark.GetAllMarksUseCase
import com.example.journalofteacher.domain.usecases.mark.GetLastDateTimeLessonUseCase
import com.example.journalofteacher.domain.usecases.mark.GetMarkByIdUseCase
import com.example.journalofteacher.domain.usecases.mark.UpdateMarkUseCase
import com.example.journalofteacher.domain.usecases.student.GetAllStudentsUseCase
import java.time.LocalDateTime

class MarksVM(
    private val createMarkUseCase: CreateMarkUseCase,
    private val updateMarkUseCase: UpdateMarkUseCase,
    private val deleteMarkUseCase: DeleteMarkUseCase,
    private val getAllMarksUseCase: GetAllMarksUseCase,
    private val getMarkByIdUseCase: GetMarkByIdUseCase,
    private val getAllStudentsUseCase: GetAllStudentsUseCase,
    private val getLastDateTimeLessonUseCase: GetLastDateTimeLessonUseCase
): ViewModel() {
    private var marksMutable = MutableLiveData<List<Mark>>(emptyList())
    val marks: LiveData<List<Mark>> = marksMutable
    private var studentsMutable = MutableLiveData<List<Student>>(emptyList())
    val students: LiveData<List<Student>> = studentsMutable
    private val lastDateTimeLessonMutable = MutableLiveData<LocalDateTime>()
    val lastDateTimeLesson: LiveData<LocalDateTime> = lastDateTimeLessonMutable
    private val markToEditMutable = MutableLiveData<Mark?>()
    val markToEdit: LiveData<Mark?> = markToEditMutable
    private var selectedStudentIdMutable = MutableLiveData<Int?>()
    val selectedStudentId: LiveData<Int?> = selectedStudentIdMutable
    private var errorMessageMutable = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = errorMessageMutable

    init {
        fetchStudents()
        fetchMarks()
        fetchLastDateTimeLesson()
    }

    fun fetchMarks(){
        marksMutable.value = getAllMarksUseCase.execute()
    }

    fun fetchStudents(){
        studentsMutable.value = getAllStudentsUseCase.execute()
    }

    fun fetchLastDateTimeLesson(){
        lastDateTimeLessonMutable.value = getLastDateTimeLessonUseCase.execute()
    }

    fun addMark(subject: String,
                dateTimeLesson: LocalDateTime,
                markValue: Int?){
        try{
            val studentId = selectedStudentId.value
            if (studentId == null){
                errorMessageMutable.value = "Вы не выбрали студента"
                return
            }
            if (subject.isBlank()){
                errorMessageMutable.value = "Вы не вписали название предмета"
                return
            }
            val param = MarkParam(studentId, subject, dateTimeLesson, markValue)
            createMarkUseCase.execute(param)
            fetchMarks()
            fetchLastDateTimeLesson()
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = ex.message
        }

    }

    fun updateMark(markId: Int,
                   subject: String,
                   dateTimeLesson: LocalDateTime,
                   markValue: Int?){
        try{
            val studentId = selectedStudentId.value
            if (studentId == null){
                errorMessageMutable.value = "Вы не выбрали студента"
                return
            }
            if (subject.isBlank()){
                errorMessageMutable.value = "Вы не вписали название предмета"
                return
            }
            val mark = Mark(markId, studentId, subject, dateTimeLesson, markValue)
            updateMarkUseCase.execute(mark)
            fetchMarks()
            fetchLastDateTimeLesson()
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = ex.message
        }
    }


    fun changeMarkToEdit(markId: Int){
        try{
            val mark = getMarkByIdUseCase.execute(markId)
            markToEditMutable.value = mark
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = "Такой отметки не существует"
        }
    }

    fun deleteMark(markId: Int){
        try{
            deleteMarkUseCase.execute(markId)
            fetchMarks()
            fetchLastDateTimeLesson()
        }
        catch(ex: IllegalArgumentException){
            errorMessageMutable.value = "Такой отметки не существует"
        }
    }

    fun clearMarkToEdit(){
        markToEditMutable.value = null
    }

    fun selectStudent(studentId: Int){
        selectedStudentIdMutable.value = studentId
    }

    fun clearSelectedStudent(){
        selectedStudentIdMutable.value = null
    }

    fun clearErrorMessage(){
        errorMessageMutable.value = null
    }
}