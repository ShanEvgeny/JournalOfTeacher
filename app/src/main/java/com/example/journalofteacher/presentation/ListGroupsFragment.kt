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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journalofteacher.R
import com.example.journalofteacher.presentation.adapters.GroupAdapter
import com.example.journalofteacher.presentation.viewmodels.GroupVM
import com.example.journalofteacher.presentation.viewmodels.factories.GroupVMFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListGroupsFragment: Fragment() {
    private val groupVM: GroupVM by activityViewModels {
        GroupVMFactory(requireContext())
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupAdapter
    private lateinit var buttonToAddGroup: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_groups, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonToAddGroup = view.findViewById(R.id.buttonToAddGroup)
        recyclerView = view.findViewById(R.id.recyclerViewGroups)
        adapter = GroupAdapter(
            emptyList(),
            onDeleteClick = { group -> showGroupDeleteDialog(group.id) },
            onEditClick = {group -> showGroupEditDialog(group.id)}
        )
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            3,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapter

        groupVM.groups.observe(viewLifecycleOwner) { groups ->
            adapter.updateItems(groups)
        }
        groupVM.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotEmpty())
                Toast.makeText(
                    requireContext(),
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
        }
        buttonToAddGroup.setOnClickListener {
            showGroupAddDialog()
        }

    }

    fun showGroupDeleteDialog(groupId: Int){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление группы")
            .setMessage("Вы уверены, что хотите удалить эту группу?")
            .setPositiveButton("Удалить") { _, _ ->
                groupVM.deleteGroup(groupId)
            }
            .setNegativeButton("Отмена"){ dialog , _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showGroupEditDialog(groupId: Int){
        groupVM.changeGroupToEdit(groupId)
        val dialog = GroupDialog()
        dialog.show(parentFragmentManager, "GroupDialog")
    }

    fun showGroupAddDialog(){
        val dialog = GroupDialog()
        dialog.show(parentFragmentManager, "GroupDialog")
    }
}