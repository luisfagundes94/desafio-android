package com.picpay.desafio.android.testing.model

import com.picpay.desafio.android.domain.model.Contact

val fakeContact = Contact(
    id = 1,
    image = "https://randomuser.me/api/portraits",
    name = "Fake Contact",
    username = "fakeContact"
)

val fakeContactList = listOf(
    fakeContact,
    fakeContact.copy(id = 2),
    fakeContact.copy(id = 3)
)
