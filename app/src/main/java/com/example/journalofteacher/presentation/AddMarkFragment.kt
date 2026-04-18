package com.example.journalofteacher.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.journalofteacher.R
import com.example.journalofteacher.presentation.viewmodels.MarksVM
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.getValue

class AddMarkFragment: Fragment() {
    private val marksVM: MarksVM by activityViewModels()
    private lateinit var inputStudent: EditText
    private lateinit var inputSubject: EditText
    private lateinit var inputDateTimeLesson: EditText
    private lateinit var inputMarkValue: EditText
    private lateinit var buttonAddMark: Button
    private lateinit var buttonCancelAddMark: Button
    private lateinit var selectedDateTime: LocalDateTime

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_mark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.textViewAddMark)
        inputStudent = view.findViewById(R.id.inputStudent)
        inputSubject = view.findViewById(R.id.inputSubject)
        inputDateTimeLesson = view.findViewById(R.id.inputDateTimeLesson)
        inputMarkValue = view.findViewById(R.id.inputMarkValue)
        buttonAddMark = view.findViewById(R.id.buttonAddMark)
        buttonCancelAddMark = view.findViewById(R.id.buttonCancelAddMark)
        textView.text = "Это фрагмент для добавления оценки"
        fetchLastDateTimeLesson()
        buttonAddMark.setOnClickListener {
            addMark()
        }
        buttonCancelAddMark.setOnClickListener {
            findNavController().navigate(R.id.action_back_to_list)
        }
        inputDateTimeLesson.setOnClickListener {
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
    }

    private fun addMark(){
        val student = inputStudent.text.toString()
        val subject = inputSubject.text.toString()
        val dateTimeLesson = selectedDateTime
        val markValue = inputMarkValue.text.toString().toIntOrNull()
        if (inputStudent.text.isBlank() ||
            inputSubject.text.isBlank() ||
            inputDateTimeLesson.text.isBlank()) {
            Toast.makeText(
                requireContext(),
                "Некоторые данные не заполнены",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        else if (markValue !in 2..5) {
            Toast.makeText(
                requireContext(),
                "Некорректное значение оценки",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        marksVM.addMark(student, subject, dateTimeLesson, markValue)
        findNavController().navigate(R.id.action_back_to_list)
    }

    private fun fetchLastDateTimeLesson(){
        marksVM.lastDateTimeLesson.value?.let { dateTime ->
            selectedDateTime = dateTime
            updateInputDateTimeLesson(selectedDateTime)
        }
    }

    private fun updateInputDateTimeLesson(dateTime: LocalDateTime){
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        inputDateTimeLesson.setText(dateTime.format(formatter))
    }
}
