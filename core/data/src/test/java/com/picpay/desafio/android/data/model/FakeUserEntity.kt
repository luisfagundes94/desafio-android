package com.picpay.desafio.android.data.model

val fakeUserEntity = UserEntity(
    id = 1,
    image = "https://randomuser.me/api/portraits",
    name = "Fake User",
    username = "fakeuser"
)

val fakeUserEntityList = listOf(
    fakeUserEntity,
    fakeUserEntity.copy(id = 2),
    fakeUserEntity.copy(id = 3)
)
