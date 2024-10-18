package com.picpay.desafio.android.contact.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.domain.model.Contact

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}
