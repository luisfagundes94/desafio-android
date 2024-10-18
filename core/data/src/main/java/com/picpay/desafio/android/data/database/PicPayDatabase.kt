package com.picpay.desafio.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class PicPayDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}
