package com.example.journalofteacher.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journalofteacher.databinding.StudentViewBinding
import com.example.journalofteacher.domain.entities.Group
import com.example.journalofteacher.domain.entities.Student

class StudentAdapter(
    private var students: List<Student>,
    private var groups: List<Group>?,
    private val onDeleteClick: (Student) -> Unit,
    private val onEditClick: (Student) -> Unit
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){
    class StudentViewHolder(val binding: StudentViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {
        val view = StudentViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: StudentViewHolder,
        position: Int
    ) {
        val student = students[position]
        holder.binding.textStudentFullName.text = "Полное имя студента:\n" + student.fullName
        holder.binding.textGroup.text = "Группа: " + getGroupName(student.groupId)
        holder.binding.buttonDeleteStudent.setOnClickListener {
            onDeleteClick(student)
        }
        holder.binding.root.setOnLongClickListener {
            onEditClick(student)
            true
        }

    }

    override fun getItemCount(): Int = students.size

    fun updateStudents(updatedStudents: List<Student>) {
        students = updatedStudents
        notifyDataSetChanged()
    }

    fun updateGroups(updatedGroups: List<Group>){
        groups = updatedGroups
        notifyDataSetChanged()
    }

    private fun getGroupName(groupId: Int): String {
        return groups?.find { it.id == groupId }?.title ?: "Неизвестная группа"
    }

}