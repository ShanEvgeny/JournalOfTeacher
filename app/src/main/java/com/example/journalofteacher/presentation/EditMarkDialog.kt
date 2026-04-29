package com.example.journalofteacher.presentation

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.journalofteacher.R
import com.example.journalofteacher.presentation.viewmodels.MarksVM
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class EditMarkDialog: DialogFragment() {
    private val marksVM: MarksVM by activityViewModels()
    private lateinit var selectorStudentsEdit: Spinner
    private lateinit var inputSubjectEdit: EditText
    private lateinit var inputDateTimeLessonEdit: EditText
    private lateinit var inputMarkValueEdit: EditText
    private var markEditId = 0
    private lateinit var selectedDateTime: LocalDateTime

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_edit_mark, null)
        selectorStudentsEdit = view.findViewById(R.id.selectorStudentsEdit)
        inputSubjectEdit = view.findViewById(R.id.inputSubjectEdit)
        inputDateTimeLessonEdit = view.findViewById(R.id.inputDateTimeLessonEdit)
        inputMarkValueEdit = view.findViewById(R.id.inputMarkValueEdit)
        builder.setView(view)
            .setTitle("Редактирование отметки")
            .setPositiveButton("Сохранить", null)
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                updateMark()

            }
            marksVM.fetchStudents()
            setupSelectorGroups()
            fetchMarkEditData()
        }
        inputDateTimeLessonEdit.setOnClickListener {
            showDateDialog()
        }

        return dialog
    }

    private fun setupSelectorGroups() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            marksVM.students.value.map { it.fullName }
        )
        if (marksVM.students.value.isEmpty()){
            selectorStudentsEdit.isEnabled = false
            return
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectorStudentsEdit.setAdapter(adapter)
        selectorStudentsEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                marksVM.selectStudent(marksVM.students.value[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    private fun showDateDialog(){
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

    private fun fetchMarkEditData(){
        marksVM.markToEdit.value?.let { mark ->
            markEditId = mark.id
            inputSubjectEdit.setText(mark.subject)
            selectedDateTime = mark.dateTimeLesson
            updateInputDateTimeLesson(mark.dateTimeLesson)
            if (mark.markValue != null)
                inputMarkValueEdit.setText(mark.markValue.toString())
            val studentPosition = marksVM.students.value?.indexOfLast { it.id == mark.studentId } ?: -1
            if (studentPosition != -1){
                selectorStudentsEdit.setSelection(studentPosition)
                marksVM.selectStudent(marksVM.students.value[studentPosition].id)
            }
        }
    }

    fun updateMark(){
        val subject = inputSubjectEdit.text.toString()
        val dateTimeLesson = selectedDateTime
        val markValue = inputMarkValueEdit.text.toString().toIntOrNull()
        marksVM.updateMark(markEditId,  subject, dateTimeLesson, markValue)
        marksVM.clearMarkToEdit()
        marksVM.clearSelectedStudent()
        if (marksVM.errorMessage.value != null){
            marksVM.clearErrorMessage()
            return
        }
        dismiss()
    }

    private fun updateInputDateTimeLesson(dateTime: LocalDateTime){
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        inputDateTimeLessonEdit.setText(dateTime.format(formatter))
    }
}