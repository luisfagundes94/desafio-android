package com.picpay.desafio.android.data.model

val fakeContactEntity = ContactEntity(
    id = 1,
    image = "https://randomuser.me/api/portraits",
    name = "Fake Contact",
    username = "fakeContact"
)

val fakeContactEntityList = listOf(
    fakeContactEntity,
    fakeContactEntity.copy(id = 2),
    fakeContactEntity.copy(id = 3)
)
