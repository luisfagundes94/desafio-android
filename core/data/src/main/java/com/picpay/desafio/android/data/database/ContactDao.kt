package com.picpay.desafio.android.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.data.model.ContactEntity

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    suspend fun getAll(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<ContactEntity>)

    @Query("DELETE FROM contacts")
    suspend fun clearAll()
}
