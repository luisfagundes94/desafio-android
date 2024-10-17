package com.luisfagundes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luisfagundes.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class PicPayDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
