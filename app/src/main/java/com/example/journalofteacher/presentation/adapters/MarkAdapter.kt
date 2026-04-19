package com.example.journalofteacher.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.recyclerview.widget.RecyclerView
import com.example.journalofteacher.databinding.MarkViewBinding
import com.example.journalofteacher.domain.entities.Mark
import java.time.format.DateTimeFormatter

class MarkAdapter(
    private var marks: List<Mark>,
    private val onDeleteClick: (Mark) -> Unit,
    private val onEditClick: (Mark) -> Unit
): RecyclerView.Adapter<MarkAdapter.MarkViewHolder>() {
    class MarkViewHolder(val binding: MarkViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkViewHolder {
        val binding = MarkViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarkViewHolder, position: Int) {
        val mark = marks[position]
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        holder.binding.textStudent.text = "Студент: " + mark.student
        holder.binding.textSubject.text = "Предмет: " + mark.subject
        holder.binding.textDateOfLesson.text = "Был на занятии:\n" + mark.dateTimeLesson.format(formatter)
        if (mark.markValue == null)
            holder.binding.textMarkValue.text = "Без оценки"
        else
            holder.binding.textMarkValue.text = "Оценка: " + mark.markValue.toString()
        holder.binding.buttonDeleteMark.setOnClickListener {
            onDeleteClick(mark)
        }
        holder.binding.root.setOnLongClickListener {
            onEditClick(mark)
            true
        }
    }

    override fun getItemCount(): Int = marks.size

    fun updateItems(updatedMarks: List<Mark>) {
        marks = updatedMarks
        notifyDataSetChanged()
    }
}