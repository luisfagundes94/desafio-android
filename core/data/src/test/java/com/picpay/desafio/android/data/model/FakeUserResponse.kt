package com.picpay.desafio.android.data.model

val fakeContactResponse = ContactResponse(
    id = 1,
    img = "https://randomuser.me/api/portraits",
    name = "Fake Contact",
    username = "fakeContact"
)

val fakeContactResponseList = listOf(
    fakeContactResponse,
    fakeContactResponse.copy(id = 2),
    fakeContactResponse.copy(id = 3)
)
