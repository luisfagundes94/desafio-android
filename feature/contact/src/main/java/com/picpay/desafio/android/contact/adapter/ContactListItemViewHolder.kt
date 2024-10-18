package com.picpay.desafio.android.contact.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.contact.R
import com.picpay.desafio.android.contact.databinding.ListItemContactBinding
import com.picpay.desafio.android.domain.model.Contact
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ContactListItemViewHolder(
    private val binding: ListItemContactBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(contact: Contact) = with(binding) {
        name.text = contact.name
        username.text = contact.username
        progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(contact.image)
            .error(R.drawable.ic_round_account_circle)
            .into(picture, picassoCallback())
    }

    private fun picassoCallback() = object : Callback {
        override fun onSuccess() {
            binding.progressBar.visibility = View.GONE
        }

        override fun onError(e: Exception?) {
            binding.progressBar.visibility = View.GONE
        }
    }
}
