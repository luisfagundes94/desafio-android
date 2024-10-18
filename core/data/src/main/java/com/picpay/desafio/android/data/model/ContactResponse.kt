package com.picpay.desafio.android.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("img") val img: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val username: String? = null
) : Parcelable
