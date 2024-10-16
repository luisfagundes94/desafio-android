package com.picpay.desafio.android

import junit.framework.TestCase.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ExampleServiceTest {

    private val api = mock<PicPayService>()

    private val service = ExampleService(api)

    @Test
    fun exampleTest() {
        // given
        val call = mock<Call<List<User>>>()
        val expectedUsers = emptyList<User>()

        whenever(call.execute()).thenReturn(Response.success(expectedUsers))
        whenever(api.getUsers()).thenReturn(call)

        // when
        val users = service.example()

        // then
        assertEquals(users, expectedUsers)
    }
}