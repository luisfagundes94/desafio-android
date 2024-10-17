package com.picpay.desafio.android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.core.domain.model.User
import com.picpay.desafio.android.databinding.ListItemUserBinding

class UserListAdapter : ListAdapter<User, UserListItemViewHolder>(UserDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UserListItemViewHolder {
        val binding =
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserListItemViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
