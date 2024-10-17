package com.picpay.desafio.android.mapper

import com.picpay.desafio.android.core.data.mapper.UserMapper.toDomain
import com.picpay.desafio.android.core.data.model.UserResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {
    @Test
    fun `toDomain maps UserResponse to User with non-null fields`() {
        val userResponse =
            mockk<UserResponse> {
                every { id } returns 123
                every { img } returns "https://example.com/image.jpg"
                every { name } returns "John Doe"
                every { username } returns "johndoe"
            }

        val user = userResponse.toDomain()

        assertEquals(123, user.id)
        assertEquals("https://example.com/image.jpg", user.image)
        assertEquals("John Doe", user.name)
        assertEquals("johndoe", user.username)
    }

    @Test
    fun `toDomain maps UserResponse to User with null fields`() {
        val userResponse =
            mockk<UserResponse> {
                every { id } returns null
                every { img } returns null
                every { name } returns null
                every { username } returns null
            }

        val user = userResponse.toDomain()

        assertEquals(0, user.id)
        assertEquals("", user.image)
        assertEquals("", user.name)
        assertEquals("", user.username)
    }
}
