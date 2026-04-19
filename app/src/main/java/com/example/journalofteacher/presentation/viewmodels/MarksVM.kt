package com.example.journalofteacher.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journalofteacher.domain.entities.Mark
import java.time.LocalDateTime

class MarksVM: ViewModel() {
    private val marksMutable = MutableLiveData<MutableList<Mark>>(mutableListOf())
    val marks: LiveData<MutableList<Mark>> = marksMutable
    private val lastDateTimeLessonMutable = MutableLiveData<LocalDateTime>()
    val lastDateTimeLesson: LiveData<LocalDateTime> = lastDateTimeLessonMutable
    private val markEditMutable = MutableLiveData<Mark>()
    val markEdit: LiveData<Mark> = markEditMutable
    private var lastId = 0

    init {
        addMark("Иванов И И", "ООП", LocalDateTime.of(2026, 3, 25, 13,45), null)
        addMark("Петров А А", "ООП", LocalDateTime.of(2026, 3, 25, 13,45), null)
    }

    fun addMark(student: String,
                subject: String,
                dateTimeLesson: LocalDateTime,
                markValue: Int?){
        val newMark = Mark(
            lastId + 1,
            student,
            subject,
            dateTimeLesson,
            markValue
        )
        updateLastDateTimeLesson(dateTimeLesson)
        val currentList = marksMutable.value
        currentList?.add(0,newMark)
        marksMutable.value = currentList
    }

    fun updateMark(oldMark: Mark,
                   student: String,
                   subject: String,
                   dateTimeLesson: LocalDateTime,
                   markValue: Int?){
        val currentList = marksMutable.value
        val index = currentList.indexOf(oldMark)
        if (index != -1) {
            val updatedItem = Mark(oldMark.id, student, subject, dateTimeLesson, markValue)
            currentList[index] = updatedItem
            marksMutable.value = currentList
        }
    }

    fun changeEditMark(newMarkEdit: Mark){
        markEditMutable.value = newMarkEdit
    }

    fun deleteMark(mark: Mark){
        val currentList = marksMutable.value
        currentList?.remove(mark)
        marksMutable.value = currentList
    }

    private fun updateLastDateTimeLesson(dateTime: LocalDateTime){
        if (lastDateTimeLessonMutable.value == null || dateTime.isAfter(lastDateTimeLessonMutable.value))
            lastDateTimeLessonMutable.value = dateTime
    }
}