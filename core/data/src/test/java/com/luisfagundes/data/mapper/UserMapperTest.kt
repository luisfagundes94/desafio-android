package com.luisfagundes.data.mapper

import com.luisfagundes.data.mapper.UserMapper.toDomain
import com.luisfagundes.data.mapper.UserMapper.toEntity
import com.luisfagundes.data.model.UserEntity
import com.luisfagundes.data.model.UserResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {
    @Test
    fun `toDomain maps UserResponse to User with non-null fields`() {
        val userResponse = mockk<UserResponse> {
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
        val userResponse = mockk<UserResponse> {
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

    @Test
    fun `toEntity maps UserResponse to UserEntity with non-null fields`() {
        val userResponse = mockk<UserResponse> {
            every { id } returns 123
            every { img } returns "https://example.com/image.jpg"
            every { name } returns "John Doe"
            every { username } returns "johndoe"
        }

        val userEntity = userResponse.toEntity()

        assertEquals(123, userEntity.id)
        assertEquals("https://example.com/image.jpg", userEntity.image)
        assertEquals("John Doe", userEntity.name)
        assertEquals("johndoe", userEntity.username)
    }

    @Test
    fun `toEntity maps UserResponse to UserEntity with null fields`() {
        val userResponse = mockk<UserResponse> {
            every { id } returns null
            every { img } returns null
            every { name } returns null
            every { username } returns null
        }

        val userEntity = userResponse.toEntity()

        assertEquals(0, userEntity.id)
        assertEquals("", userEntity.image)
        assertEquals("", userEntity.name)
        assertEquals("", userEntity.username)
    }

    @Test
    fun `toDomain maps UserEntity to User with non-null fields`() {
        val userEntity = mockk<UserEntity> {
            every { id } returns 123
            every { image } returns "https://example.com/image.jpg"
            every { name } returns "John Doe"
            every { username } returns "johndoe"
        }

        val user = userEntity.toDomain()

        assertEquals(123, user.id)
        assertEquals("https://example.com/image.jpg", user.image)
        assertEquals("John Doe", user.name)
        assertEquals("johndoe", user.username)
    }

    @Test
    fun `toDomain maps UserEntity to User with null fields`() {
        val userEntity = mockk<UserEntity> {
            every { id } returns 0
            every { image } returns ""
            every { name } returns ""
            every { username } returns ""
        }

        val user = userEntity.toDomain()

        assertEquals(0, user.id)
        assertEquals("", user.image)
        assertEquals("", user.name)
        assertEquals("", user.username)
    }
}
