package com.example.journalofteacher.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journalofteacher.R
import com.example.journalofteacher.presentation.adapters.StudentAdapter
import com.example.journalofteacher.presentation.viewmodels.StudentVM
import com.example.journalofteacher.presentation.viewmodels.factories.StudentVMFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListStudentsFragment: Fragment() {
    private val studentVM: StudentVM by activityViewModels {
        StudentVMFactory(requireContext())
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var buttonToAddStudent: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_students, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonToAddStudent = view.findViewById(R.id.buttonToAddStudent)
        recyclerView = view.findViewById(R.id.recyclerViewStudents)
        adapter = StudentAdapter(
            emptyList(),
            studentVM.groups.value,
            onDeleteClick = { student -> showStudentDeleteDialog(student.id) },
            onEditClick = {student -> showStudentEditDialog(student.id)}
        )
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            3,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapter
        studentVM.students.observe(viewLifecycleOwner){ students ->
            adapter.updateStudents(students)
        }
        studentVM.groups.observe(viewLifecycleOwner){ groups ->
            adapter.updateGroups(groups)
        }
        studentVM.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null){
                Toast.makeText(
                    requireContext(),
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        buttonToAddStudent.setOnClickListener {
            showStudentAddDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        studentVM.fetchAllGroups()
    }

    fun showStudentDeleteDialog(studentId: Int){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление студента")
            .setMessage("Вы уверены, что хотите удалить этого студента?")
            .setPositiveButton("Удалить") { _, _ ->
                studentVM.deleteStudent(studentId)
            }
            .setNegativeButton("Отмена"){ dialog , _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showStudentEditDialog(studentId: Int){
        studentVM.changeStudentToEdit(studentId)
        val dialog = StudentDialog()
        dialog.show(parentFragmentManager, "StudentDialog")
    }

    fun showStudentAddDialog(){
        val dialog = StudentDialog()
        dialog.show(parentFragmentManager, "StudentDialog")
    }
}