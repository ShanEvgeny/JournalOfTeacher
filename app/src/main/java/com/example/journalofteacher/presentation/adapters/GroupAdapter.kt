package com.example.journalofteacher.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journalofteacher.databinding.GroupViewBinding
import com.example.journalofteacher.domain.entities.Group
class GroupAdapter(
    private var groups: List<Group>,
    private val onDeleteClick: (Group) -> Unit,
    private val onEditClick: (Group) -> Unit
): RecyclerView.Adapter<GroupAdapter.GroupViewHolder>(){
    class GroupViewHolder(val binding: GroupViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = GroupViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.binding.textGroupTitle.text = "Название: " + group.title
        holder.binding.buttonDeleteGroup.setOnClickListener {
            onDeleteClick(group)
        }
        holder.binding.root.setOnLongClickListener {
            onEditClick(group)
            true
        }
    }

    override fun getItemCount(): Int = groups.size

    fun updateItems(updatedGroups: List<Group>) {
        groups = updatedGroups
        notifyDataSetChanged()
    }

}