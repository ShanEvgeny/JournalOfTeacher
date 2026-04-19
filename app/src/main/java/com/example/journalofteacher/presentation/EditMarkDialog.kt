package com.example.journalofteacher.presentation

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.journalofteacher.R
import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.presentation.viewmodels.MarksVM
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.ranges.contains

class EditMarkDialog: DialogFragment() {
    private val marksVM: MarksVM by activityViewModels()
    private lateinit var inputStudentEdit: EditText
    private lateinit var inputSubjectEdit: EditText
    private lateinit var inputDateTimeLessonEdit: EditText
    private lateinit var inputMarkValueEdit: EditText
    private lateinit var markEdit: Mark
    private lateinit var selectedDateTime: LocalDateTime

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_mark, null)
        inputStudentEdit = view.findViewById(R.id.inputStudentEdit)
        inputSubjectEdit = view.findViewById(R.id.inputSubjectEdit)
        inputDateTimeLessonEdit = view.findViewById(R.id.inputDateTimeLessonEdit)
        inputMarkValueEdit = view.findViewById(R.id.inputMarkValueEdit)
        fetchMarkEditData()
        builder.setView(view)
            .setTitle("Редактирование отметки")
            .setPositiveButton("Сохранить", null)
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                updateMark()
            }
        }
        inputDateTimeLessonEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    TimePickerDialog(
                        requireContext(),
                        {_, hourOfDay, minute ->
                            selectedDateTime = LocalDateTime.of(year, month + 1, day, hourOfDay, minute)
                            updateInputDateTimeLesson(selectedDateTime)
                        },
                        hourOfDay, minute, true
                    ).show()
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        return dialog
    }

    private fun fetchMarkEditData(){
        marksVM.markEdit.value?.let { mark ->
            markEdit = mark
            inputStudentEdit.setText(markEdit.student)
            inputSubjectEdit.setText(markEdit.subject)
            selectedDateTime = markEdit.dateTimeLesson
            updateInputDateTimeLesson(markEdit.dateTimeLesson)
            if (markEdit.markValue != null)
                inputMarkValueEdit.setText(markEdit.markValue.toString())
        }
    }

    fun updateMark(){
        val student = inputStudentEdit.text.toString()
        val subject = inputSubjectEdit.text.toString()
        val dateTimeLesson = selectedDateTime
        val markValue = inputMarkValueEdit.text.toString().toIntOrNull()
        if (inputStudentEdit.text.isBlank() ||
            inputSubjectEdit.text.isBlank() ||
            inputDateTimeLessonEdit.text.isBlank()) {
            Toast.makeText(
                requireContext(),
                "Некоторые данные не заполнены",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        else if (markValue !in 2..5 && markValue != null) {
            Toast.makeText(
                requireContext(),
                "Некорректное значение оценки",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        marksVM.updateMark(markEdit, student, subject, dateTimeLesson, markValue)
        dismiss()
    }

    private fun updateInputDateTimeLesson(dateTime: LocalDateTime){
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        inputDateTimeLessonEdit.setText(dateTime.format(formatter))
    }
}