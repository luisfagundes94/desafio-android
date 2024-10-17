package com.picpay.desafio.android.contact.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.domain.model.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
