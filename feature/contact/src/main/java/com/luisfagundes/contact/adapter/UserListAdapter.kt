package com.luisfagundes.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.luisfagundes.contact.databinding.ListItemUserBinding
import com.luisfagundes.domain.model.User

class UserListAdapter : ListAdapter<User, UserListItemViewHolder>(UserDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val binding = ListItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
