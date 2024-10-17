package com.picpay.desafio.android.fake.model

import com.picpay.desafio.android.core.data.model.UserResponse

val fakeUserResponse =
    UserResponse(
        id = 1,
        img = "https://randomuser.me/api/portraits",
        name = "Fake User",
        username = "fakeuser"
    )

val fakeUserResponseList =
    listOf(
        fakeUserResponse,
        fakeUserResponse.copy(id = 2),
        fakeUserResponse.copy(id = 3)
    )
