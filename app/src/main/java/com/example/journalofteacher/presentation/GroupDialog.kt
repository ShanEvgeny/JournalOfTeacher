package com.example.journalofteacher.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.journalofteacher.R
import com.example.journalofteacher.presentation.viewmodels.GroupVM
import com.example.journalofteacher.presentation.viewmodels.factories.GroupVMFactory
import kotlin.getValue

class GroupDialog: DialogFragment() {
    private val groupVM: GroupVM by activityViewModels {
        GroupVMFactory(requireContext())
    }
    private lateinit var inputGroupTitle: EditText
    private var groupEditId = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_add_and_edit_group, null)
        inputGroupTitle = view.findViewById(R.id.inputGroupTitle)
        builder.setView(view)
            .setPositiveButton("Сохранить", null)
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
                groupVM.clearGroupToEdit()
            }
        if (groupVM.groupToEdit.value != null){
            builder.setTitle("Редактирование группы")
            fetchGroupToEditData()
        }
        else{
            builder.setTitle("Добавление группы")
        }
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                saveGroup()
            }
        }

        return dialog
    }

    fun saveGroup(){
        val groupTitle = inputGroupTitle.text.toString()
        if (groupVM.groupToEdit.value != null){
            groupVM.updateGroup(groupTitle, groupEditId)
            groupVM.clearGroupToEdit()
        }
        else
            groupVM.addGroup(groupTitle)
        if (groupVM.errorMessage.value != null){
            groupVM.clearErrorMessage()
            return
        }
        dismiss()
    }

    fun fetchGroupToEditData(){
        groupVM.groupToEdit.value?.let { group ->
            groupEditId = group.id
            inputGroupTitle.setText(group.title)
        }
    }
}