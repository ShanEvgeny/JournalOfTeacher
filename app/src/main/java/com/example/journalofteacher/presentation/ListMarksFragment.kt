package com.example.journalofteacher.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journalofteacher.R
import com.example.journalofteacher.domain.entities.Mark
import com.example.journalofteacher.presentation.adapters.MarkAdapter
import com.example.journalofteacher.presentation.viewmodels.MarksVM
import com.example.journalofteacher.presentation.viewmodels.factories.MarkVMFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.format.DateTimeFormatter

class ListMarksFragment: Fragment() {
    private val marksVM: MarksVM by activityViewModels(){
        MarkVMFactory(requireContext())
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MarkAdapter
    private lateinit var buttonToAddMark: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_marks, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.textViewListMarks)
        buttonToAddMark = view.findViewById(R.id.buttonToAddMark)
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = MarkAdapter(
            emptyList(),
            marksVM.students.value,
            onDeleteClick = { mark -> showMarkDeleteDialog(mark.id) },
            onEditClick = {mark -> showMarkEditDialog(mark.id)}
        )
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            3,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapter
        textView.text = "Пока пусто( отметок не добавляли"
        marksVM.marks.observe(viewLifecycleOwner) { marks ->
            adapter.updateItems(marks)
        }
        marksVM.students.observe(viewLifecycleOwner){ students ->
            adapter.updateStudents(students)
        }
        marksVM.lastDateTimeLesson.observe(viewLifecycleOwner){ datetime ->
            if (datetime != null){
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                textView.text = "Последнее сохраненное время пары:\n${
                    datetime.format(formatter)
                }"
            }
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
        buttonToAddMark.setOnClickListener {
            findNavController().navigate(R.id.action_to_add_mark)
        }
    }

    override fun onStart() {
        super.onStart()
        marksVM.fetchStudents()
    }

    fun showMarkDeleteDialog(markId: Int){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление отметки")
            .setMessage("Вы уверены, что хотите удалить эту отметку?")
            .setPositiveButton("Удалить") { _, _ ->
                marksVM.deleteMark(markId)
            }
            .setNegativeButton("Отмена"){ dialog , _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showMarkEditDialog(markId: Int){
        val dialog = EditMarkDialog()
        marksVM.changeMarkToEdit(markId)
        dialog.show(parentFragmentManager, "EditMarkDialog")
    }
}