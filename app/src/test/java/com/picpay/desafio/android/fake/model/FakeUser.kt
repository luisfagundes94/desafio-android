package com.picpay.desafio.android.fake.model

import com.picpay.desafio.android.core.domain.model.User

val fakeUser =
    User(
        id = 1,
        image = "https://randomuser.me/api/portraits",
        name = "Fake User",
        username = "fakeuser",
    )

val fakeUserList =
    listOf(
        fakeUser,
        fakeUser.copy(id = 2),
        fakeUser.copy(id = 3),
    )
