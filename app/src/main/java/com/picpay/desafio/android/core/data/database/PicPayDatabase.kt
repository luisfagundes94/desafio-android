package com.picpay.desafio.android.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.core.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class PicPayDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
