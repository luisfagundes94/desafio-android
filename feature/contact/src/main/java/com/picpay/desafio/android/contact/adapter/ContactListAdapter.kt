package com.picpay.desafio.android.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.contact.databinding.ListItemContactBinding
import com.picpay.desafio.android.domain.model.Contact

class ContactListAdapter : ListAdapter<Contact, ContactListItemViewHolder>(ContactDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListItemViewHolder {
        val binding = ListItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
