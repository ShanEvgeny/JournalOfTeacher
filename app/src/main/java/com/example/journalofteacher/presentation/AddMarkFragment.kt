package com.example.journalofteacher.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.journalofteacher.R
import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.presentation.viewmodels.MarksVM
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.getValue

class AddMarkFragment: Fragment() {
    private val marksVM: MarksVM by activityViewModels()
    private lateinit var selectorStudents: Spinner
    private lateinit var inputSubject: EditText
    private lateinit var inputDateTimeLesson: EditText
    private lateinit var inputMarkValue: EditText
    private lateinit var buttonAddMark: Button
    private lateinit var buttonCancelAddMark: Button
    private lateinit var selectedDataTime: LocalDateTime

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
        selectorStudents = view.findViewById(R.id.selectorStudents)
        inputSubject = view.findViewById(R.id.inputSubject)
        inputDateTimeLesson = view.findViewById(R.id.inputDateTimeLesson)
        inputMarkValue = view.findViewById(R.id.inputMarkValue)
        buttonAddMark = view.findViewById(R.id.buttonAddMark)
        buttonCancelAddMark = view.findViewById(R.id.buttonCancelAddMark)
        textView.text = "Это фрагмент для добавления оценки"
        updateInputDateTimeLesson(marksVM.lastDateTimeLesson.value)
        setupSelectorGroups()
        buttonAddMark.setOnClickListener {
            addMark()
        }
        buttonCancelAddMark.setOnClickListener {
            findNavController().navigate(R.id.action_back_to_list)
        }
        inputDateTimeLesson.setOnClickListener {
            showDateDialog()
        }
        marksVM.lastDateTimeLesson.observe(viewLifecycleOwner) { dateTime ->
            updateInputDateTimeLesson(dateTime)
        }
        marksVM.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null){
                Toast.makeText(
                    requireContext(),
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        marksVM.fetchStudents()
        marksVM.fetchLastDateTimeLesson()
        setupSelectorGroups()
    }

    private fun setupSelectorGroups() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            marksVM.students.value.map { it.fullName }
        )
        if (marksVM.students.value.isEmpty()){
            selectorStudents.isEnabled = false
            return
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectorStudents.setAdapter(adapter)
        selectorStudents.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                        updateInputDateTimeLesson(
                            LocalDateTime.of(year, month + 1, day, hourOfDay, minute)
                        )
                    },
                    hourOfDay, minute, true
                ).show()
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun addMark(){
        val subject = inputSubject.text.toString()
        val dateTimeLesson = selectedDataTime
        val markValue = inputMarkValue.text.toString().toIntOrNull()
        marksVM.addMark(subject, dateTimeLesson, markValue)
        if (marksVM.errorMessage.value != null) {
            marksVM.clearErrorMessage()
            return
        }
        marksVM.clearSelectedStudent()
        findNavController().navigate(R.id.action_back_to_list)

    }

    private fun updateInputDateTimeLesson(dateTime: LocalDateTime?){
        selectedDataTime = dateTime ?: LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        inputDateTimeLesson.setText(selectedDataTime.format(formatter))
    }
}
