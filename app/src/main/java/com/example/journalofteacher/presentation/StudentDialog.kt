package com.example.journalofteacher.presentation

import android.app.AlertDialog
import android.app.Dialog
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
import com.example.journalofteacher.presentation.viewmodels.StudentVM
import com.example.journalofteacher.presentation.viewmodels.factories.StudentVMFactory

class StudentDialog: DialogFragment() {
    private val studentVM: StudentVM by activityViewModels {
        StudentVMFactory(requireContext())
    }
    private lateinit var inputStudentFullName: EditText
    private lateinit var selectorGroups: Spinner
    private var studentToEditId = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_add_and_edit_student, null)
        inputStudentFullName = view.findViewById(R.id.inputStudentFullName)
        selectorGroups = view.findViewById(R.id.selectorGroups)
        builder.setView(view)
            .setPositiveButton("Сохранить", null)
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
                studentVM.clearStudentToEdit()
                studentVM.clearSelectedGroup()
            }
        if (studentVM.studentToEdit.value != null){
            builder.setTitle("Редактирование студента")
        }
        else{
            builder.setTitle("Добавление студента")
        }
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                saveStudent()
            }
            studentVM.fetchAllGroups()
            setupSelectorGroups()
            if (studentVM.studentToEdit.value != null)
                fetchGroupToEditData()
        }
        return dialog
    }

    private fun setupSelectorGroups() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            studentVM.groups.value.map { it.title }
        )
        if (studentVM.groups.value.isEmpty()){
            selectorGroups.isEnabled = false
            return
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectorGroups.setAdapter(adapter)
        selectorGroups.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                studentVM.selectGroup(studentVM.groups.value[position].id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

    }

    fun saveStudent(){
        val fullName = inputStudentFullName.text.toString()
        if (studentVM.studentToEdit.value != null){
            studentVM.updateStudent(fullName, studentToEditId)
            studentVM.clearStudentToEdit()
            studentVM.clearSelectedGroup()
        }
        else
            studentVM.addStudent(fullName)
        if (studentVM.errorMessage.value != null){
            studentVM.clearErrorMessage()
            return
        }
        dismiss()
    }

    fun fetchGroupToEditData(){
        studentVM.studentToEdit.value?.let { student ->
            studentToEditId = student.id
            inputStudentFullName.setText(student.fullName)

            val groupPosition = studentVM.groups.value?.indexOfLast { it.id == student.groupId } ?: -1
            if (groupPosition != -1){
                selectorGroups.setSelection(groupPosition)
                studentVM.selectGroup(studentVM.groups.value[groupPosition].id)
            }
        }
    }
}