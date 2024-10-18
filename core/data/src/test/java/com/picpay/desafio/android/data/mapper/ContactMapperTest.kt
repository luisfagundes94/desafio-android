package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.mapper.ContactMapper.toDomain
import com.picpay.desafio.android.data.mapper.ContactMapper.toEntity
import com.picpay.desafio.android.data.model.ContactEntity
import com.picpay.desafio.android.data.model.ContactResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ContactMapperTest {
    @Test
    fun `toDomain maps ContactResponse to Contact with non-null fields`() {
        val contactResponse = mockk<ContactResponse> {
            every { id } returns 123
            every { img } returns "https://example.com/image.jpg"
            every { name } returns "John Doe"
            every { username } returns "johndoe"
        }

        val contact = contactResponse.toDomain()

        assertEquals(123, contact.id)
        assertEquals("https://example.com/image.jpg", contact.image)
        assertEquals("John Doe", contact.name)
        assertEquals("johndoe", contact.username)
    }

    @Test
    fun `toDomain maps ContactResponse to Contact with null fields`() {
        val contactResponse = mockk<ContactResponse> {
            every { id } returns null
            every { img } returns null
            every { name } returns null
            every { username } returns null
        }

        val contact = contactResponse.toDomain()

        assertEquals(0, contact.id)
        assertEquals("", contact.image)
        assertEquals("", contact.name)
        assertEquals("", contact.username)
    }

    @Test
    fun `toEntity maps ContactResponse to ContactEntity with non-null fields`() {
        val contactResponse = mockk<ContactResponse> {
            every { id } returns 123
            every { img } returns "https://example.com/image.jpg"
            every { name } returns "John Doe"
            every { username } returns "johndoe"
        }

        val contactEntity = contactResponse.toEntity()

        assertEquals(123, contactEntity.id)
        assertEquals("https://example.com/image.jpg", contactEntity.image)
        assertEquals("John Doe", contactEntity.name)
        assertEquals("johndoe", contactEntity.username)
    }

    @Test
    fun `toEntity maps ContactResponse to ContactEntity with null fields`() {
        val contactResponse = mockk<ContactResponse> {
            every { id } returns null
            every { img } returns null
            every { name } returns null
            every { username } returns null
        }

        val contactEntity = contactResponse.toEntity()

        assertEquals(0, contactEntity.id)
        assertEquals("", contactEntity.image)
        assertEquals("", contactEntity.name)
        assertEquals("", contactEntity.username)
    }

    @Test
    fun `toDomain maps ContactEntity to Contact with non-null fields`() {
        val contactEntity = mockk<ContactEntity> {
            every { id } returns 123
            every { image } returns "https://example.com/image.jpg"
            every { name } returns "John Doe"
            every { username } returns "johndoe"
        }

        val contact = contactEntity.toDomain()

        assertEquals(123, contact.id)
        assertEquals("https://example.com/image.jpg", contact.image)
        assertEquals("John Doe", contact.name)
        assertEquals("johndoe", contact.username)
    }

    @Test
    fun `toDomain maps ContactEntity to Contact with null fields`() {
        val contactEntity = mockk<ContactEntity> {
            every { id } returns 0
            every { image } returns ""
            every { name } returns ""
            every { username } returns ""
        }

        val contact = contactEntity.toDomain()

        assertEquals(0, contact.id)
        assertEquals("", contact.image)
        assertEquals("", contact.name)
        assertEquals("", contact.username)
    }
}
