package com.picpay.desafio.android.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.domain.model.User
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) = with(binding) {
        name.text = user.name
        username.text = user.username
        progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(user.image)
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
